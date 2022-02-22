package com.jobarth.deutsche.bahn.data.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@XmlRootElement(name = "ar")
public class Arrival {

    private final static Logger LOGGER = LoggerFactory.getLogger(Arrival.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyMMddHHmm");


    private String changedTime;
    private String changedPlatform;
    private String plannedTime;
    private String plannedPlatform;
    private String plannedPath;
    private String changedPath;

    public Arrival(String changedTime, String changedPlatform, String plannedTime, String plannedPlatform, String plannedPath, String changedPath) {
        this.changedTime = changedTime;
        this.changedPlatform = changedPlatform;
        this.plannedTime = plannedTime;
        this.plannedPlatform = plannedPlatform;
        this.plannedPath = plannedPath;
        this.changedPath = changedPath;
    }

    public Arrival() {
    }

    public Arrival(Arrival arrival) {
        if (arrival == null) {
            this.changedTime = null;
            this.changedPlatform = "";
            this.plannedTime = null;
            this.plannedPlatform = "";
            this.plannedPath = "";
            this.changedPath = "";
        } else {
            this.changedTime = arrival.getChangedTime();
            this.changedPlatform = arrival.getChangedPlatform();
            this.plannedTime = arrival.getPlannedTime();
            this.plannedPlatform = arrival.getPlannedPlatform();
            this.plannedPath = arrival.getPlannedPath();
            this.changedPath = arrival.getChangedPath();
        }
    }

    public void setChangedPath(String changedPath) {
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

    public void update(Arrival arrival) {
        if (arrival == null) {
            return;
        }
        LOGGER.info("Updating the arrival.");
        this.setPlannedPath(arrival.getPlannedPath());
        this.setPlannedTime(arrival.getPlannedTime());
        this.setPlannedPlatform(arrival.getPlannedPlatform());
        this.setChangedPlatform(arrival.getChangedPlatform());
        this.setChangedTime(arrival.getChangedTime());
        this.setChangedPath(arrival.getChangedPath());
    }

    public LocalDateTime getChangedTimeAsLocalDateTime() {
        if (getChangedTime() == null && getPlannedTime() == null) {
            return null;
        }
        if (getChangedTime() != null)
            return LocalDateTime.parse(getChangedTime(), DATE_TIME_FORMATTER);
        return LocalDateTime.parse(getPlannedTime(), DATE_TIME_FORMATTER);
    }

    public LocalDateTime getPlannedTimeAsLocalDateTime() {
        if (getPlannedTime() == null) {
            return null;
        }
        return LocalDateTime.parse(getPlannedTime(), DATE_TIME_FORMATTER);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Arrival arrival = (Arrival) o;
        return Objects.equals(changedTime, arrival.changedTime) && Objects.equals(changedPlatform, arrival.changedPlatform) && Objects.equals(plannedTime, arrival.plannedTime) && Objects.equals(plannedPlatform, arrival.plannedPlatform) && Objects.equals(plannedPath, arrival.plannedPath) && Objects.equals(changedPath, arrival.changedPath);
    }
}
