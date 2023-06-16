package com.DentalClinicX.DentalClinicManagement.configuration;


import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerConfig {

    public LoggerConfig(){};

    @Bean
    public Logger getLogger(){
        return Logger.getLogger(LoggerConfig.class);
    }
}
