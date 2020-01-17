package com.briller.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@ComponentScan
public class DBConfig {
    @Bean(name="ftdcDB")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource ftdcDB(){return DataSourceBuilder.create().build();  }

    @Bean(name = "postgresJdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("ftdcDB") DataSource ftdcDB){return new JdbcTemplate(ftdcDB);}

}