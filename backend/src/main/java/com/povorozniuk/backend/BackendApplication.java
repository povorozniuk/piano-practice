package com.povorozniuk.backend;

import com.amazonaws.services.s3.AmazonS3;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.cloud.aws.autoconfigure.context.ContextInstanceDataAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;
import java.util.Collections;
import java.util.List;


@EnableTransactionManagement
@EnableScheduling
@SpringBootApplication(exclude = ContextInstanceDataAutoConfiguration.class)
public class BackendApplication {

	@Value("${telegram.bot.bot_token}")
	private String botToken;

	public static void main(String[] args) {
		final SpringApplication app = new SpringApplicationBuilder(BackendApplication.class).build();
		app.addListeners(event -> {
			if (event instanceof ApplicationEnvironmentPreparedEvent) {
				try {
					final ResourceLoader loader = new DefaultResourceLoader();
					final YamlPropertySourceLoader bean = new YamlPropertySourceLoader();
					List<PropertySource<?>> props = bean.load("queries.yaml",
							loader.getResource("classpath:/queries.yaml"));
					props.addAll(bean.load("telegram.yaml",
							loader.getResource("classpath:/telegram.yaml")));
					for (PropertySource<?> propertySource : props){
						((ApplicationEnvironmentPreparedEvent) event).getEnvironment().getPropertySources()
								.addLast(propertySource);
					}
					System.out.println();
				} catch (IOException e) {
					throw new IllegalStateException("An error has occurred while reading query files.", e);
				}
			} else if (event instanceof ApplicationReadyEvent) {
				app.setListeners(Collections.emptyList());
			}
		});
		app.run(args);
	}

	@Bean
	ApplicationRunner applicationRunner(AmazonS3 amazonS3){
		return args -> {
			if (StringUtils.isBlank(botToken)){
				System.out.println("Secrets are not loaded");
			}else{
				System.out.println("Secrets are loaded");
			}
//			amazonS3.listBuckets().forEach(bucket -> System.out.println(bucket.getName()));
		};
	}

}
