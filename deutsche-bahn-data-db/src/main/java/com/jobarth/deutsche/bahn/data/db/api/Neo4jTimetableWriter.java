package com.jobarth.deutsche.bahn.data.db.api;

import com.google.common.collect.Lists;
import com.jobarth.deutsche.bahn.data.db.domain.StationEntity;
import com.jobarth.deutsche.bahn.data.db.domain.TimetableStopEntity;
import com.jobarth.deutsche.bahn.data.db.domain.TripCategoryLabel;
import com.jobarth.deutsche.bahn.data.db.repository.StationRepository;
import com.jobarth.deutsche.bahn.data.db.repository.TimetableStopRepository;
import com.jobarth.deutsche.bahn.data.db.repository.TripCategoryRepository;
import com.jobarth.deutsche.bahn.data.domain.Arrival;
import com.jobarth.deutsche.bahn.data.domain.Departure;
import com.jobarth.deutsche.bahn.data.domain.Timetable;
import com.jobarth.deutsche.bahn.data.domain.TimetableStop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class Neo4jTimetableWriter implements TimetableWriter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Neo4jTimetableWriter.class);

    private StationRepository stationRepository;
    private TripCategoryRepository tripCategoryRepository;
    private TimetableStopRepository timetableStopRepository;

    public Neo4jTimetableWriter(StationRepository stationRepository, TripCategoryRepository tripCategoryRepository, TimetableStopRepository timetableStopRepository) {
        this.stationRepository = stationRepository;
        this.tripCategoryRepository = tripCategoryRepository;
        this.timetableStopRepository = timetableStopRepository;
    }

    @Override
    public void write(Timetable timetable) {
        LOGGER.info("Writing timetable for {} into neo4j database", timetable.getStation());
        StationEntity station = stationRepository.findByEva(timetable.getEvaNo());

        Collection<TimetableStopEntity> stopsToSave = Lists.newArrayList();
        for (TimetableStop stop : timetable.getTimetableStops()) {
            StationEntity nextStation = stationRepository.findByName(stop.getNextStop());
            StationEntity previousStation = stationRepository.findByName(stop.getPreviousStop());
            LOGGER.info("Previous stop: {}", stop.getPreviousStop());
            LOGGER.info("Next stop: {}", stop.getNextStop());
            TripCategoryLabel tripCategoryLabel = tripCategoryRepository.findByTripCategory(stop.getTripLabel().getTripCategory());
            Arrival arrival = stop.getArrival();
            Departure departure = stop.getDeparture();
            TimetableStopEntity timetableStop = new TimetableStopEntity(stop.getId(), tripCategoryLabel,
                    station, nextStation, previousStation,
                    stop.getTripLabel().getTrainNumber(),
                    arrival.getPlannedTimeAsLocalDateTime(), arrival.getChangedTimeAsLocalDateTime(),
                    departure.getPlannedTimeAsLocalDateTime(), departure.getChangedTimeAsLocalDateTime(),
                    arrival.getPlannedPlatform(), departure.getChangedPlatform());
            stopsToSave.add(timetableStop);
        }
        LOGGER.info("Saving {} stops to the database.", stopsToSave.size());
        timetableStopRepository.saveAll(stopsToSave);
    }
}
