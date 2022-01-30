package com.jobarth.deutsche.bahn.data.server.filter;

import com.jobarth.deutsche.bahn.data.domain.*;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        TimetableStop mock = mock(TimetableStop.class);
        Departure departureMock = mock(Departure.class);
        when(departureMock.getPlannedTimeAsLocalDateTime()).thenReturn(plannedDeparture);
        when(departureMock.getChangedTimeAsLocalDateTime()).thenReturn(actualDeparture);
        when(mock.getArrival()).thenReturn(mock(Arrival.class));
        when(mock.getTripLabel()).thenReturn(mock(TripLabel.class));

        when(mock.getDeparture()).thenReturn(departureMock);
        return mock;
    }
}