package com.jobarth.deutsche.bahn.data.acquisition;

import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@EnableConfigurationProperties(ApplicationProperties.class)
public class DataAcquisitionMain {

    public static void main(String[] args) {
        SpringApplication.run(DataAcquisitionMain.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner(ApplicationProperties properties) {
        return args -> {
            Arrays.stream(properties.getEva().split(","))
                    .map(eva -> eva.replaceAll("\\s", ""))
                    .map(QuartzTimetableService::new)
                    .forEach(QuartzTimetableService::start);
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
        };
    }
}
