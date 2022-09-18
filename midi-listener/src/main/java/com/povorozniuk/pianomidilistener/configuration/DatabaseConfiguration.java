package com.povorozniuk.pianomidilistener.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class DatabaseConfiguration {

    @Bean(destroyMethod = "close")
    @ConfigurationProperties(prefix = "piano.jdbc")
    public HikariDataSource pianoDataSource() {
        final HikariDataSource ds = DataSourceBuilder.create().type(HikariDataSource.class).build();
        ds.setPoolName("Piano - Postgres DB");
        return ds;
    }

    @Bean
    public JdbcTemplate pianoJdbcTemplate(
            @Qualifier("pianoDataSource") HikariDataSource pianoDataSource) {
        final JdbcTemplate template = new JdbcTemplate(pianoDataSource);
        template.setFetchSize(100);
        return template;
    }

    @Bean(name = "pianoTransactionManager")
    public PlatformTransactionManager pianoTransactionManager(
            @Qualifier("pianoDataSource") HikariDataSource pianoDataSource) {
        return new DataSourceTransactionManager(pianoDataSource);
    }

}
