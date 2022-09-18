package com.povorozniuk.pianomidilistener.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

@Slf4j
public class JsonUtil {
    private static final JacksonMapper jacksonMapper = new JacksonMapper();

    public static String serialize(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return jacksonMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("Unable to serialize obj: " + obj, e);
            return null;
        }
    }


    public static <T> T deserialize(String serializedStr, Class<T> objectClass) {
        if (StringUtils.isBlank(serializedStr)) {
            return null;
        }
        try {
            return jacksonMapper.readerFor(objectClass).readValue(serializedStr);
        } catch (IOException e) {
            log.error("Unable to deserialize str: " + serializedStr, e);
        }
        return null;
    }
}
