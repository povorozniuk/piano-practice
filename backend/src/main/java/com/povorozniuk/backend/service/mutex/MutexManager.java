package com.povorozniuk.backend.service.mutex;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.concurrent.Semaphore;

@Slf4j
@JsonPropertyOrder({"name","latestRunStartedAt","latestSuccessfulRunFinishedAt","percentCompleted", "isBlocked","isRunning"})
public class MutexManager {

    @Getter
    private boolean isBlocked;
    @Getter
    private boolean isRunning;
    @Getter
    private DateTime latestSuccessfulRunFinishedAt;
    @Getter
    private DateTime latestRunStartedAt;
    @Getter
    private int percentCompleted = 0;
    @Getter
    private final String name;

    public MutexManager(String name) {
        this.name = name;
    }

    private final Semaphore mutex = new Semaphore(1);

    public void updatePercentCompleted (int percentCompleted){
        log.info("[ {} ] completed [ {}% ] ", name, percentCompleted);
        this.percentCompleted = percentCompleted;
    }

    public void block(){
        if (isBlocked){
            log.info("[ {} ] is already blocked", name);
        }else{
            final boolean permitAcquired = mutex.tryAcquire();
            if (permitAcquired){
                isBlocked = true;
                log.info("Blocked [ {} ]", name);
            }else{
                log.error("Could not block [ {} ]", name);
            }
        }
    }

    public void unblock(){
        if (isBlocked){
            mutex.release();
            isBlocked = false;
            log.info("Unblocked [ {} ]", name);
        }else{
            log.info("[ {} ] is already unblocked", name);
        }
    }

    public void startTask(){
        if (isBlocked) {
            log.info("Cannot start [ {} ] as it is blocked", name);
            return;
        }
        if (isRunning){
            log.info("[ {} ] is already running", name);
            return;
        }
        final boolean permitAcquired = mutex.tryAcquire();
        if (permitAcquired){
            log.info("Started [ {} ]", name);
            latestRunStartedAt = DateTime.now(DateTimeZone.UTC);
            isRunning = true;
        }else {
            log.error("Could not acquire permit to start [ {} ]", name);
        }
    }

    public void completeTask(){
        if (isRunning){
            log.info("Completed [ {} ]", name);
            mutex.release();
            isRunning = false;
            percentCompleted = 100;
            latestSuccessfulRunFinishedAt = DateTime.now(DateTimeZone.UTC);
        }
    }

}
