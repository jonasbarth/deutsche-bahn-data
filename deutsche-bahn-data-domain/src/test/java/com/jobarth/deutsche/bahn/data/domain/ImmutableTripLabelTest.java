package com.jobarth.deutsche.bahn.data.domain;

import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for {@link ImmutableTripLabel}.
 */
public class ImmutableTripLabelTest {

    @Test
    public void testThatExceptionThrownForNullTrainType() {
        assertThatThrownBy(() -> new ImmutableTripLabel(0, null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("The trainType must not be null");
    }

    @Test
    public void testThatGetTrainNumberReturnsExpectedTrainNumber() {
        int expectedTripNumber = 10;
        ImmutableTripLabel tripLabel = new ImmutableTripLabel(expectedTripNumber, TrainType.ICE);

        assertThat(tripLabel.getTripNumber()).isEqualTo(expectedTripNumber);
    }

    @Test
    public void testThatGetTrainTypeReturnsExpectedTrainType() {
        TrainType expectedTrainType = TrainType.RB;
        ImmutableTripLabel tripLabel = new ImmutableTripLabel(0, expectedTrainType);

        assertThat(tripLabel.getTrainType()).isEqualTo(expectedTrainType);
    }

}