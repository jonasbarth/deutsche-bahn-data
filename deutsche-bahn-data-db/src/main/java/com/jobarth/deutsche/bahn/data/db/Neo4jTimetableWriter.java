package com.jobarth.deutsche.bahn.data.db;

import com.jobarth.deutsche.bahn.data.db.repository.StationRepository;
import com.jobarth.deutsche.bahn.data.db.repository.TripCategoryRepository;
import com.jobarth.deutsche.bahn.data.domain.Timetable;
import org.springframework.stereotype.Service;

@Service
public class Neo4jTimetableWriter implements TimetableWriter {

    private StationRepository stationRepository;
    private TripCategoryRepository tripCategoryRepository;

    public Neo4jTimetableWriter(StationRepository stationRepository, TripCategoryRepository tripCategoryRepository) {
        this.stationRepository = stationRepository;
        this.tripCategoryRepository = tripCategoryRepository;
    }

    @Override
    public void write(Timetable timetable) {
        System.out.println("Hello");

    }
}
