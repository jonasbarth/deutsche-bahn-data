package com.jobarth.deutsche.bahn.data.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit tests for {@link TimetableStopImpl}.
 */
class TimetableStopImplTest {

    private static final Station BERLIN_HBF = new ImmutableStation(12, "Berlin Hbf", new ImmutableLocation(0, 0));
    private static final Platform PLATFORM_9 = new ImmutablePlatform("9");
    private static final String ID = "ID";

    @Test
    public void testThatExceptionThrownForNullId() {
        assertThatThrownBy(() -> new TimetableStopImpl(null, BERLIN_HBF, LocalDateTime.now(), LocalDateTime.now(), new ImmutablePlatform("")))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("The ID must not be null");
    }

    @Test
    public void testThatExceptionThrownForNullStation() {
        assertThatThrownBy(() -> new TimetableStopImpl(ID, null, LocalDateTime.now(), LocalDateTime.now(), new ImmutablePlatform("")))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("The station must not be null");
    }

    @Test
    public void testThatExceptionThrownForNullPlannedArrival() {
        assertThatThrownBy(() -> new TimetableStopImpl(ID, BERLIN_HBF, null, LocalDateTime.now(), new ImmutablePlatform("")))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("The planned arrival must not be null");
    }

    @Test
    public void testThatExceptionThrownForNullPlannedDeparture() {
        assertThatThrownBy(() -> new TimetableStopImpl(ID, BERLIN_HBF, LocalDateTime.now(), null, new ImmutablePlatform("")))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("The planned departure must not be null");
    }

    @Test
    public void testThatExceptionThrownForNullPlannedPlatform() {
        assertThatThrownBy(() -> new TimetableStopImpl(ID, BERLIN_HBF, LocalDateTime.now(), LocalDateTime.now(), null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("The planned platform must not be null");
    }

    @Test
    public void testThatGetIdReturnsExpectedId() {
        TimetableStop timetableStop = new TimetableStopImpl(ID, BERLIN_HBF, LocalDateTime.now(), LocalDateTime.now(), PLATFORM_9);

        assertThat(timetableStop.getId()).isEqualTo(ID);
    }

    @Test
    public void testThatGetStationReturnsExpectedStation() {
        TimetableStop timetableStop = new TimetableStopImpl(ID, BERLIN_HBF, LocalDateTime.now(), LocalDateTime.now(), PLATFORM_9);

        assertThat(timetableStop.getStation()).isEqualTo(BERLIN_HBF);
    }

    @Test
    public void testThatGetPlannedArrivalReturnsExpectedPlannedArrival() {
        LocalDateTime expectedArrival = LocalDateTime.now();
        TimetableStop timetableStop = new TimetableStopImpl(ID, BERLIN_HBF, expectedArrival, LocalDateTime.now(), PLATFORM_9);

        assertThat(timetableStop.getPlannedArrival()).isEqualTo(expectedArrival);
    }

    @Test
    public void testThatActualArrivalIsPlannedArrivalIfNoUpdate() {
        LocalDateTime expectedArrival = LocalDateTime.now();
        TimetableStop timetableStop = new TimetableStopImpl(ID, BERLIN_HBF, expectedArrival, LocalDateTime.now(), PLATFORM_9);

        assertThat(timetableStop.getActualArrival()).isEqualTo(expectedArrival);
    }

    @Test
    public void testThatActualArrivalIsUpdatedArrival() {
        LocalDateTime plannedArrival = LocalDateTime.now();
        TimetableStop timetableStop = new TimetableStopImpl(ID, BERLIN_HBF, plannedArrival, LocalDateTime.now(), PLATFORM_9);

        LocalDateTime expectedActualArrival = plannedArrival.plusMinutes(10);
        timetableStop.setNewArrival(expectedActualArrival);

        assertThat(timetableStop.getActualArrival()).isEqualTo(expectedActualArrival);
    }


    @Test
    public void testThatGetPlannedDepartureReturnsExpectedPlannedDeparture() {
        LocalDateTime expectedDeparture = LocalDateTime.now();
        TimetableStop timetableStop = new TimetableStopImpl(ID, BERLIN_HBF, LocalDateTime.now(), expectedDeparture, PLATFORM_9);

        assertThat(timetableStop.getPlannedDeparture()).isEqualTo(expectedDeparture);
    }

    @Test
    public void testThatActualDepartureIsPlannedDepartureIfNoUpdate() {
        LocalDateTime expectedDeparture = LocalDateTime.now();
        TimetableStop timetableStop = new TimetableStopImpl(ID, BERLIN_HBF, LocalDateTime.now(), expectedDeparture, PLATFORM_9);

        assertThat(timetableStop.getActualDeparture()).isEqualTo(expectedDeparture);
    }

    @Test
    public void testThatActualDepartureIsUpdatedDeparture() {
        LocalDateTime plannedDeparture = LocalDateTime.now();
        TimetableStop timetableStop = new TimetableStopImpl(ID, BERLIN_HBF, LocalDateTime.now(), plannedDeparture, PLATFORM_9);

        LocalDateTime expectedActualDeparture = plannedDeparture.plusMinutes(23);
        timetableStop.setNewArrival(expectedActualDeparture);

        assertThat(timetableStop.getActualArrival()).isEqualTo(expectedActualDeparture);
    }

    @Test
    public void testThatGetPlannedPlatformReturnsExpectedPlannedPlatform() {
        TimetableStop timetableStop = new TimetableStopImpl(ID, BERLIN_HBF, LocalDateTime.now(), LocalDateTime.now(), PLATFORM_9);

        assertThat(timetableStop.getPlannedPlatform()).isEqualTo(PLATFORM_9);
    }

    @Test
    public void testThatActualPlatformIsPlannedPlatformIfNoUpdate() {
        TimetableStop timetableStop = new TimetableStopImpl(ID, BERLIN_HBF, LocalDateTime.now(), LocalDateTime.now(), PLATFORM_9);

        assertThat(timetableStop.getActualPlatform()).isEqualTo(PLATFORM_9);
    }

    @Test
    public void testThatActualPlatformIsUpdatedPlatform() {
        TimetableStop timetableStop = new TimetableStopImpl(ID, BERLIN_HBF, LocalDateTime.now(), LocalDateTime.now(), PLATFORM_9);
        Platform expectedPlatform = new ImmutablePlatform("10");

        timetableStop.setNewPlatform(expectedPlatform);

        assertThat(timetableStop.getActualPlatform()).isEqualTo(expectedPlatform);
    }

}