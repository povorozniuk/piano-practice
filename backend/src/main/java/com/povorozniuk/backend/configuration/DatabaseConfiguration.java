package com.povorozniuk.backend.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class DatabaseConfiguration {

    @Bean(destroyMethod = "close")
    @ConfigurationProperties(prefix = "multi-db.piano.jdbc")
    public HikariDataSource multiDbPianoDataSource() {
        final HikariDataSource ds = DataSourceBuilder.create().type(HikariDataSource.class).build();
        ds.setPoolName("Piano Remote - Postgres DB");
        return ds;
    }

    @Bean
    public JdbcTemplate multiDbPianoJdbcTemplate(
            @Qualifier("multiDbPianoDataSource") HikariDataSource dataSource) {
        final JdbcTemplate template = new JdbcTemplate(dataSource);
        template.setFetchSize(100);
        return template;
    }

    @Bean(name = "multiDbPianoTransactionManager")
    public PlatformTransactionManager multiDbPianoTransactionManager(
            @Qualifier("multiDbPianoDataSource") HikariDataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager( @Qualifier("multiDbPianoDataSource") HikariDataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
