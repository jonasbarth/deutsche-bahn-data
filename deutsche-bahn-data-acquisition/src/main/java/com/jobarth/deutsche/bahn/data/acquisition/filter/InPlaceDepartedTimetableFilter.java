package com.jobarth.deutsche.bahn.data.acquisition.filter;

import com.google.common.collect.Lists;
import com.jobarth.deutsche.bahn.data.domain.Timetable;
import com.jobarth.deutsche.bahn.data.domain.TimetableStop;

import java.util.List;

/**
 * A type of {@link TimetableFilter} that removes
 */
public class InPlaceDepartedTimetableFilter implements TimetableFilter {

    @Override
    public Timetable apply(Timetable timetable) {
        Timetable copy = new Timetable(timetable);
        List<TimetableStop> filteredStops = Lists.newLinkedList();
        copy.getTimetableStops().stream()
                .filter(TimetableStop::hasDeparted)
                .forEach(filteredStops::add);

        timetable.remove(filteredStops);
        copy.setTimetableStops(filteredStops);
        return copy;
    }
}
