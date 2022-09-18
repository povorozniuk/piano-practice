package com.povorozniuk.pianomidilistener.midi;

import com.povorozniuk.pianomidilistener.service.DatabaseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Configuration
public class MidiConfiguration {
    private final SimpMessagingTemplate messageTemplate;
    private final DatabaseService databaseService;

    public MidiConfiguration(SimpMessagingTemplate messageTemplate, DatabaseService databaseService) {
        this.messageTemplate = messageTemplate;
        this.databaseService = databaseService;
    }

    @Bean
    public MidiService midiHandler(){
        return new MidiService(messageTemplate, databaseService);
    }
}
