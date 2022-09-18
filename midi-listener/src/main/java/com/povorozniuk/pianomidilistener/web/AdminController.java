package com.povorozniuk.pianomidilistener.web;

import com.povorozniuk.pianomidilistener.exception.MidiException;
import com.povorozniuk.pianomidilistener.midi.MidiService;
import com.povorozniuk.pianomidilistener.util.NoteUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final MidiService midiService;

    public AdminController(MidiService midiService) {
        this.midiService = midiService;
    }

    @GetMapping(path = "/midi-devices")
    public ResponseEntity<?> getListOfMidiDevices() throws MidiException {
        return ResponseEntity.ok(midiService.getDevices());
    }

    @GetMapping(path = "/notes")
    public ResponseEntity<?> getNotes(){
        return ResponseEntity.ok(NoteUtil.get());
    }

}
