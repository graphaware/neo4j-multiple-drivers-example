package com.graphaware.neo4j.experiment.security.configuration;


import com.graphaware.neo4j.experiment.security.neo4j.Neo4jContextFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public FilterRegistrationBean<Neo4jContextFilter> filterRegistration(Neo4jContextFilter filter) {
        FilterRegistrationBean<Neo4jContextFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(filter);
        registration.addUrlPatterns("*");
        registration.setName("neo4jContextFilter");
        registration.setOrder(1);
        return registration;
    }
}
