package com.povorozniuk.backend.configuration;

import com.povorozniuk.backend.service.mutex.MutexManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;


@Configuration
public class TaskConfiguration {

    @Bean
    public MutexManager dailyDataTransformationManager(){
        return new MutexManager("Daily Data Transformation Task");
    }

    @Bean
    public MutexManager ongoingTransformationManager(){
        return new MutexManager("Ongoing Transformation Task");
    }

    @Bean
    public List<MutexManager> allManagers(MutexManager dailyDataTransformationManager, MutexManager ongoingTransformationManager){
        return Arrays.asList(ongoingTransformationManager, dailyDataTransformationManager);
    }
}
