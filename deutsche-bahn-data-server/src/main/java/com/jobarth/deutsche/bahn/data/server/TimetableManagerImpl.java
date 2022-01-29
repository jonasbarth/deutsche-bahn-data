package com.jobarth.deutsche.bahn.data.server;

import com.google.common.collect.Sets;
import com.jobarth.deutsche.bahn.data.domain.Timetable;
import com.jobarth.deutsche.bahn.data.server.jobs.TimetablePlanJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Set;

/**
 * Implementation of {@link TimetableManager}.
 */
public class TimetableManagerImpl implements TimetableRequestListener {

    private final static Logger LOGGER = LoggerFactory.getLogger(TimetablePlanJob.class);

    private final Set<Timetable> timetables = Sets.newHashSet();

    public Timetable getFirst() {
        return timetables.stream().findFirst().get();
    }

    @Override
    public void onPlan(Timetable timetable) {
        //if the timetable does not exist yet
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
        timetables.stream()
                .filter(tt -> tt.getStation().equals(timetable.getStation()))
                .forEach(tt -> tt.updateTimetable(timetable));
    }

    @Override
    public void onFutureChanges(Timetable timetable) {
        timetables.stream()
                .filter(tt -> tt.getStation().equals(timetable.getStation()))
                .forEach(tt -> tt.updateTimetable(timetable));
    }
}
