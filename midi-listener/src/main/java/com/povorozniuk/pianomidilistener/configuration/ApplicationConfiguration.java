package com.povorozniuk.pianomidilistener.configuration;


import com.povorozniuk.pianomidilistener.util.NoteUtil;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class ApplicationConfiguration {

    @PostConstruct
    public void setupNoteMappings(){
        NoteUtil.setKeyToNoteMapping(NoteUtil.generateKeyToNoteMapping(21, 108, 'A', 0, false));
    }

}
