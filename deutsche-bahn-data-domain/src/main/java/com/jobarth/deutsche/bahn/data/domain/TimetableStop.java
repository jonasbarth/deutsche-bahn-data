package com.jobarth.deutsche.bahn.data.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "s")
public class TimetableStop {

    private final static Logger LOGGER = LoggerFactory.getLogger(TimetableStop.class);

    private String id;
    private Arrival arrival;
    private Departure departure;
    private TripLabel tripLabel;

    public TimetableStop(String id, Arrival arrival, Departure departure, TripLabel tripLabel) {
        this.id = id;
        this.arrival = arrival;
        this.departure = departure;
        this.tripLabel = tripLabel;
    }

    public TimetableStop() {
    }

    @XmlElement(name = "tl")
    public TripLabel getTripLabel() {
        return tripLabel;
    }

    public void setTripLabel(TripLabel tripLabel) {
        this.tripLabel = tripLabel;
    }

    @XmlElement(name = "dp")
    public Departure getDeparture() {
        return departure;
    }

    public void setDeparture(Departure departure) {
        this.departure = departure;
    }

    @XmlAttribute(name = "id")
    public String getId() {
        return id;
    }

    @XmlElement(name = "ar")
    public Arrival getArrival() {
        return this.arrival;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setArrival(Arrival arrival) {
        this.arrival = arrival;
    }

    public void update(TimetableStop timetableStop) {
        Objects.requireNonNull(timetableStop, "timetableStop must not be null");
        if (!timetableStop.getId().equals(this.id)) {
            throw new IllegalArgumentException(String.format("The timetable stops must have the same ID. ID of timetable stop to be updated: %s =/= %s ID of provided timetable stop.", this.id, timetableStop.getId()));
        }
        LOGGER.info("Updating the timetable stop with ID {}.", this.id);
        this.arrival.update(timetableStop.getArrival());

        /* If this stop is the end station, there will be no departure, hence we must check if it is null */
        if (this.departure != null)
            this.departure.update(timetableStop.getDeparture());
        this.tripLabel.update(timetableStop.getTripLabel());
    }
}
