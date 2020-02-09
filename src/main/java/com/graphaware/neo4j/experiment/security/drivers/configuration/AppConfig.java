package com.graphaware.neo4j.experiment.security.drivers.configuration;


import com.graphaware.neo4j.experiment.security.drivers.neo4j.DriverProvider;
import com.graphaware.neo4j.experiment.security.drivers.neo4j.Neo4jContextFilter;
import org.neo4j.driver.Driver;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

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

    @Bean(destroyMethod = "")
    @RequestScope
    public Driver createDriver(DriverProvider driverProvider) {
        return driverProvider.getDriver();
    }
}
