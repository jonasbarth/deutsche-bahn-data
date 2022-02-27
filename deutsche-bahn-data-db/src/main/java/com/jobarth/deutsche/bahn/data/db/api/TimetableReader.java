package com.jobarth.deutsche.bahn.data.db.api;

import com.jobarth.deutsche.bahn.data.db.domain.StationEntity;
import com.jobarth.deutsche.bahn.data.db.domain.TimetableStopEntity;
import com.jobarth.deutsche.bahn.data.domain.TimetableStop;

import java.util.Collection;

/**
 * Provides read access to the saved timetable data.
 */
public interface TimetableReader {

    /**
     * @param eva the eva number of the station of these stops
     * @return all {@link TimetableStop} for the station of the eva number.
     */
    Collection<TimetableStopEntity> getTimetableStops(String eva);

    Collection<StationEntity> getStations();
}
