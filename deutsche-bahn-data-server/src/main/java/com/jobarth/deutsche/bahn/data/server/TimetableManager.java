package com.jobarth.deutsche.bahn.data.server;

import com.jobarth.deutsche.bahn.data.domain.Timetable;

/**
 * Creates, stores, and manipulates multiple {@link Timetable}.
 */
public interface TimetableManager {

    /**
     * Adds a timetable to the manager. If a timetable with the eva number already exists, it is not added.
     * @param timetable the {@link Timetable} to be added.
     */
    public void add(Timetable timetable);

    /**
     * Updates the timetable of the station with this eva number.
     * @param evaNumber the eva number of the timetable of the station to be updated.
     */
    public void update(int evaNumber);

}
