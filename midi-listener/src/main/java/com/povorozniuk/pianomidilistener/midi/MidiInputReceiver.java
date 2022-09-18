package com.povorozniuk.pianomidilistener.midi;
import com.povorozniuk.pianomidilistener.model.Key;
import com.povorozniuk.pianomidilistener.model.Pedal;
import com.povorozniuk.pianomidilistener.service.DatabaseService;
import com.povorozniuk.pianomidilistener.util.JacksonMapper;
import com.povorozniuk.pianomidilistener.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDateTime;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import java.util.Map;


@Slf4j
public class MidiInputReceiver implements Receiver {
    private final SimpMessagingTemplate messageTemplate;
    private final DatabaseService databaseService;
    private final JacksonMapper jacksonMapper = new JacksonMapper();
    private final String deviceName;

    private final static boolean ENABLE_LOGGING = false;
    private final static boolean SEND_PEDAL_DATA_VIA_WEB_SOCKET = false;
    private final static boolean SEND_KEYS_DATA_VIA_WEB_SOCKET = true;

    public MidiInputReceiver(String deviceName, SimpMessagingTemplate simpMessagingTemplate, DatabaseService databaseService) {
        messageTemplate = simpMessagingTemplate;
        this.deviceName = deviceName;
        this.databaseService = databaseService;
    }


    @Override
    public void send(MidiMessage message, long timeStamp) {
        String ts = LocalDateTime.now().toString("yyyy-MM-dd HH:mm:ss.SSSSSS");
        Map<String, Object> map = jacksonMapper.convertValue(message, Map.class);
        if (map.containsKey("status")){
            if (map.get("status") instanceof Number){
                Integer status = (Integer) map.get("status");
                if (status == 144) {
                    Key key = new Key(144, (Integer) map.get("data1"), (Integer) map.get("data2"), ts);
                    if (SEND_KEYS_DATA_VIA_WEB_SOCKET){
                        messageTemplate.convertAndSend("/topic/real-time-piano", JsonUtil.serialize(key));
                    }
                    databaseService.addKeyAction(key);
                    if (ENABLE_LOGGING){
                        log.info(JsonUtil.serialize(key));
                    }
                }else if(status == 176){
                    if (SEND_PEDAL_DATA_VIA_WEB_SOCKET){
                        Pedal pedal = new Pedal(176, (Integer) map.get("data2"));
                        this.messageTemplate.convertAndSend("/topic/real-time-piano", JsonUtil.serialize(pedal));
                        if(ENABLE_LOGGING){
                            log.info(JsonUtil.serialize(pedal));
                        }
                    }
                }
            }
        }
        if (ENABLE_LOGGING){
            log.info(JsonUtil.serialize(map));
        }
    }

    @Override
    public void close() {
        log.info("Shutting down Midi...");
    }
}
