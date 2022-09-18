package com.povorozniuk.pianomidilistener.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

public class JacksonMapper extends ObjectMapper {
    public JacksonMapper() {
        this.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        this.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        this.configure(SerializationFeature.INDENT_OUTPUT, Boolean.valueOf(System.getProperty("debug")));
        this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.registerModule(new Jdk8Module());
    }

}
