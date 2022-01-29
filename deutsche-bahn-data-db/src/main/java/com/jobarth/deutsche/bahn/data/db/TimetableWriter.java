package com.jobarth.deutsche.bahn.data.db;

import com.jobarth.deutsche.bahn.data.domain.Timetable;

/**
 * Writes a timetable to storage.
 */
public interface TimetableWriter {

    public void write(Timetable timetable);
}
