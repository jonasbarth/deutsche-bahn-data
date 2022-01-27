package com.jobarth.deutsche.bahn.data.server;

import com.jobarth.deutsche.bahn.data.domain.Change;
import com.jobarth.deutsche.bahn.data.domain.Timetable;
import com.jobarth.deutsche.bahn.data.domain.Station;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Makes a request to the DB timetable API to fetch timetable data for a station.
 */
public interface TimetableRequest {

    /**
     *
     * @param datetime
     * @return
     * @throws IOException
     * @throws InterruptedException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public Timetable getPlan(LocalDateTime datetime) throws IOException, InterruptedException, ParserConfigurationException, SAXException;

    /**
     * @return a {@link Collection} of recent {@link Change} for the {@link Station} of this request.
     */
    public Collection<Change> getRecentChanges() throws IOException, InterruptedException, ParserConfigurationException, SAXException;

    /**
     * @return a {@link Collection} of future known {@link Change} for the {@link Station} of this request.
     */
    public Collection<Change> getFutureChanges() throws IOException, InterruptedException, ParserConfigurationException, SAXException;




}
