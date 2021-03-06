package com.jobarth.deutsche.bahn.data.acquisition;

import com.google.common.collect.Sets;
import com.jobarth.deutsche.bahn.data.acquisition.jobs.TimetablePlanJob;
import com.jobarth.deutsche.bahn.data.acquisition.request.TimetableRequestListener;
import com.jobarth.deutsche.bahn.data.domain.Station;
import com.jobarth.deutsche.bahn.data.domain.Timetable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of {@link TimetableManager}.
 */
@Component
public class TimetableManagerImpl implements TimetableRequestListener, TimetableManager {

    private final static Logger LOGGER = LoggerFactory.getLogger(TimetablePlanJob.class);

    private final Set<Timetable> timetables = Sets.newHashSet();

    @Override
    public void onPlan(Timetable timetable) {
        //if the timetable does not exist yet
        if (timetable.getStation() == null) {
            LOGGER.warn("Cannot update the manager with a timetable that has no station.");
            return;
        }
        Optional<Timetable> maybeTimetable = timetables.stream()
                .filter(tt -> tt.getStation().equals(timetable.getStation()))
                .findFirst();

        if (maybeTimetable.isPresent()) {
            LOGGER.info("Extending the {} timetable", maybeTimetable.get().getStation());
            maybeTimetable.get().extend(timetable);
        } else {
            LOGGER.info("No existent timetable yet for {}. Adding it to the manager.", timetable.getStation());
            timetables.add(timetable);
        }
    }

    @Override
    public void onRecentChanges(Timetable timetable) {
        if (timetable.getStation() == null) {
            LOGGER.warn("Cannot apply recent changes from a timetable that has no station.");
            return;
        }
        timetables.stream()
                .filter(tt -> tt.getStation().equals(timetable.getStation()))
                .forEach(tt -> tt.updateTimetable(timetable));
    }

    @Override
    public void onFutureChanges(Timetable timetable) {
        if (timetable.getStation() == null) {
            LOGGER.warn("Cannot apply future changes from a timetable that has no station.");
            return;
        }
        timetables.stream()
                .filter(tt -> tt.getStation().equals(timetable.getStation()))
                .forEach(tt -> tt.updateTimetable(timetable));
    }

    @Override
    public Timetable get(String evaNo) {
        return timetables.stream()
                .filter(timetable -> evaNo.equals(timetable.getEvaNo() ))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Collection<Station> getStations() {
        return timetables.stream()
                .map(timetable -> new Station(timetable.getStation(), timetable.getEvaNo()))
                .collect(Collectors.toList());
    }
}
