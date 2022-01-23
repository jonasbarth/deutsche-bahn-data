package com.jobarth.deutsche.bahn.data.server;

import com.jobarth.deutsche.bahn.data.domain.TimetableStop;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;

/**
 * Parses a response from the Deutsche Bahn timetable REST API.
 */
public interface TimetableParser {

    /**
     * @return a {@link Collection} that contains all {@link TimetableStop} in a response from the timetable REST API.
     */
    public Collection<TimetableStop> parseTimetableStops() throws IOException, ParserConfigurationException, SAXException;
}
