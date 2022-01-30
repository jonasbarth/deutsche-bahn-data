package com.jobarth.deutsche.bahn.data.domain;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@XmlRootElement(name = "timetable")
public class Timetable {

    private final static Logger LOGGER = LoggerFactory.getLogger(Timetable.class);

    private List<TimetableStop> timetableStops;
    private String station;
    private String evaNo;

    public Timetable(List<TimetableStop> timetableStops, String station) {
        this.timetableStops = timetableStops;
        this.station = station;
    }

    public Timetable() {
        this.timetableStops = Lists.newLinkedList();
    }

    public Timetable(Timetable timetable) {
        this.station = timetable.getStation();
        this.evaNo = timetable.getEvaNo();
        this.timetableStops = Lists.newArrayListWithCapacity(timetable.getTimetableStops().size());
        timetable.getTimetableStops().stream()
            .map(TimetableStop::new)
            .forEach(timetableStops::add);

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

    @XmlAttribute(name = "eva")
    public String getEvaNo() {
        return evaNo;
    }

    public void setEvaNo(String evaNo) {
        this.evaNo = evaNo;
    }

    public void updateTimetable(Timetable timetable) {
        Objects.requireNonNull(timetable, "timetable must not be null.");
        if (!timetable.getStation().equals(this.station)) {
            throw new IllegalArgumentException(String.format("The timetables must have the same station. Station of timetable to be updated: %s =/= %s station of provided timetable.", this.station, timetable.getStation()));
        }
        LOGGER.info("Updating the timetable of station {}.", this.station);
        setEvaNo(timetable.getEvaNo());
        for (TimetableStop timetableStopUpdate : timetable.getTimetableStops()) {
            for (TimetableStop timetableStop : this.timetableStops) {
                if (timetableStopUpdate.getId().equals(timetableStop.getId())) {
                    timetableStop.update(timetableStopUpdate);
                }
            }
        }
    }

    public void extend(Timetable timetable) {
        Objects.requireNonNull(timetable, "timetable must not be null.");
        if (!timetable.getStation().equals(this.station)) {
            LOGGER.warn("Cannot extend timetable. The given timetable is from {} whereas the one it is supposed to be added to is {}.", timetable.getStation(), this.station);
            throw new IllegalArgumentException(String.format("Cannot extend timetable. The given timetable is from %s whereas the one it is supposed to be added to is %s.", timetable.getStation(), this.station));
        }

        Set<TimetableStop> timetableStopsToAdd = timetable.getTimetableStops().stream()
                .filter(timetableStop -> timetableStops
                        .stream()
                        .noneMatch(tt -> tt.getId().equals(timetableStop.getId())))
                .collect(Collectors.toSet());
        this.timetableStops.addAll(timetableStopsToAdd);
        LOGGER.info("Extended the time table of station {} with {} timetable stops.", this.station, timetableStopsToAdd.size());
    }


}
