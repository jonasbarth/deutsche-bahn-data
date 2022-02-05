package com.jobarth.deutsche.bahn.data.acquisition.filter;

import com.jobarth.deutsche.bahn.data.domain.*;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DepartedTimetableFilterTest {


    @Test
    public void testThatNoFutureDepartedTimeInTimetable() {
        TimetableStop futureStop = mockTimetableStop(LocalDateTime.now().plusMinutes(10), LocalDateTime.now().plusMinutes(12));
        TimetableStop pastStop = mockTimetableStop(LocalDateTime.now().minusMinutes(10), LocalDateTime.now().minusMinutes(4));
        DepartedTimetableFilter filter = new DepartedTimetableFilter();
        Timetable timetable = new Timetable(Lists.newArrayList(futureStop, pastStop), "Berlin Hbf");

        Timetable filteredTimetable = filter.apply(timetable);
        List<TimetableStop> pastStops = filteredTimetable.getTimetableStops();

        assertThat(filteredTimetable).isNotEqualTo(timetable);
        assertThat(pastStops).containsExactly(pastStop);
    }

    private static TimetableStop mockTimetableStop(LocalDateTime plannedDeparture, LocalDateTime actualDeparture) {
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyMMddHHmm");
        return new TimetableStop("", new Arrival(),
                new Departure(DATE_TIME_FORMATTER.format(actualDeparture), "", DATE_TIME_FORMATTER.format(plannedDeparture), "", "", ""),
                new TripLabel("", "", "", "", ""));
    }
}