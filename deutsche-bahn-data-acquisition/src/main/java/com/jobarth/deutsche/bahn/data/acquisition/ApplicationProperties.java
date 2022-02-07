package com.jobarth.deutsche.bahn.data.acquisition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Reads the application.yaml file with all of the eva numbers for the stations that will be included.
 */
@ConfigurationProperties("application-properties")
public class ApplicationProperties {

    @Autowired
    private TimetableManagerImpl timetableManager;

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
                .map(eva -> new QuartzTimetableService(eva, timetableManager))
                .collect(Collectors.toList());
    }
}
