package com.povorozniuk.backend.web;

import com.povorozniuk.backend.service.mutex.MutexManager;
import com.povorozniuk.backend.service.tasks.DailyDataTransformTask;
import com.povorozniuk.backend.service.tasks.OngoingTransformationTask;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/task")
public class TaskController {
    private final DailyDataTransformTask dailyDataTransformTask;

    private final OngoingTransformationTask ongoingTransformationTask;

    private final List<MutexManager> allManagers;

    @Value("${secret-passphrase}")
    private String secretPassphrase;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public TaskController(DailyDataTransformTask dailyDataTransformTask, OngoingTransformationTask ongoingTransformationTask, List<MutexManager> allManagers) {
        this.dailyDataTransformTask = dailyDataTransformTask;
        this.ongoingTransformationTask = ongoingTransformationTask;
        this.allManagers = allManagers;
    }

    @GetMapping(path = "/all/status")
    public ResponseEntity<?> allTasksStatus(){
        return ResponseEntity.ok(allManagers);
    }


    private boolean isAccessGranted(final String passphrase){
        return StringUtils.equals(secretPassphrase, passphrase);
    }

    @PostMapping(path = "/daily-transformation/block")
    public ResponseEntity<?> blockDailyTransformationTask(@RequestHeader("passphrase") String providedPassphrase){
        if (isAccessGranted(providedPassphrase)){
            dailyDataTransformTask.getManager().block();
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping(path = "/daily-transformation/unblock")
    public ResponseEntity<?> unblockDailyTransformationTask(@RequestHeader("passphrase") String providedPassphrase){
        if (isAccessGranted(providedPassphrase)){
            dailyDataTransformTask.getManager().unblock();
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping(path = "/daily-transformation/start")
    public ResponseEntity<?> startDailyTransformationTask(@RequestHeader("passphrase") String providedPassphrase){
        if (isAccessGranted(providedPassphrase)){
            return submitTask(dailyDataTransformTask);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }


    @PostMapping(path = "/ongoing-transformation/block")
    public ResponseEntity<?> blockOngoingTransformationTask(@RequestHeader("passphrase") String providedPassphrase){
        if (isAccessGranted(providedPassphrase)){
            ongoingTransformationTask.getManager().block();
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping(path = "/ongoing-transformation/unblock")
    public ResponseEntity<?> unblockOngoingTransformationTask(@RequestHeader("passphrase") String providedPassphrase){
        if (isAccessGranted(providedPassphrase)){
            ongoingTransformationTask.getManager().unblock();
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping(path = "/ongoing-transformation/start")
    public ResponseEntity<?> startOngoingTransformationTask(@RequestHeader("passphrase") String requestPassphrase){
        if (isAccessGranted(requestPassphrase)){
            return submitTask(ongoingTransformationTask);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    private ResponseEntity<?> submitTask(Runnable task){
        executorService.submit(task);
        return ResponseEntity.ok("Task Submitted!");
    }
}
