package com.povorozniuk.backend.service;

import com.povorozniuk.backend.service.tasks.DailyDataTransformTask;
import com.povorozniuk.backend.service.tasks.OngoingTransformationTask;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class OrchestrationService {

    private final OngoingTransformationTask ongoingTransformationTask;
    private final DailyDataTransformTask dailyDataTransformTask;

    public OrchestrationService(DailyDataTransformTask dailyDataTransformTask, OngoingTransformationTask ongoingTransformationTask) {
        this.dailyDataTransformTask = dailyDataTransformTask;
        this.ongoingTransformationTask = ongoingTransformationTask;
    }

    @Scheduled(cron = "${cron.daily-transformation}")
    public void initiateDailyTransformationTask(){
        dailyDataTransformTask.run();
    }

    @Scheduled(cron = "${cron.ongoing-transformation}")
    public void initiateOngoingTransformationTask(){
        ongoingTransformationTask.run();
    }
}
