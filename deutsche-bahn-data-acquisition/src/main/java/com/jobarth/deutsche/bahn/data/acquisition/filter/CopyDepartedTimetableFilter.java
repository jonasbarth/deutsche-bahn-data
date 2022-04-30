package com.jobarth.deutsche.bahn.data.acquisition.filter;

import com.google.common.collect.Lists;
import com.jobarth.deutsche.bahn.data.domain.Timetable;
import com.jobarth.deutsche.bahn.data.domain.TimetableStop;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * A type of {@link TimetableFilter} that removes
 */
@Component
public class CopyDepartedTimetableFilter implements TimetableFilter {

    private LocalDateTime latestDepartureSent = LocalDateTime.now();

    @Override
    public Timetable apply(Timetable timetable) {
        Timetable copy = new Timetable(timetable);
        List<TimetableStop> filteredStops = Lists.newLinkedList();

        copy.getTimetableStops().stream()
                .filter(TimetableStop::hasDeparted)
                .filter(timetableStop -> timetableStop.hasDepartedAfter(latestDepartureSent))
                .forEach(filteredStops::add);

        latestDepartureSent = LocalDateTime.now();

        copy.setTimetableStops(filteredStops);
        return copy;
    }
}
