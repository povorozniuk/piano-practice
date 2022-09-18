package com.povorozniuk.pianomidilistener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class PianoMidiListener {

	public static void main(String[] args) {
		final SpringApplication app = new SpringApplicationBuilder(PianoMidiListener.class).build();
		app.addListeners(event -> {
			if (event instanceof ApplicationEnvironmentPreparedEvent) {
				try {
					final ResourceLoader loader = new DefaultResourceLoader();
					final YamlPropertySourceLoader bean = new YamlPropertySourceLoader();
					List<PropertySource<?>> props = bean.load("notes.yaml",
							loader.getResource("classpath:/notes.yaml"));
					((ApplicationEnvironmentPreparedEvent) event).getEnvironment().getPropertySources()
							.addLast(props.get(0));
					log.info("Loaded notes.yaml");
				} catch (IOException e) {
					throw new IllegalStateException("An error has occurred while reading yaml files.", e);
				}
			} else if (event instanceof ApplicationReadyEvent) {
				app.setListeners(Collections.emptyList());
			}
		});
		app.run(args);
	}

}
