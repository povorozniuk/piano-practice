package com.povorozniuk.pianomidilistener.service;

import com.povorozniuk.pianomidilistener.model.Key;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

@Slf4j
@Service
public class DatabaseService {
    private final JdbcTemplate pianoJdbcTemplate;
    private static final Integer MAX_INSERT_STATEMENT_SIZE = 200;
    private final Queue<Key> queue = new ConcurrentLinkedDeque<>();

    public DatabaseService(JdbcTemplate pianoJdbcTemplate) {
        this.pianoJdbcTemplate = pianoJdbcTemplate;
    }
    public void addKeyAction(Key key){
        if (key != null){
            queue.add(key);
        }
    }

    @Scheduled(fixedDelay = 10000)
    public void saveDataFromQueueToDatabase(){
        List<Key> keys = new ArrayList<>();
        if (queue.size() >0){
            log.info("Records waiting to be saved to the db [ " + queue.size() + " ]");
            int queueSize = queue.size();
            int insertStatementSize = queueSize < MAX_INSERT_STATEMENT_SIZE ? queueSize : MAX_INSERT_STATEMENT_SIZE;
            for (int i = 0; i < insertStatementSize; i++){
                keys.add(queue.remove());
            }
            String sql = generateInsertSql(keys);
            try{
                pianoJdbcTemplate.execute(sql);
            }catch (Exception e){
                log.error("An error has occurred while saving data to the database. Adding [ " + keys.size() + " ] notes back into the queue", e);
                queue.addAll(keys);
            }
        }
    }

    private String generateInsertSql(List<Key> keys){
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO action_history (interaction_type, time, pressure, note_value, key_number) ");
        sb.append("VALUES ");
        for (Key key : keys){
            sb.append(String.format("('key', '%s', %d, '%s', %d)", key.getTimeStamp(), key.getPressure(), key.getNoteValue(), key.getKeyNumber()));
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(";");
        return sb.toString();
    }
}
