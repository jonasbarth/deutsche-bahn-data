package com.jobarth.deutsche.bahn.data.acquisition.filter;

import com.jobarth.deutsche.bahn.data.domain.*;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test for {@link InPlaceDepartedTimetableFilter}.
 */
public class InPlaceDepartedTimetableFilterTest {

    @Test
    public void testThatDepartedStopsRemoved() {
        TimetableStop futureStop = mockTimetableStop("id1", LocalDateTime.now().plusMinutes(10), LocalDateTime.now().plusMinutes(12));
        TimetableStop pastStop = mockTimetableStop("id2", LocalDateTime.now().minusMinutes(10), LocalDateTime.now().minusMinutes(4));
        TimetableStop pastStop2 = mockTimetableStop("id3", LocalDateTime.now().minusMinutes(11), LocalDateTime.now().minusMinutes(5));
        InPlaceDepartedTimetableFilter filter = new InPlaceDepartedTimetableFilter();
        Timetable timetable = new Timetable(Lists.newArrayList(futureStop, pastStop, pastStop2), "Berlin Hbf");

        Timetable filteredTimetable = filter.apply(timetable);
        List<TimetableStop> pastStops = filteredTimetable.getTimetableStops();

        assertThat(filteredTimetable).isNotEqualTo(timetable);
        assertThat(pastStops).containsExactly(pastStop, pastStop2);
        assertThat(timetable.getTimetableStops()).doesNotContainAnyElementsOf(pastStops);
        assertThat(timetable.getTimetableStops()).containsExactly(futureStop);
    }

    private static TimetableStop mockTimetableStop(String id, LocalDateTime plannedDeparture, LocalDateTime actualDeparture) {
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyMMddHHmm");
        return new TimetableStop(id, new Arrival(),
                new Departure(DATE_TIME_FORMATTER.format(actualDeparture), "", DATE_TIME_FORMATTER.format(plannedDeparture), "", "", ""),
                new TripLabel("", "", "", "", ""));
    }
}