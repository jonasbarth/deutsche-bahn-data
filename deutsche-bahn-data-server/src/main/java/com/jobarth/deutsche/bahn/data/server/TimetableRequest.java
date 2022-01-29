package com.jobarth.deutsche.bahn.data.server;

import com.jobarth.deutsche.bahn.data.domain.Timetable;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Makes a request to the DB timetable API to fetch timetable data for a station.
 */
public interface TimetableRequest {

    /**
     * @return the eva number of the station for which this request fetches data. Will not return {@code null}.
     */
    public String getEvaNo();

    /**
     * Fetches the planned trips for this station.
     * @param datetime the {@link LocalDateTime} for which the API will query a timetable for. Must only be to the hour.
     * @return a {@link Timetable} with the known trips for this station and date time.
     */
    public Timetable getPlan(LocalDateTime datetime) throws IOException, InterruptedException, JAXBException;

    /**
     * Fetches the recent changes for this station.
     * @return a {@link Timetable} with recent changes for this station.
     */
    public Timetable getRecentChanges() throws IOException, InterruptedException, JAXBException;

    /**
     * Fetches all known future changes for this station.
     * @return a {@link Timetable} of future known {@link } for the {@link } of this request.
     */
    public Timetable getFutureChanges() throws IOException, InterruptedException, JAXBException;
}
