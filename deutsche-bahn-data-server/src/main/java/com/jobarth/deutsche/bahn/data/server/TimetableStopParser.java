package com.jobarth.deutsche.bahn.data.server;

import com.jobarth.deutsche.bahn.data.domain.TimetableStop;

/**
 * Parses timetable stop from the Deutsche Bahn timetable REST API.
 */
public interface TimetableStopParser {

    /**
     * @return a {@link TimetableStop} as parsed from the REST API response.
     */
    public TimetableStop parseTimetableStop();
}
