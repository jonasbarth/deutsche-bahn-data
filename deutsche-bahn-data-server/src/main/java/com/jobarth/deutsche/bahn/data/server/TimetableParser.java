package com.jobarth.deutsche.bahn.data.server;

import com.jobarth.deutsche.bahn.data.domain.Change;
import com.jobarth.deutsche.bahn.data.domain.TimetableStop;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Collection;

/**
 * Parses a response from the Deutsche Bahn timetable REST API.
 */
public interface TimetableParser {

    /**
     * @return a {@link Collection} that contains all {@link TimetableStop} in a response from the timetable REST API.
     */
    public Collection<TimetableStop> parsePlannedTimetableStops() throws IOException, ParserConfigurationException, SAXException;

    public Collection<Change> parseChanges() throws ParserConfigurationException, IOException, SAXException;
}
