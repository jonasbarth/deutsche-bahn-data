package com.jobarth.deutsche.bahn.data.server.domain;

/**
 * A timetable for a specific station.
 */
public interface Timetable {

    public void getPlannedDepartures();

    public void getPlannedArrivals();

    public void getPlannedDeparture(String id);

    public void getPlannedArrival(String id);
}
