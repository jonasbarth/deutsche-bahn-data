package com.jobarth.deutsche.bahn.data.acquisition.filter;

import com.jobarth.deutsche.bahn.data.domain.Timetable;

/**
 * A filter that is applied to a {@link Timetable} object.
 */
public interface TimetableFilter {

    /**
     * Applies a filter to the timetable object.
     * @param timetable the {@link Timetable} to which the filter will be applied
     * @return the original {@link Timetable} with the filter applied.
     */
    public Timetable apply(Timetable timetable);
}
