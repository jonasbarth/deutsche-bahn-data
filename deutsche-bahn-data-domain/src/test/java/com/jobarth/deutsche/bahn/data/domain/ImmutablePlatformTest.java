package com.jobarth.deutsche.bahn.data.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit tests for {@link ImmutablePlatform}.
 */
class ImmutablePlatformTest {

    @Test
    public void testThatGetNumberReturnsCorrectNumber() {
        String expectedPlatform = "42a";
        Platform platform = new ImmutablePlatform(expectedPlatform);

        assertThat(platform.getNumber()).isEqualTo(expectedPlatform);
    }

    @Test
    public void testThatExceptionThrownForWhenNullGiven() {
        assertThatThrownBy(() -> new ImmutablePlatform(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("The platformNumber must not be null");
    }
}