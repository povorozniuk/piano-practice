package com.povorozniuk.backend.service.tasks;

import com.povorozniuk.backend.service.mutex.MutexManager;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DailyDataTransformTask extends Task {
    @Getter
    private final MutexManager manager;
    private final PostgresScriptExecutor postgresScriptExecutor;

    public DailyDataTransformTask(MutexManager dailyDataTransformationManager, PostgresScriptExecutor postgresScriptExecutor) {
        this.manager = dailyDataTransformationManager;
        this.postgresScriptExecutor = postgresScriptExecutor;
    }

    @Override
    public void run() {
        if (canRun(manager)){
            try{
                long started = System.currentTimeMillis();
                manager.startTask();
                postgresScriptExecutor.executeScriptsForPracticeMinuteTable();
                manager.updatePercentCompleted(35);
                postgresScriptExecutor.executeScriptsForPracticeDayTable();
                manager.updatePercentCompleted(70);
                postgresScriptExecutor.executeScriptsForPracticeMonthTable();
                long timeTaken = System.currentTimeMillis() - started;
                log.info("[ {} ] completed successfully. Time taken [ {} ]", manager.getName(), DurationFormatUtils.formatDuration(timeTaken, "HH:mm:ss,SSS"));
            }catch (Exception e){
                log.error("An error has occurred while running [ {} ]", manager.getName(), e);
                sendSnsNotification(ExceptionUtils.getMessage(e), manager.getName() + " failure");
                manager.updatePercentCompleted(0);
            }finally {
                manager.completeTask();
            }
        }
    }
}
