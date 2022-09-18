package com.povorozniuk.backend.service.tasks;

import com.povorozniuk.backend.util.SqlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class PostgresScriptExecutor {

    private final JdbcTemplate jdbcTemplate;

    @Value("${transformations.practice_minute.prep}")
    private String tablePracticeMinutePrepSql;
    @Value("${transformations.practice_minute.transform}")
    private String tablePracticeMinuteTransformSql;
    @Value("${transformations.practice_minute.min_criteria}")
    private String tablePracticeMinuteMinCriteriaSql;

    @Value("${transformations.practice_day.prep}")
    private String tablePracticeDayPrepSql;
    @Value("${transformations.practice_day.transform}")
    private String tablePracticeDayTransformSql;
    @Value("${transformations.practice_day.min_criteria}")
    private String tablePracticeDayMinCriteriaSql;

    @Value("${transformations.practice_month.prep}")
    private String tablePracticeMonthPrepSql;
    @Value("${transformations.practice_month.transform}")
    private String tablePracticeMonthTransformSql;
    @Value("${transformations.practice_month.min_criteria}")
    private String tablePracticeMonthMinCriteria;

    @Value("${transformations.common_sql_scripts.merge_from_tmp}")
    private String mergeFromTempTableSql;

    public PostgresScriptExecutor(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void executeScriptsForPracticeMinuteTable (){

        final String tableName = "practice_minute";
        final Map<String, Object> findAndReplaceMap = new HashMap<>();

        findAndReplaceMap.put(":merge_column", "time");
        findAndReplaceMap.put(":table_name", tableName);

        executePrepScripts(tablePracticeMinutePrepSql, tableName);

        final String minCriteria = getMinCriteria(tablePracticeMinuteMinCriteriaSql);
        findAndReplaceMap.put(":min_day", minCriteria);

        executeTransformScripts(findAndReplaceMap, tablePracticeMinuteTransformSql, tableName);
    }


    public void executeScriptsForPracticeDayTable (){

        final String tableName = "practice_day";
        final Map<String, Object> findAndReplaceMap = new HashMap<>();

        findAndReplaceMap.put(":merge_column", "day");
        findAndReplaceMap.put(":table_name", tableName);

        executePrepScripts(tablePracticeDayPrepSql, tableName);

        final String minCriteria = getMinCriteria(tablePracticeDayMinCriteriaSql);
        findAndReplaceMap.put(":min_day", minCriteria);

        executeTransformScripts(findAndReplaceMap, tablePracticeDayTransformSql, tableName);
    }

    public void executeScriptsForPracticeMonthTable (){

        final String tableName = "practice_month";
        final Map<String, Object> findAndReplaceMap = new HashMap<>();

        findAndReplaceMap.put(":merge_column", "month");
        findAndReplaceMap.put(":table_name", tableName);

        executePrepScripts(tablePracticeMonthPrepSql, tableName);

        final String minCriteria = getMinCriteria(tablePracticeMonthMinCriteria);
        findAndReplaceMap.put(":min_day", minCriteria);

        executeTransformScripts(findAndReplaceMap, tablePracticeMonthTransformSql, tableName);
    }

    private String getMinCriteria(final String sql){
        return jdbcTemplate.queryForObject(sql, String.class);
    }

    @Transactional(transactionManager = "multiDbPianoTransactionManager")
    public void executePrepScripts(final String prepSql, final String tableName) {
        List<String> prepSqlStatements = SqlUtil.splitSqlStatements(prepSql);
        log.info("Executing prep sql for '{}'", tableName);
        prepSqlStatements.forEach(jdbcTemplate::execute);
    }

    @Transactional(transactionManager = "multiDbPianoTransactionManager")
    public void executeTransformScripts(final Map<String, Object> findAndReplaceMap, final String transformSql, final String tableName) {

        List<String> transformSqlStatements = SqlUtil.splitSqlStatements(transformSql);
        transformSqlStatements.forEach(sql -> {
            String populated = SqlUtil.findAndReplace(sql, findAndReplaceMap);
            log.info("Executing transform sql for '{}'", tableName);
            jdbcTemplate.execute(populated);
        });

        List<String> mergeSql = SqlUtil.splitSqlStatements(mergeFromTempTableSql);
        mergeSql.forEach(sql -> {
            String populated = SqlUtil.findAndReplace(sql, findAndReplaceMap);
            log.info("Executing merge sql for '{}'", tableName);
            jdbcTemplate.execute(populated);
        });
    }

}
