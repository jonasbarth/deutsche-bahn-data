package com.jobarth.deutsche.bahn.data.domain;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link Timetable}.
 */
class TimetableTest {

    private static final String ID_1 = "ID1";
    private static final String ID_2 = "ID2";
    private static final String BERLIN_HBF = "Berlin Hbf";


    @Test
    public void testThatExtendsTimetable() {
        TimetableStop timetableStop1 = mockTimetableStop(ID_1);
        TimetableStop timetableStop2 = mockTimetableStop(ID_2);
        TimetableStop timetableStop3 = mockTimetableStop(ID_1);
        Timetable timetable1 = spy(new Timetable(Lists.newArrayList(timetableStop1), BERLIN_HBF));
        Timetable timetable2 = spy(new Timetable(Lists.newArrayList(), BERLIN_HBF));
        when(timetable2.getTimetableStops()).thenReturn(Lists.newArrayList(timetableStop2, timetableStop3));

        timetable1.extend(timetable2);

        assertThat(timetable1.getTimetableStops()).containsExactly(timetableStop1, timetableStop2);
    }

    @Test
    public void testThatExceptionThrownForTimetabelsWithDifferentStations() {
        Timetable timetable1 = new Timetable(Lists.newArrayList(), BERLIN_HBF);
        Timetable timetable2 = new Timetable(Lists.newArrayList(), "Aachen Hbf");
        assertThatThrownBy(() -> timetable1.extend(timetable2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cannot extend timetable. The given timetable is from Aachen Hbf whereas the one it is supposed to be added to is Berlin Hbf.");
    }

    private static TimetableStop mockTimetableStop(String id) {
        TimetableStop timetableStop = mock(TimetableStop.class);
        when(timetableStop.getId()).thenReturn(id);

        return timetableStop;
    }
}