package com.jobarth.deutsche.bahn.data.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "dp")
public class Departure {

    private final static Logger LOGGER = LoggerFactory.getLogger(Departure.class);

    private String changedTime;
    private String changedPlatform;
    private String plannedTime;
    private String plannedPlatform;
    private String plannedPath;
    private String changedPath;

    public Departure(String changedTime, String changedPlatform, String plannedTime, String plannedPlatform, String plannedPath, String changedPath) {
        this.changedTime = changedTime;
        this.changedPlatform = changedPlatform;
        this.plannedTime = plannedTime;
        this.plannedPlatform = plannedPlatform;
        this.plannedPath = plannedPath;
        this.changedPath = changedPath;
    }

    public Departure() {
    }

    public void setPlannedPath(String plannedPath) {
        if (plannedPath != null)
            this.plannedPath = plannedPath;
    }

    public void setChangedTime(String changedTime) {
        if (changedTime != null)
            this.changedTime = changedTime;
    }

    public void setChangedPlatform(String changedPlatform) {
        if (changedPlatform != null)
            this.changedPlatform = changedPlatform;
    }

    public void setPlannedTime(String plannedTime) {
        if (plannedTime != null)
            this.plannedTime = plannedTime;
    }

    public void setPlannedPlatform(String plannedPlatform) {
        if (plannedPlatform != null)
            this.plannedPlatform = plannedPlatform;
    }

    public void setChangedPath(String changedPath) {
        if (changedPath != null)
            this.changedPath = changedPath;
    }

    @XmlAttribute(name = "cpth")
    public String getChangedPath() {
        return changedPath;
    }

    @XmlAttribute(name = "ppth")
    public String getPlannedPath() {
        return plannedPath;
    }

    @XmlAttribute(name = "ct")
    public String getChangedTime() {
        return this.changedTime;
    }

    @XmlAttribute(name = "cp")
    public String getChangedPlatform() {
        return this.changedPlatform;
    }

    @XmlAttribute(name = "pt")
    public String getPlannedTime() {
        return plannedTime;
    }

    @XmlAttribute(name = "pp")
    public String getPlannedPlatform() {
        return plannedPlatform;
    }

    public void update(Departure departure) {
        if (departure == null) {
            return;
        }
        LOGGER.info("Updating the departure.");
        this.setPlannedPath(departure.getPlannedPath());
        this.setPlannedTime(departure.getPlannedTime());
        this.setPlannedPlatform(departure.getPlannedPlatform());
        this.setChangedPlatform(departure.getChangedPlatform());
        this.setChangedTime(departure.getChangedTime());
        this.setChangedPath(departure.getChangedPath());
    }
}
