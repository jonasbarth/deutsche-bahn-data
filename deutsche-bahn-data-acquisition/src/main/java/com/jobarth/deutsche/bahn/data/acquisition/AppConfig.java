package com.jobarth.deutsche.bahn.data.acquisition;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.jobarth.deutsche.bahn.data.acquisition")
public class AppConfig {

    @Bean
    public TimetableManagerImpl timetableManager() {
        return new TimetableManagerImpl();
    }
}
