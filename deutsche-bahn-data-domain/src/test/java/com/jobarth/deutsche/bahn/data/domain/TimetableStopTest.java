package com.jobarth.deutsche.bahn.data.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TimetableStopTest {


    @Test
    public void testThatNextAndPreviousStopCorrectWithoutChangedPath() {
        Arrival arrival = mockArrival("Nauen|Berlin Ostbahnhof", null);
        Departure departure = mockDeparture("Berlin-Spandau|Stendal Hbf|Wolfsburg Hbf|Hannover Hbf|Minden(Westf)|Bad Oeynhausen|Osnabr&#252;ck Hbf|Rheine|Bad Bentheim|Hengelo|Almelo|Deventer|Apeldoorn|Amersfoort Centraal|Hilversum|Amsterdam Centraal", null);

        TimetableStop stop = new TimetableStop(null, arrival, departure, null);

        assertThat(stop.getPreviousStop()).isEqualTo("Berlin Ostbahnhof");
        assertThat(stop.getNextStop()).isEqualTo("Berlin-Spandau");
    }

    @Test
    public void testThatNextAndPreviousStopCorrectWithChangedPath() {
        Arrival arrival = mockArrival("Berlin Ostbahnhof", "Berlin Hbf");
        Departure departure = mockDeparture("Berlin-Spandau|Stendal Hbf|Wolfsburg Hbf|Hannover Hbf|Minden(Westf)|Bad Oeynhausen|Osnabr&#252;ck Hbf|Rheine|Bad Bentheim|Hengelo|Almelo|Deventer|Apeldoorn|Amersfoort Centraal|Hilversum|Amsterdam Centraal", "Berlin Ostbahnhof");

        TimetableStop stop = new TimetableStop(null, arrival, departure, null);

        assertThat(stop.getPreviousStop()).isEqualTo("Berlin Hbf");
        assertThat(stop.getNextStop()).isEqualTo("Berlin Ostbahnhof");
    }

    @Test
    public void testThatNextStopNullForEmptyArrival() {
        Arrival arrival = mockArrival("", null);

        TimetableStop stop = new TimetableStop(null, arrival, null, null);

        assertThat(stop.getPreviousStop()).isNull();
    }

    @Test
    public void testThatNextStopNullForEmptyDeparture() {
        Departure departure = mockDeparture("", null);

        TimetableStop stop = new TimetableStop(null, null, departure, null);

        assertThat(stop.getNextStop()).isNull();
    }

    private static Arrival mockArrival(String plannedPath, String changedPath) {
        Arrival mockArrival = mock(Arrival.class);
        when(mockArrival.getPlannedPath()).thenReturn(plannedPath);
        when(mockArrival.getChangedPath()).thenReturn(changedPath);
        return mockArrival;
    }

    private static Departure mockDeparture(String plannedPath, String changedPath) {
        Departure mockDeparture = mock(Departure.class);
        when(mockDeparture.getPlannedPath()).thenReturn(plannedPath);
        when(mockDeparture.getChangedPath()).thenReturn(changedPath);
        return mockDeparture;
    }

}