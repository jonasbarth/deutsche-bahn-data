package com.jobarth.deutsche.bahn.data.acquisition;

import com.jobarth.deutsche.bahn.data.domain.Station;
import com.jobarth.deutsche.bahn.data.domain.Timetable;

import java.util.Collection;

/**
 * Creates, stores, and manipulates multiple {@link Timetable}.
 */
public interface TimetableManager {

    /**
     * @param evaNo the eva number of the timetable to look for.
     * @return the {@link Timetable} that has the evaNo. Returns {@code null} if no timetable matching the evaNo is found.
     */
    public Timetable get(String evaNo);

    /**
     * @return all stations currently existing in the manager. Will return an empty collection if no stations are present.
     */
    public Collection<Station> getStations();

}
