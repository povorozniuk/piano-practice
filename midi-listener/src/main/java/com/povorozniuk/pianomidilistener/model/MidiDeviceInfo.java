package com.povorozniuk.pianomidilistener.model;

import lombok.Getter;
import lombok.Setter;

import javax.sound.midi.MidiDevice;

@Getter
@Setter
public class MidiDeviceInfo extends MidiDevice.Info {

    private boolean isOpen;
    /**
     * Constructs a device info object.
     *
     * @param name        the name of the device
     * @param vendor      the name of the company who provides the device
     * @param description a description of the device
     * @param version     version information for the device
     */
    public MidiDeviceInfo(String name, String vendor, String description, String version, boolean isOpen) {
        super(name, vendor, description, version);
        this.isOpen = isOpen;
    }
}
