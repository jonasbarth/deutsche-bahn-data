package com.jobarth.deutsche.bahn.data.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link TimetableImpl}.
 */
public class TimetableImplTest {

    private TimetableImpl timetable;
    private TimetableStop initialTimetableStop;
    private static final Station BERLIN_HBF = new ImmutableStation(1, "Berlin Hbf", new ImmutableLocation(0, 0));
    private static final Station AACHEN_HBF = new ImmutableStation(2, "Aachen Hbf", new ImmutableLocation(1, 1));

    @BeforeEach
    public void beforeEach() {
        timetable = new TimetableImpl();
        LocalDateTime now = LocalDateTime.now();
        initialTimetableStop = mockTimetableStop(BERLIN_HBF, now.minusMinutes(15), now.minusMinutes(15), now.minusMinutes(10), now.minusMinutes(10));
    }

    @Test
    public void testThatExceptionThrownForNullTimetableStop() {
        assertThatThrownBy(() -> timetable.addTimetableStop(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("The timetableStop must not be null");
    }

    @Test
    public void testThatExceptionThrownForTimetableStopWithDifferentStation() {
        timetable.addTimetableStop(initialTimetableStop);
        TimetableStop timetableStopToThrowException = mock(TimetableStopImpl.class);
        when(timetableStopToThrowException.getStation()).thenReturn(AACHEN_HBF);

        assertThatThrownBy(() -> timetable.addTimetableStop(timetableStopToThrowException))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The station of the time table stop does not match the already existing station of the timetable");
    }

    @Test
    public void testThatGetStationReturnsNullWhenNoTimetableStopsAdded() {
        assertThat(timetable.getStation()).isEqualTo(null);
    }

    @Test
    public void testThatGetStationReturnsStationWhenTimetableStopsAdded() {
        timetable.addTimetableStop(initialTimetableStop);

        assertThat(timetable.getStation()).isEqualTo(BERLIN_HBF);
    }

    @Test
    public void testThatGetAllTimetableStopsReturnsAllTimetableStops() {
        timetable.addTimetableStop(initialTimetableStop);
        TimetableStop timetableStop = mock(TimetableStopImpl.class);
        when(timetableStop.getStation()).thenReturn(BERLIN_HBF);

        timetable.addTimetableStop(timetableStop);

        assertThat(timetable.getAllTimetableStops()).containsExactly(initialTimetableStop, timetableStop);
    }

    @Test
    public void testThatGetPlannedDepartureBeforeDate() {
        when(initialTimetableStop.getPlannedDeparture()).thenReturn(LocalDateTime.now().minusMinutes(10));
        TimetableStop timetableStop = mockTimetableStop(BERLIN_HBF, null, null, LocalDateTime.now().plusMinutes(10), null);

        timetable.addTimetableStop(initialTimetableStop);
        timetable.addTimetableStop(timetableStop);

        assertThat(timetable.getPlannedDepartureBefore(LocalDateTime.now())).containsExactly(initialTimetableStop);
    }

    @Test
    public void testThatGetPlannedDepartureAfterDate() {
        when(initialTimetableStop.getPlannedDeparture()).thenReturn(LocalDateTime.now().minusMinutes(10));
        TimetableStop timetableStop = mockTimetableStop(BERLIN_HBF, null, null, LocalDateTime.now().plusMinutes(10), null);

        timetable.addTimetableStop(initialTimetableStop);
        timetable.addTimetableStop(timetableStop);

        assertThat(timetable.getPlannedDepartureAfter(LocalDateTime.now())).containsExactly(timetableStop);
    }

    @Test
    public void testThatGetActualDepartureBeforeDate() {
        when(initialTimetableStop.getPlannedDeparture()).thenReturn(LocalDateTime.now().minusMinutes(10));
        TimetableStop timetableStop = mockTimetableStop(BERLIN_HBF, null, null, null, LocalDateTime.now().plusMinutes(10));

        timetable.addTimetableStop(initialTimetableStop);
        timetable.addTimetableStop(timetableStop);

        assertThat(timetable.getActualDepartureBefore(LocalDateTime.now())).containsExactly(initialTimetableStop);
    }

    @Test
    public void testThatGetActualDepartureAfterDate() {
        when(initialTimetableStop.getPlannedDeparture()).thenReturn(LocalDateTime.now().minusMinutes(10));
        TimetableStop timetableStop = mockTimetableStop(BERLIN_HBF, null, null, null, LocalDateTime.now().plusMinutes(10));

        timetable.addTimetableStop(initialTimetableStop);
        timetable.addTimetableStop(timetableStop);

        assertThat(timetable.getActualDepartureAfter(LocalDateTime.now())).containsExactly(timetableStop);
    }

    @Test
    public void testThatGetActualArrivalBeforeDate() {
        when(initialTimetableStop.getActualArrival()).thenReturn(LocalDateTime.now().minusMinutes(10));
        TimetableStop timetableStop = mockTimetableStop(BERLIN_HBF, null, LocalDateTime.now().plusMinutes(10), null, null);

        timetable.addTimetableStop(initialTimetableStop);
        timetable.addTimetableStop(timetableStop);

        assertThat(timetable.getActualArrivalBefore(LocalDateTime.now())).containsExactly(initialTimetableStop);
    }

    @Test
    public void testThatGetPlannedArrivalBeforeDate() {
        when(initialTimetableStop.getPlannedArrival()).thenReturn(LocalDateTime.now().minusMinutes(10));
        TimetableStop timetableStop = mockTimetableStop(BERLIN_HBF, LocalDateTime.now().plusMinutes(10), null, null, null);

        timetable.addTimetableStop(initialTimetableStop);
        timetable.addTimetableStop(timetableStop);

        assertThat(timetable.getPlannedArrivalBefore(LocalDateTime.now())).containsExactly(initialTimetableStop);
    }

    @Test
    public void testThatGetActualArrivalAfterDate() {
        when(initialTimetableStop.getActualArrival()).thenReturn(LocalDateTime.now().minusMinutes(10));
        TimetableStop timetableStop = mockTimetableStop(BERLIN_HBF, null, LocalDateTime.now().plusMinutes(10), null, null);

        timetable.addTimetableStop(initialTimetableStop);
        timetable.addTimetableStop(timetableStop);

        assertThat(timetable.getActualArrivalAfter(LocalDateTime.now())).containsExactly(timetableStop);
    }

    @Test
    public void testThatGetPlannedArrivalAfterDate() {
        when(initialTimetableStop.getPlannedArrival()).thenReturn(LocalDateTime.now().minusMinutes(10));
        TimetableStop timetableStop = mockTimetableStop(BERLIN_HBF, LocalDateTime.now().plusMinutes(10),null,  null, null);

        timetable.addTimetableStop(initialTimetableStop);
        timetable.addTimetableStop(timetableStop);

        assertThat(timetable.getPlannedArrivalAfter(LocalDateTime.now())).containsExactly(timetableStop);
    }


    private TimetableStop mockTimetableStop(Station station, LocalDateTime plannedArrival, LocalDateTime actualArrival, LocalDateTime plannedDeparture, LocalDateTime actualDeparture) {
        TimetableStop timetableStop = mock(TimetableStop.class);
        when(timetableStop.getStation()).thenReturn(station);
        when(timetableStop.getPlannedArrival()).thenReturn(plannedArrival);
        when(timetableStop.getActualArrival()).thenReturn(actualArrival);
        when(timetableStop.getPlannedDeparture()).thenReturn(plannedDeparture);
        when(timetableStop.getActualDeparture()).thenReturn(actualDeparture);
        return timetableStop;
    }
}