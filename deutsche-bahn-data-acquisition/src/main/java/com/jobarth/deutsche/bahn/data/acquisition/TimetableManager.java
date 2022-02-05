package com.jobarth.deutsche.bahn.data.acquisition;

import com.jobarth.deutsche.bahn.data.domain.Timetable;

/**
 * Creates, stores, and manipulates multiple {@link Timetable}.
 */
public interface TimetableManager {

    /**
     * @param evaNo the eva number of the timetable to look for.
     * @return the {@link Timetable} that has the evaNo. Returns {@code null} if no timetable matching the evaNo is found.
     */
    public Timetable get(String evaNo);

}
