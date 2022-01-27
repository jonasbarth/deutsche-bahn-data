package com.jobarth.deutsche.bahn.data.server;

import com.google.common.collect.ImmutableMap;
import com.jobarth.deutsche.bahn.data.domain.*;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    public TimetableStop parsePlannedTimetableStop() {
        String id = timetableStop.getAttributes().getNamedItem(ID).getNodeValue();
        String trainType = "";
        String trainNumber = "";
        String plannedArrivalTime = "";
        String plannedDepartureTime = "";
        String plannedPlatformAttr = "";
        //how can I distinguish between changed and planned time? By injecting another class that extracts the arrival and departure time from the node
        //Can I specify the type of expected attributes? Give them from the outside? Or just have a different method. Might be easier.
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

    @Override
    public Change parseChangedTimetableStop() {
        ImmutableChange.Builder builder = new ImmutableChange.Builder();
        builder.timetableStopId(timetableStop.getAttributes().getNamedItem(ID).getNodeValue());

        Set<String> childNodes = IntStream.range(0, timetableStop.getChildNodes().getLength())
                .mapToObj(index -> timetableStop.getChildNodes().item(index))
                .map(Node::getNodeName)
                .collect(Collectors.toSet());

        if (childNodes.contains(ARRIVAL_NODE)) {
            addArrival(builder);
        }
        if (childNodes.contains(DEPARTURE_NODE)) {
            addDeparture(builder);
        }


        //return new TimetableStopImpl(id, tripLabel, plannedArrival, plannedDeparture, plannedPlatform);
        return builder.build();
    }

    private void addArrival(ImmutableChange.Builder builder) {
        Map<String, Consumer<String>> arrivalMap = ImmutableMap.of(
                "ct", builder::changedArrival,
                "cp", builder::changedPlatform
        );

        Set<String> expectedAttributes = arrivalMap.keySet();
        //the arrival or departure nodes might not always be present
        System.out.println("Fetching arrival node " + timetableStop.getAttributes().getNamedItem(ID).getNodeValue());
        Node arrival = IntStream.range(0, timetableStop.getChildNodes().getLength())
                .mapToObj(index -> timetableStop.getChildNodes().item(index))
                .filter(node -> node.getNodeName().equals(ARRIVAL_NODE))
                .findFirst()
                .get();

        NamedNodeMap arrivalAttributes = arrival.getAttributes();

        IntStream.range(0, arrivalAttributes.getLength())
                .mapToObj(index -> arrivalAttributes.item(index).getNodeName())
                .filter(expectedAttributes::contains)
                .forEach(attribute -> {
                    arrivalMap
                            .get(attribute)
                            .accept(arrivalAttributes.getNamedItem(attribute).getNodeValue());
                });
    }

    private void addDeparture(ImmutableChange.Builder builder) {
        Map<String, Consumer<String>> departureMap = ImmutableMap.of(
                "ct", builder::changedDeparture,
                "cp", builder::changedPlatform
        );

        Set<String> expectedAttributes = departureMap.keySet();

        System.out.println("Fetching departure node " + timetableStop.getAttributes().getNamedItem(ID).getNodeValue());
        Node departure = IntStream.range(0, timetableStop.getChildNodes().getLength())
                .mapToObj(index -> timetableStop.getChildNodes().item(index))
                .filter(node -> node.getNodeName().equals(DEPARTURE_NODE))
                .findFirst()
                .get();

        NamedNodeMap departureAttributes = departure.getAttributes();

        IntStream.range(0, departureAttributes.getLength())
                .mapToObj(index -> departureAttributes.item(index).getNodeName())
                .filter(expectedAttributes::contains)
                .forEach(attribute -> {
                    departureMap
                            .get(attribute)
                            .accept(departureAttributes.getNamedItem(attribute).getNodeValue());
                });

    }

    private static String nullIfEmpty(String toCheck) {
        return toCheck.equals("") ? null : toCheck;
    }
}
