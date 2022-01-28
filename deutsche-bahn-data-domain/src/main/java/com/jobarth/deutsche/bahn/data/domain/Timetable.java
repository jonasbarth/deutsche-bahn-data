package com.jobarth.deutsche.bahn.data.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;

@XmlRootElement(name = "timetable")
public class Timetable {

    private List<TimetableStop> timetableStops;
    private String station;

    public Timetable(List<TimetableStop> timetableStops, String station) {
        this.timetableStops = timetableStops;
        this.station = station;
    }

    public Timetable() {
    }

    @XmlElement(name = "s")
    public List<TimetableStop> getTimetableStops() {
        return timetableStops;
    }

    public void setTimetableStops(List<TimetableStop> timetableStops) {
        this.timetableStops = timetableStops;
    }

    @XmlAttribute(name = "station")
    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public void updateTimetable(Timetable timetable) {
        Objects.requireNonNull(timetable, "timetable must not be null.");
        if (!timetable.getStation().equals(this.station)) {
            throw new IllegalArgumentException(String.format("The timetables must have the same station. Station of timetable to be updated: %s =/= %s station of provided timetable.", this.station, timetable.getStation()));
        }
        for (TimetableStop timetableStopUpdate : timetable.getTimetableStops()) {
            for (TimetableStop timetableStop : this.timetableStops) {
                if (timetableStopUpdate.getId().equals(timetableStop.getId())) {
                    timetableStop.update(timetableStopUpdate);
                }
            }
        }
    }
}
