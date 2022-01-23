package com.jobarth.deutsche.bahn.data.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit tests for {@link ImmutableStation}.
 */
class ImmutableStationTest {

    @Test
    public void testThatExceptionThrownForNullName() {
        assertThatThrownBy(() -> new ImmutableStation(0, null, new ImmutableLocation(0, 0)))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("The station name must not be null");
    }

    @Test
    public void testThatExceptionThrownForNullLocation() {
        assertThatThrownBy(() -> new ImmutableStation(0, "", null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("The location must not be null");
    }

    @Test
    public void testThatGetStationNameReturnsCorrectName() {
        String expectedStationName = "Berlin Hbf";
        ImmutableStation station = new ImmutableStation(0, expectedStationName, new ImmutableLocation(0, 0));

        assertThat(station.getName()).isEqualTo(expectedStationName);
    }

    @Test
    public void testThatGetEvaNumberReturnsCorrectNumber() {
        int expectedEvaNumber = 13;
        ImmutableStation station = new ImmutableStation(expectedEvaNumber, "", new ImmutableLocation(0, 0));

        assertThat(station.getEvaNumber()).isEqualTo(expectedEvaNumber);
    }

    @Test
    public void testThatGetLocationReturnsCorrectLocation() {
        Location expectedLocation = new ImmutableLocation(13.45, 65.12);
        ImmutableStation station = new ImmutableStation(0, "", expectedLocation);

        assertThat(station.getLocation()).isEqualTo(expectedLocation);
    }
}