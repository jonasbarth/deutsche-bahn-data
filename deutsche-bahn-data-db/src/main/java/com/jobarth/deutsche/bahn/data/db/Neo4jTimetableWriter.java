package com.jobarth.deutsche.bahn.data.db;

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
import org.springframework.stereotype.Service;

@Service
public class Neo4jTimetableWriter implements TimetableWriter {

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
        StationEntity station = stationRepository.findByEva(timetable.getEvaNo());

        for (TimetableStop stop : timetable.getTimetableStops()) {
            TripCategoryLabel tripCategoryLabel = tripCategoryRepository.findByTripCategory(stop.getTripLabel().getTripCategory());
            Arrival arrival = stop.getArrival();
            Departure departure = stop.getDeparture();
            TimetableStopEntity timetableStop = new TimetableStopEntity(stop.getId(), tripCategoryLabel, station,
                    stop.getTripLabel().getTrainNumber(),
                    arrival.getPlannedTimeAsLocalDateTime(), arrival.getChangedTimeAsLocalDateTime(),
                    departure.getPlannedTimeAsLocalDateTime(), departure.getChangedTimeAsLocalDateTime(),
                    arrival.getPlannedPlatform(), departure.getChangedPlatform());
            timetableStopRepository.save(timetableStop);
        }
    }
}
