package com.jobarth.deutsche.bahn.data.db.api;

import com.google.common.collect.Lists;
import com.jobarth.deutsche.bahn.data.domain.Timetable;
import com.jobarth.deutsche.bahn.data.domain.TimetableStop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 *
 */
public class FileTimetableWriter implements TimetableWriter {

    private final static Logger LOGGER = LoggerFactory.getLogger(FileTimetableWriter.class);

    @Override
    public void write(Timetable timetable) {
        //create string that can be written to CSV

        String station = timetable.getStation();
        String evaNo = timetable.getEvaNo();
        String stopId;
        String plannedArrival = "";
        String actualArrival = "";
        String plannedDeparture = "";
        String actualDeparture = "";
        String plannedArrivalPlatform = "";
        String actualArrivalPlatform = "";
        String plannedDeparturePlatform = "";
        String actualDeparturePlatform = "";
        String tripType = "";
        String trainNumber = "";
        String previousStop = "";
        String nextStop = "";
        String finalStop = "";

        StringBuilder stringBuilder = new StringBuilder();
        Collection<String> rows = Lists.newArrayListWithCapacity(timetable.getTimetableStops().size());
        String headerRow = String.join(",", new String[]{"STATION", "EVA_NO", "STOP_ID",
                "TRIP_TYPE", "TRAIN_NUMBER",
                "PLANNED_ARRIVAL", "ACTUAL_ARRIVAL",
                "PLANNED_DEPARTURE", "ACTUAL_DEPARTURE",
                "PLANNED_ARRIVAL_PLATFORM", "ACTUAL_ARRIVAL_PLATFORM",
                "PLANNED_DEPARTURE_PLATFORM", "ACTUAL_DEPARTURE_PLATFORM"
        });
        stringBuilder.append(headerRow).append("\n");

        for (TimetableStop timetableStop: timetable.getTimetableStops()) {
            stopId = timetableStop.getId();
            LOGGER.info("Writing {} to file.", timetableStop);
            LOGGER.info("Arrival is {}.", timetableStop.getArrival());
            if (timetableStop.getArrival() != null) {
                plannedArrival = timetableStop.getArrival().getPlannedTime();
                actualArrival = timetableStop.getArrival().getChangedTime();
                plannedArrivalPlatform = timetableStop.getArrival().getPlannedPlatform();
                actualArrivalPlatform = timetableStop.getArrival().getChangedPlatform();
            }
            LOGGER.info("Departure is {}.", timetableStop.getDeparture());
            if (timetableStop.getDeparture() != null) {
                plannedDeparture = timetableStop.getDeparture().getPlannedTime();
                actualDeparture = timetableStop.getDeparture().getChangedTime();
                plannedDeparturePlatform = timetableStop.getDeparture().getPlannedPlatform();
                actualDeparturePlatform = timetableStop.getDeparture().getChangedPlatform();
            }

            tripType = timetableStop.getTripLabel().getTripCategory();
            trainNumber = timetableStop.getTripLabel().getTrainNumber();

            String row = String.join(",", new String[]{station, evaNo, stopId, tripType, trainNumber,
                    plannedArrival, actualArrival,
                    plannedDeparture, actualDeparture,
                    plannedArrivalPlatform, actualArrivalPlatform,
                    plannedDeparturePlatform, actualDeparturePlatform});
            rows.add(row);
            stringBuilder.append(row).append("\n");

        }
        String dateTime = LocalDateTime.now().toString().replace(":", "-").replace(".", "-");
        File csvOutputFile = new File("output" + dateTime + ".csv");
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(csvOutputFile))) {
            pw.println(stringBuilder.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}