package com.jobarth.deutsche.bahn.data.server;

import com.google.common.collect.Lists;
import com.jobarth.deutsche.bahn.data.domain.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

/**
 * An implementation of {@link TimetableParser} that parses XML.
 */
public class XmlTimetableParser implements TimetableParser {

    private final String xmlBody;
    private static final String PLANNED_TIME = "pt";
    private static final String PLANNED_PLATFORM = "pp";
    private static final String CHANGED_TIME = "ct";
    private static final String CHANGED_PLATFORM = "pt";
    private static final String STATION = "station";
    private static final String EVA = "eva";
    private static final String ID = "id";
    private static final String TRAIN_TYPE = "c";
    private static final String TRAIN_NUMBER = "n";

    private static final String ARRIVAL_NODE = "ar";
    private static final String DEPARTURE_NODE = "dp";
    private static final String TRIP_LABEL_NODE = "tl";

    public XmlTimetableParser(String xmlBody) {
        this.xmlBody = xmlBody;
    }

    @Override
    public Collection<TimetableStop> parseTimetableStops() throws IOException, ParserConfigurationException, SAXException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        ByteArrayInputStream input = new ByteArrayInputStream(xmlBody.getBytes(StandardCharsets.UTF_8));
        Document doc = builder.parse(input);
        Element root = doc.getDocumentElement();

        Collection<TimetableStop> parsedTimetableStops = Lists.newArrayListWithCapacity(root.getChildNodes().getLength());

        String stationName = root.getAttribute(STATION);

        for (int i = 0; i < root.getChildNodes().getLength(); i++) {
            Node timetableStop = root.getChildNodes().item(i);
            String id = timetableStop.getAttributes().getNamedItem(ID).getNodeValue();
            String trainType = "";
            String trainNumber = "";
            String plannedArrivalTime = "";
            String plannedDepartureTime = "";
            String plannedPlatformAttr = "";

            for (int j = 0; j < timetableStop.getChildNodes().getLength(); j++) {
                Node timetableStopChild = timetableStop.getChildNodes().item(j);
                NamedNodeMap attributes = timetableStopChild.getAttributes();

                switch (timetableStopChild.getNodeName()) {

                    case TRIP_LABEL_NODE:
                        trainType = attributes.getNamedItem(TRAIN_TYPE).getNodeValue();
                        trainNumber = attributes.getNamedItem(TRAIN_NUMBER).getNodeValue();
                        break;
                    case ARRIVAL_NODE:
                        plannedArrivalTime = attributes.getNamedItem(PLANNED_TIME).getNodeValue();
                        plannedPlatformAttr = attributes.getNamedItem(PLANNED_PLATFORM).getNodeValue();
                        break;
                    case DEPARTURE_NODE:
                        plannedDepartureTime = attributes.getNamedItem(PLANNED_TIME).getNodeValue();
                        plannedPlatformAttr = attributes.getNamedItem(PLANNED_PLATFORM).getNodeValue();
                        break;
                    default:
                        break;
                }
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmm");
            LocalDateTime plannedArrival = LocalDateTime.parse(plannedArrivalTime, formatter);
            LocalDateTime plannedDeparture = LocalDateTime.parse(plannedDepartureTime, formatter);
            Platform plannedPlatform = new ImmutablePlatform(plannedPlatformAttr);
            //create timetable stop object
            Station station = new ImmutableStation(0, stationName, new ImmutableLocation(0, 0));

            parsedTimetableStops.add(new TimetableStopImpl(id, station, plannedArrival, plannedDeparture, plannedPlatform));
        }
        return parsedTimetableStops;
    }
}
