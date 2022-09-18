package com.povorozniuk.backend.service.tasks;

import com.povorozniuk.backend.service.mutex.MutexManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;

public abstract class Task implements Runnable {

    @Autowired
    private NotificationMessagingTemplate notificationMessagingTemplate;

    @Value("${sns.topic-name}")
    private String topicName;

    protected boolean canRun(MutexManager manager){
        return !manager.isRunning() && !manager.isBlocked();
    }

    protected void sendSnsNotification(String message, String subject){
        notificationMessagingTemplate.sendNotification(topicName, message, subject);
    }

}
