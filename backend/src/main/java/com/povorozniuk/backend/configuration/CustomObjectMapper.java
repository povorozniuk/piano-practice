package com.povorozniuk.backend.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.springframework.stereotype.Component;

@Component
public class CustomObjectMapper extends ObjectMapper {

        public CustomObjectMapper() {
            this.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            this.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            this.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            this.registerModule(new JodaModule());
        }

}
