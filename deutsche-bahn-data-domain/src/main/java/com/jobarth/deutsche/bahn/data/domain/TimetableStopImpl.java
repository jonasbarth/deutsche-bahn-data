package com.jobarth.deutsche.bahn.data.domain;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Implementation of {@link TimetableStop}.
 */
public class TimetableStopImpl implements TimetableStop {

    private final String id;
    private final TripLabel tripLabel;
    private final LocalDateTime plannedArrival;
    private LocalDateTime actualArrival;
    private final LocalDateTime plannedDeparture;
    private LocalDateTime actualDeparture;
    private final Platform plannedPlatform;
    private Platform actualPlatform;

    public TimetableStopImpl(String id, TripLabel tripLabel, LocalDateTime plannedArrival, LocalDateTime plannedDeparture, Platform plannedPlatform) {
        Objects.requireNonNull(id, "The ID must not be null");
        Objects.requireNonNull(tripLabel, "The tripLabel must not be null");
        Objects.requireNonNull(plannedArrival, "The planned arrival must not be null");
        Objects.requireNonNull(plannedDeparture, "The planned departure must not be null");
        Objects.requireNonNull(plannedPlatform, "The planned platform must not be null");
        this.id = id;
        this.tripLabel = tripLabel;
        this.plannedArrival = plannedArrival;
        this.plannedDeparture = plannedDeparture;
        this.plannedPlatform = plannedPlatform;

        this.actualArrival = plannedArrival;
        this.actualDeparture = plannedDeparture;
        this.actualPlatform = plannedPlatform;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public TripLabel getTripLabel() {
        return tripLabel;
    }

    @Override
    public LocalDateTime getPlannedArrival() {
        return plannedArrival;
    }

    @Override
    public LocalDateTime getActualArrival() {
        return actualArrival;
    }

    @Override
    public void setNewArrival(LocalDateTime newArrival) {
        actualArrival = newArrival;
    }

    @Override
    public LocalDateTime getPlannedDeparture() {
        return plannedDeparture;
    }

    @Override
    public LocalDateTime getActualDeparture() {
        return actualDeparture;
    }

    @Override
    public void setNewDeparture(LocalDateTime newDeparture) {
        actualDeparture = newDeparture;
    }

    @Override
    public Platform getPlannedPlatform() {
        return plannedPlatform;
    }

    @Override
    public Platform getActualPlatform() {
        return actualPlatform;
    }

    @Override
    public void setNewPlatform(Platform newPlatform) {
        actualPlatform = newPlatform;
    }
}
