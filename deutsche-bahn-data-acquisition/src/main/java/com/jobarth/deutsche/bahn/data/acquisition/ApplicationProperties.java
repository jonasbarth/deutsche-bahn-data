package com.jobarth.deutsche.bahn.data.acquisition;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

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
}
