package com.jobarth.deutsche.bahn.data.acquisition;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Reads the application.yaml file with all of the eva numbers for the stations that will be included.
 */
@ConfigurationProperties("application-properties")
public class ApplicationProperties {

    @Value("${eva}")
    private String eva;

    public String getEva() {
        return eva;
    }

    public void setEva(String eva) {
        this.eva = eva;
    }

    @Bean
    public List<TimetableService> getServices() {
        return Arrays.stream(eva.split(","))
                .map(eva -> eva.replaceAll("\\s", ""))
                .map(QuartzTimetableService::new)
                .collect(Collectors.toList());
    }

    @Bean
    public List<String> evas() {
        return Arrays.stream(eva.split(","))
                .map(eva -> eva.replaceAll("\\s", ""))
                .collect(Collectors.toList());
    }

    @Bean
    public SchedulerFactoryBean quartzSchedule() {
        SchedulerFactoryBean quartzScheduler = new SchedulerFactoryBean();

        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(ApplicationContextProvider.getApplicationContext());
        quartzScheduler.setJobFactory(jobFactory);
        quartzScheduler.setSchedulerName("Quartz-Scheduler");

        return quartzScheduler;
    }
}
