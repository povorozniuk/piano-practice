package com.povorozniuk.pianomidilistener.midi;

import com.povorozniuk.pianomidilistener.model.MidiDeviceInfo;
import com.povorozniuk.pianomidilistener.exception.MidiException;
import com.povorozniuk.pianomidilistener.service.DatabaseService;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Transmitter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class MidiService {

    private SimpMessagingTemplate messageTemplate;
    private DatabaseService databaseService;

    private static final String VENDOR_NAME = "iConnectivity";
    private static final String DESCRIPTION = "mio";

    private Logger logger = LoggerFactory.getLogger(MidiService.class);
    private List<MidiDevice.Info> midiDevicesInfo;

    public List<MidiDeviceInfo> getDevices() throws MidiException {
        List<MidiDeviceInfo> midiDeviceInfoList = new ArrayList<>();
        MidiDevice.Info[] deviceInformation = MidiSystem.getMidiDeviceInfo();
        for (MidiDevice.Info info : deviceInformation){
            MidiDevice device = getDevice(info);
            MidiDeviceInfo deviceInfo = new MidiDeviceInfo(info.getName(), info.getVendor(), info.getDescription(), info.getVersion(), device.isOpen());
            midiDeviceInfoList.add(deviceInfo);
        }
        return midiDeviceInfoList;
    }

    public MidiService(SimpMessagingTemplate simpMessagingTemplate, DatabaseService databaseService){
        this.messageTemplate = simpMessagingTemplate;
        MidiDevice.Info[] allMidiDevices = MidiSystem.getMidiDeviceInfo();
        logger.info(String.format("Connected %d Midi Devices: ", allMidiDevices.length));
        for (MidiDevice.Info dev : allMidiDevices){
            logger.info(String.format("Vendor [ %s ] Name [ %s ] Description [ %s ] Version [ %s ]", dev.getVendor(), dev.getName(), dev.getDescription(), dev.getVersion()));
            try {
                logger.info("Class name: " + MidiSystem.getMidiDevice(dev).getClass().getName());
            } catch (MidiUnavailableException e) {
                e.printStackTrace();
            }
        }
        logger.info("------------------------------------------------------------------------------------------------");
        midiDevicesInfo  = Arrays.stream(MidiSystem.getMidiDeviceInfo()).filter(this::filterByVendor).collect(Collectors.toList());
        logger.info("Filtered Midi Interfaces:");
        logger.info("Size: " + midiDevicesInfo.size());
            midiDevicesInfo.forEach(dev -> {
                try {
                    logger.info(String.format("Vendor [ %s ] Name [ %s ] Description [ %s ] Version [ %s ] ClassName [ %s ]", dev.getVendor(), dev.getName(), dev.getDescription(), dev.getVersion(), MidiSystem.getMidiDevice(dev).getClass().getName()));
                } catch (MidiUnavailableException e) {
                    e.printStackTrace();
                }
            });
        try {
            MidiDevice piano = MidiSystem.getMidiDevice(midiDevicesInfo.stream().filter(midiDevice -> midiDevice.getClass().getName().contains("MidiIn")).findFirst().orElseThrow(() -> new IllegalStateException("Piano is not connected")));
            Transmitter trans = piano.getTransmitter();
            trans.setReceiver(new MidiInputReceiver(piano.getDeviceInfo().toString(), messageTemplate, databaseService));
            piano.open();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    private boolean filterByVendor(MidiDevice.Info device){
        logger.info("Checking whether " + device.toString() + " is a valid vendor");
        return device.getVendor().equalsIgnoreCase(VENDOR_NAME) || device.getDescription().toLowerCase().contains(DESCRIPTION) || device.getName().equalsIgnoreCase(DESCRIPTION);
    }

    private MidiDevice getDevice(MidiDevice.Info deviceInformation) throws MidiException {
        try {
            return MidiSystem.getMidiDevice(deviceInformation);
        } catch (MidiUnavailableException e) {
            throw new MidiException(String.format("An error has occurred while getting a device. Name [ %s ] Vendor [ %s ]", deviceInformation.getName(), deviceInformation.getVendor()), e);
        }
    }
}
