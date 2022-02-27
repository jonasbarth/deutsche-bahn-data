package com.jobarth.deutsche.bahn.data.db.api;

import com.jobarth.deutsche.bahn.data.db.domain.StationEntity;
import com.jobarth.deutsche.bahn.data.db.domain.TimetableStopEntity;
import com.jobarth.deutsche.bahn.data.db.repository.StationRepository;
import com.jobarth.deutsche.bahn.data.db.repository.TimetableStopRepository;
import com.jobarth.deutsche.bahn.data.db.repository.TripCategoryRepository;
import com.jobarth.deutsche.bahn.data.domain.Arrival;
import com.jobarth.deutsche.bahn.data.domain.TimetableStop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Implementation of {@link TimetableReader} that fetches data from the neo4j database.
 */
@Component
public class Neo4jTimetableReader implements TimetableReader {

    @Autowired
    StationRepository stationRepository;

    @Autowired
    TripCategoryRepository tripCategoryRepository;

    @Autowired
    TimetableStopRepository timetableStopRepository;

    @Override
    public Collection<TimetableStopEntity> getTimetableStops(String eva) {
        return timetableStopRepository.findByStation(eva);
    }

    @Override
    public Collection<StationEntity> getStations() {
        return stationRepository.findAll();
    }
}
