package com.jobarth.deutsche.bahn.data.server;

import com.jobarth.deutsche.bahn.data.domain.Timetable;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
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
    public Timetable getPlan(LocalDateTime datetime) throws IOException, InterruptedException, JAXBException;

    /**
     * @return a {@link Collection} of recent {@link } for the {@link } of this request.
     */
    public Timetable getRecentChanges() throws IOException, InterruptedException, JAXBException;

    /**
     * @return a {@link Collection} of future known {@link } for the {@link } of this request.
     */
    public Timetable getFutureChanges() throws IOException, InterruptedException, JAXBException;
}
