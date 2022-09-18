package com.povorozniuk.backend.service.tasks;

import com.povorozniuk.backend.repository.ActionHistoryRepository;
import com.povorozniuk.backend.service.mutex.MutexManager;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OngoingTransformationTask extends Task {
    @Getter
    private final MutexManager manager;
    private final ActionHistoryRepository actionHistoryRepository;
    private final PostgresScriptExecutor postgresScriptExecutor;
    private final DailyDataTransformTask dailyDataTransformTask;
    private Integer currentCountCached;

    public OngoingTransformationTask(MutexManager ongoingTransformationManager, ActionHistoryRepository actionHistoryRepository, PostgresScriptExecutor postgresScriptExecutor, DailyDataTransformTask dailyDataTransformTask) {
        this.manager = ongoingTransformationManager;
        this.actionHistoryRepository = actionHistoryRepository;
        this.postgresScriptExecutor = postgresScriptExecutor;
        this.dailyDataTransformTask = dailyDataTransformTask;
    }

    @Override
    public void run() {
        if (canRun(manager)) {
            try {
                if (!dailyDataTransformTask.getManager().isRunning()){

                    long started = System.currentTimeMillis();
                    manager.startTask();
                    Integer currentCount = actionHistoryRepository.getCountForToday();
                    if (currentCount == null){
                        throw new IllegalStateException("Could not get current count from action_history table");
                    }
                    if (!currentCount.equals(currentCountCached)){
                        log.info("Current count cached [ {} ] vs db [ {} ]", currentCountCached, currentCount);
                        currentCountCached = currentCount;

                        postgresScriptExecutor.executeScriptsForPracticeMinuteTable();
                        manager.updatePercentCompleted(50);
                        postgresScriptExecutor.executeScriptsForPracticeDayTable();

                        long timeTaken = System.currentTimeMillis() - started;
                        log.info("[ {} ] completed successfully. Time taken [ {} ]", manager.getName(), DurationFormatUtils.formatDuration(timeTaken, "HH:mm:ss,SSS"));
                    }else{
                        log.info("Nothing to do. Current count cached [ {} ] vs db [ {} ]", currentCountCached, currentCount);
                    }
                }
            } catch (Exception e) {
                log.error("An error has occurred while running [ {} ]", manager.getName(), e);
                sendSnsNotification(ExceptionUtils.getMessage(e), manager.getName() + " failure");
                manager.updatePercentCompleted(0);
            } finally {
                manager.completeTask();
            }
        }
    }
}
