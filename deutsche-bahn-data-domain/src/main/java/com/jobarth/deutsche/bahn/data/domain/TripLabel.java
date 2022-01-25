package com.jobarth.deutsche.bahn.data.domain;

/**
 * Information about the trip of a train.
 */
public interface TripLabel {

    /**
     * @return the number of the train associated with this trip.
     */
    public int getTripNumber();

    /**
     * @return the {@link TrainType} of this trip.
     */
    public TrainType getTrainType();
}
