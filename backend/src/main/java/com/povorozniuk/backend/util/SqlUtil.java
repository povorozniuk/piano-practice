package com.povorozniuk.backend.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlUtil {

    public static List<String> splitSqlStatements (final String sql){
        String[] statements = sql.split(";");
        List<String> finalStatements = new ArrayList<>();
        for (String statement : statements){
            finalStatements.add(StringUtils.normalizeSpace(statement) + ";");
        }
        return finalStatements;
    }

    public static String findAndReplace(String sql, final Map<String, Object> findAndReplaceMap){
        for (Map.Entry<String, Object> entry : findAndReplaceMap.entrySet()){
            sql = sql.replaceAll(entry.getKey(), entry.getValue().toString());
        }
        return sql;
    }
}
