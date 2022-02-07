package com.jobarth.deutsche.bahn.data.acquisition;

import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
public class DataAcquisitionMain {

    @Autowired
    List<TimetableService> timetableServices;

    public static void main(String[] args) {
        SpringApplication.run(DataAcquisitionMain.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner(ApplicationProperties properties) {
        return args -> {
            timetableServices.forEach(TimetableService::start);
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
        };
    }
}
