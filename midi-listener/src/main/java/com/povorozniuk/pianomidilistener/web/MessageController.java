package com.povorozniuk.pianomidilistener.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class MessageController {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final SimpMessagingTemplate messageTemplate;

    public MessageController(SimpMessagingTemplate messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    @PostMapping(path = "/add")
    public ResponseEntity<?> sendData() throws JsonProcessingException {
        this.messageTemplate.convertAndSend("/topic/real-time-piano", objectMapper.writeValueAsString(RandomUtils.nextDouble(0,100)));
        return ResponseEntity.ok("ok");
    }

}
