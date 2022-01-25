package com.jobarth.deutsche.bahn.data.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit tests for {@link TimetableStopImpl}.
 */
class TimetableStopImplTest {

    private static final Platform PLATFORM_9 = new ImmutablePlatform("9");
    private static final String ID = "ID";
    private static final TripLabel TRIP_LABEL = new ImmutableTripLabel(0, TrainType.ICE);

    @Test
    public void testThatExceptionThrownForNullId() {
        assertThatThrownBy(() -> new TimetableStopImpl(null, TRIP_LABEL, LocalDateTime.now(), LocalDateTime.now(), new ImmutablePlatform("")))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("The ID must not be null");
    }

    @Test
    public void testThatExceptionThrownForNullTripLabel() {
        assertThatThrownBy(() -> new TimetableStopImpl(ID, null, LocalDateTime.now(), LocalDateTime.now(), new ImmutablePlatform("")))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("The tripLabel must not be null");
    }

    @Test
    public void testThatExceptionThrownForNullPlannedArrival() {
        assertThatThrownBy(() -> new TimetableStopImpl(ID,  TRIP_LABEL,null, LocalDateTime.now(), new ImmutablePlatform("")))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("The planned arrival must not be null");
    }

    @Test
    public void testThatExceptionThrownForNullPlannedDeparture() {
        assertThatThrownBy(() -> new TimetableStopImpl(ID, TRIP_LABEL, LocalDateTime.now(), null, new ImmutablePlatform("")))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("The planned departure must not be null");
    }

    @Test
    public void testThatExceptionThrownForNullPlannedPlatform() {
        assertThatThrownBy(() -> new TimetableStopImpl(ID, TRIP_LABEL, LocalDateTime.now(), LocalDateTime.now(), null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("The planned platform must not be null");
    }

    @Test
    public void testThatGetIdReturnsExpectedId() {
        TimetableStop timetableStop = new TimetableStopImpl(ID, TRIP_LABEL, LocalDateTime.now(), LocalDateTime.now(), PLATFORM_9);

        assertThat(timetableStop.getId()).isEqualTo(ID);
    }

    @Test
    public void testThatGetTripLabelReturnsExpectedTripLabel() {
        TimetableStop timetableStop = new TimetableStopImpl(ID, TRIP_LABEL, LocalDateTime.now(), LocalDateTime.now(), PLATFORM_9);

        assertThat(timetableStop.getTripLabel()).isEqualTo(TRIP_LABEL);
    }

    @Test
    public void testThatGetPlannedArrivalReturnsExpectedPlannedArrival() {
        LocalDateTime expectedArrival = LocalDateTime.now();
        TimetableStop timetableStop = new TimetableStopImpl(ID,TRIP_LABEL,  expectedArrival, LocalDateTime.now(), PLATFORM_9);

        assertThat(timetableStop.getPlannedArrival()).isEqualTo(expectedArrival);
    }

    @Test
    public void testThatActualArrivalIsPlannedArrivalIfNoUpdate() {
        LocalDateTime expectedArrival = LocalDateTime.now();
        TimetableStop timetableStop = new TimetableStopImpl(ID, TRIP_LABEL, expectedArrival, LocalDateTime.now(), PLATFORM_9);

        assertThat(timetableStop.getActualArrival()).isEqualTo(expectedArrival);
    }

    @Test
    public void testThatActualArrivalIsUpdatedArrival() {
        LocalDateTime plannedArrival = LocalDateTime.now();
        TimetableStop timetableStop = new TimetableStopImpl(ID, TRIP_LABEL, plannedArrival, LocalDateTime.now(), PLATFORM_9);

        LocalDateTime expectedActualArrival = plannedArrival.plusMinutes(10);
        timetableStop.setNewArrival(expectedActualArrival);

        assertThat(timetableStop.getActualArrival()).isEqualTo(expectedActualArrival);
    }


    @Test
    public void testThatGetPlannedDepartureReturnsExpectedPlannedDeparture() {
        LocalDateTime expectedDeparture = LocalDateTime.now();
        TimetableStop timetableStop = new TimetableStopImpl(ID, TRIP_LABEL, LocalDateTime.now(), expectedDeparture, PLATFORM_9);

        assertThat(timetableStop.getPlannedDeparture()).isEqualTo(expectedDeparture);
    }

    @Test
    public void testThatActualDepartureIsPlannedDepartureIfNoUpdate() {
        LocalDateTime expectedDeparture = LocalDateTime.now();
        TimetableStop timetableStop = new TimetableStopImpl(ID, TRIP_LABEL,  LocalDateTime.now(), expectedDeparture, PLATFORM_9);

        assertThat(timetableStop.getActualDeparture()).isEqualTo(expectedDeparture);
    }

    @Test
    public void testThatActualDepartureIsUpdatedDeparture() {
        LocalDateTime plannedDeparture = LocalDateTime.now();
        TimetableStop timetableStop = new TimetableStopImpl(ID, TRIP_LABEL, LocalDateTime.now(), plannedDeparture, PLATFORM_9);

        LocalDateTime expectedActualDeparture = plannedDeparture.plusMinutes(23);
        timetableStop.setNewArrival(expectedActualDeparture);

        assertThat(timetableStop.getActualArrival()).isEqualTo(expectedActualDeparture);
    }

    @Test
    public void testThatGetPlannedPlatformReturnsExpectedPlannedPlatform() {
        TimetableStop timetableStop = new TimetableStopImpl(ID, TRIP_LABEL, LocalDateTime.now(), LocalDateTime.now(), PLATFORM_9);

        assertThat(timetableStop.getPlannedPlatform()).isEqualTo(PLATFORM_9);
    }

    @Test
    public void testThatActualPlatformIsPlannedPlatformIfNoUpdate() {
        TimetableStop timetableStop = new TimetableStopImpl(ID,TRIP_LABEL,  LocalDateTime.now(), LocalDateTime.now(), PLATFORM_9);

        assertThat(timetableStop.getActualPlatform()).isEqualTo(PLATFORM_9);
    }

    @Test
    public void testThatActualPlatformIsUpdatedPlatform() {
        TimetableStop timetableStop = new TimetableStopImpl(ID, TRIP_LABEL, LocalDateTime.now(), LocalDateTime.now(), PLATFORM_9);
        Platform expectedPlatform = new ImmutablePlatform("10");

        timetableStop.setNewPlatform(expectedPlatform);

        assertThat(timetableStop.getActualPlatform()).isEqualTo(expectedPlatform);
    }

}