package com.jobarth.deutsche.bahn.data.server;

import com.jobarth.deutsche.bahn.data.server.domain.Timetable;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Makes a request to the DB com.jobarth.deutsche.bahn.data.domain.Timetable API to fetch timetable data for a station
 */
public interface TimetableRequest {

    public Timetable getPlan(LocalDateTime datetime) throws IOException, InterruptedException, ParserConfigurationException, SAXException;

    public Timetable getRecentChanges();
}
