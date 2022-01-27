package com.jobarth.deutsche.bahn.data.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for {@link ImmutableChange}.
 */
public class ImmutableChangeTest {

    private static final String TIMETABLE_STOP_ID = "ID";
    private static final LocalDateTime CHANGED_ARRIVAL = LocalDateTime.now();
    private static final LocalDateTime CHANGED_DEPARTURE = LocalDateTime.now();
    private static final Platform CHANGED_PLATFORM = new ImmutablePlatform("");

    private static final ImmutableChange IMMUTABLE_CHANGE = new ImmutableChange(TIMETABLE_STOP_ID, CHANGED_ARRIVAL, CHANGED_DEPARTURE, CHANGED_PLATFORM);

    @Test
    public void testThatExceptionThrownForNullTimetableStopId() {
        assertThatThrownBy(() -> new ImmutableChange(null, CHANGED_ARRIVAL, CHANGED_DEPARTURE, CHANGED_PLATFORM))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("The timetableStopId must not be null");
    }

    @Test
    public void testThatExceptionThrownForNullChangedArrival() {
        assertThatThrownBy(() -> new ImmutableChange(TIMETABLE_STOP_ID, null, CHANGED_DEPARTURE, CHANGED_PLATFORM))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("The changedArrival must not be null");
    }

    @Test
    public void testThatExceptionThrownForNullChangedDeparture() {
        assertThatThrownBy(() -> new ImmutableChange(TIMETABLE_STOP_ID, CHANGED_ARRIVAL, null, CHANGED_PLATFORM))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("The changedDeparture must not be null");
    }

    @Test
    public void testThatGetTimetableStopIdReturnsTimetableStopId() {
        assertThat(IMMUTABLE_CHANGE.getTimetableStopId()).isEqualTo(TIMETABLE_STOP_ID);
    }

    @Test
    public void testThatGetChangedArrivalReturnsChangedArrival() {
        assertThat(IMMUTABLE_CHANGE.getChangedArrival()).isEqualTo(CHANGED_ARRIVAL);
    }

    @Test
    public void testThatGetChangedDepartureReturnsChangedDeparture() {
        assertThat(IMMUTABLE_CHANGE.getChangedDeparture()).isEqualTo(CHANGED_DEPARTURE);
    }

    @Test
    public void testThatGetChangedPlatformReturnsChangedPlatform() {
        assertThat(IMMUTABLE_CHANGE.getChangedPlatform()).isEqualTo(CHANGED_PLATFORM);
    }

}