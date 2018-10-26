package com.bf.bikefinder.com.bf.bikefinder.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:application.properties")
public class Datasource {

    @Value("${spring.datasource.driverClassName}")
    private String driverClass;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

//    @Bean
//    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() throws Exception{
//        System.out.println(driverClass+" "+ url+" "+username+" "+password);
//        DriverManagerDataSource source = new DriverManagerDataSource();
//        source.setDriverClassName(driverClass);
//        source.setUrl(url);
//        source.setUsername(username);
//        source.setPassword(password);
//        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(source);
//        return namedParameterJdbcTemplate;
//    }

    @Bean
    public DataSource dataSource(){
        System.out.println(driverClass+" "+ url+" "+username+" "+password);
        DriverManagerDataSource source = new DriverManagerDataSource();
        source.setDriverClassName(driverClass);
        source.setUrl(url);
        source.setUsername(username);
        source.setPassword(password);
        return source;
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(){
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(this.dataSource());
        return namedParameterJdbcTemplate;
    }
}
