package com.jobarth.deutsche.bahn.data.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit tests for {@link ImmutableLocation}.
 */
class ImmutableLocationTest {

    @Test
    public void assertThatExceptionThrownForIllegalLongitude() {
        double illegalLongitude = Location.MAX_LONGITUDE + 1;
        assertThatThrownBy(() -> new ImmutableLocation(illegalLongitude, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(String.format("The given longitude %f is not within the accepted range %s", illegalLongitude, Location.LEGAL_LONGITUDES));
    }

    @Test
    public void assertThatExceptionThrownForIllegalLatitude() {
        double illegalLatitude = Location.MAX_LATITUDE + 1;
        assertThatThrownBy(() -> new ImmutableLocation(0, illegalLatitude))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(String.format("The given latitude %f is not within the accepted range %s", illegalLatitude, Location.LEGAL_LATITUDES));
    }

    @Test
    public void testThatGetLongitudeReturnsExpectedLongitude() {
        double expectedLongitude = 12.45;
        Location location = new ImmutableLocation(expectedLongitude, 0);

        assertThat(location.getLongitude()).isEqualTo(expectedLongitude);
    }

    @Test
    public void testThatGetLatitudeReturnsExpectedLatitude() {
        double expectedLatitude = 12.45;
        Location location = new ImmutableLocation(0, expectedLatitude);

        assertThat(location.getLatitude()).isEqualTo(expectedLatitude);
    }

    @Test
    public void testThatEqualsTrueForSameLonLat() {
        Location location1 = new ImmutableLocation(0, 0);
        Location location2 = new ImmutableLocation(0, 0);

        assertThat(location1.equals(location2)).isTrue();
        assertThat(location2.equals(location1)).isTrue();
        assertThat(location1.equals(location1)).isTrue();
    }

}