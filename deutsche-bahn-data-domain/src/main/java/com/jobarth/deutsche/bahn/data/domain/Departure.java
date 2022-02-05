package com.jobarth.deutsche.bahn.data.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@XmlRootElement(name = "dp")
public class Departure {

    private final static Logger LOGGER = LoggerFactory.getLogger(Departure.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyMMddHHmm");

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

    public Departure(Departure departure) {
        if (departure == null) {
            this.changedTime = null;
            this.changedPlatform = "";
            this.plannedTime = null;
            this.plannedPlatform = "";
            this.plannedPath = "";
            this.changedPath = "";
        } else {
            this.changedTime = departure.getChangedTime();
            this.changedPlatform = departure.getChangedPlatform();
            this.plannedTime = departure.getPlannedTime();
            this.plannedPlatform = departure.getPlannedPlatform();
            this.plannedPath = departure.getPlannedPath();
            this.changedPath = departure.getChangedPath();
        }
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
        Departure departure = (Departure) o;
        return Objects.equals(changedTime, departure.changedTime) && Objects.equals(changedPlatform, departure.changedPlatform) && Objects.equals(plannedTime, departure.plannedTime) && Objects.equals(plannedPlatform, departure.plannedPlatform) && Objects.equals(plannedPath, departure.plannedPath) && Objects.equals(changedPath, departure.changedPath);
    }
}
