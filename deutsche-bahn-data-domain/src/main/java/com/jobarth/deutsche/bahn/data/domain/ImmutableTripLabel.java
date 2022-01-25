package com.jobarth.deutsche.bahn.data.domain;

import java.util.Objects;

/**
 * An immutable implementation of {@link TripLabel}.
 */
public class ImmutableTripLabel implements TripLabel {

    private final int tripNumber;
    private final TrainType trainType;

    /**
     * Constructor for {@link ImmutableTripLabel}.
     * @param tripNumber the trip number of the trip.
     * @param trainType the {@link TrainType} of this trip. Must not be null.
     */
    public ImmutableTripLabel(int tripNumber, TrainType trainType) {
        Objects.requireNonNull(trainType, "The trainType must not be null");
        this.tripNumber = tripNumber;
        this.trainType = trainType;
    }

    @Override
    public int getTripNumber() {
        return tripNumber;
    }

    @Override
    public TrainType getTrainType() {
        return trainType;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof TripLabel) {
            TripLabel otherTripLabel = (TripLabel) other;
            return this.getTripNumber() == otherTripLabel.getTripNumber()
                    && this.getTrainType().equals(otherTripLabel.getTrainType());
        }
        return false;
    }
}
