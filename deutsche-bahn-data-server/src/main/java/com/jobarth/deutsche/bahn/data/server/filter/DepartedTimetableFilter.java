package com.jobarth.deutsche.bahn.data.server.filter;

import com.google.common.collect.Lists;
import com.jobarth.deutsche.bahn.data.domain.Timetable;
import com.jobarth.deutsche.bahn.data.domain.TimetableStop;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

//I want to create a copy of the original timetable which only has the departures in the past.

/**
 * A type of {@link TimetableFilter} that removes
 */
public class DepartedTimetableFilter implements TimetableFilter {

    @Override
    public Timetable apply(Timetable timetable) {
        Timetable copy = new Timetable(timetable);
        List<TimetableStop> filteredStops = Lists.newLinkedList();
        timetable.getTimetableStops().stream()
                .filter(DepartedTimetableFilter::hasDeparted)
                .forEach(filteredStops::add);

        timetable.getTimetableStops().removeAll(filteredStops);
        copy.setTimetableStops(filteredStops);
        return copy;
    }

    private static boolean hasDeparted(TimetableStop timetableStop) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime plannedDeparture = timetableStop.getDeparture().getPlannedTimeAsLocalDateTime();
        LocalDateTime actualDeparture = timetableStop.getDeparture().getChangedTimeAsLocalDateTime();

        if (actualDeparture != null) {
            return actualDeparture.isBefore(now) && plannedDeparture.isBefore(now);
        }
        return plannedDeparture.isBefore(now);
    }
}
