package com.jobarth.deutsche.bahn.data.server;

import com.jobarth.deutsche.bahn.data.domain.*;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * An implementation of {@link TimetableStop} that parses an XML Node.
 */
public class XmlTimetableStopParser implements TimetableStopParser {
    private static final String PLANNED_TIME = "pt";
    private static final String PLANNED_PLATFORM = "pp";
    private static final String CHANGED_TIME = "ct";
    private static final String CHANGED_PLATFORM = "pt";
    private static final String EVA = "eva";
    private static final String ID = "id";
    private static final String TRAIN_TYPE = "c";
    private static final String TRAIN_NUMBER = "n";

    private static final String ARRIVAL_NODE = "ar";
    private static final String DEPARTURE_NODE = "dp";
    private static final String TRIP_LABEL_NODE = "tl";
    private final Node timetableStop;

    /**
     * Constructor for {@link XmlTimetableStopParser}.
     * @param timetableStop the XML {@link Node} containing the timetable stop.
     */
    public XmlTimetableStopParser(Node timetableStop) {
        Objects.requireNonNull(timetableStop, "The timetableStop must not be null.");
        this.timetableStop = timetableStop;
    }

    @Override
    public TimetableStop parseTimetableStop() {
        String id = timetableStop.getAttributes().getNamedItem(ID).getNodeValue();
        String trainType = "";
        String trainNumber = "";
        String plannedArrivalTime = "";
        String plannedDepartureTime = "";
        String plannedPlatformAttr = "";

        for (int i = 0; i < timetableStop.getChildNodes().getLength(); i++) {
            Node timetableStopChild = timetableStop.getChildNodes().item(i);
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
        TripLabel tripLabel = new ImmutableTripLabel(Integer.parseInt(trainNumber), TrainType.valueOf(trainType));
        return new TimetableStopImpl(id, tripLabel, plannedArrival, plannedDeparture, plannedPlatform);
    }
}
