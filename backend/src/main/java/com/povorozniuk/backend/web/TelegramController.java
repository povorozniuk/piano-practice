package com.povorozniuk.backend.web;

import com.povorozniuk.backend.service.PianoDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/telegram")
public class TelegramController {

    private final PianoDatabaseService pianoDatabaseService;

    public TelegramController(PianoDatabaseService pianoDatabaseService) {
        this.pianoDatabaseService = pianoDatabaseService;
    }

    @GetMapping("/piano-bot/messages")
    public ResponseEntity<?> getBotHistory(){
        return ResponseEntity.ok(pianoDatabaseService.getMessages());
    }
}
