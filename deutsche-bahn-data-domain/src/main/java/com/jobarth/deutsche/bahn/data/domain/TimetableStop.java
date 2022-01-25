package com.jobarth.deutsche.bahn.data.domain;

import java.time.LocalDateTime;

/**
 * A single stop of a train made on its journey. Multiple TimetableStops make up a Timetable.
 */
public interface TimetableStop {

    /**
     * @return the unique ID of this timetable stop.
     */
    public String getId();

    /**
     * @return the {@link TripLabel} of this timetable stop.
     */
    public TripLabel getTripLabel();

    /**
     * @return the planned arrival as a {@link LocalDateTime} to this stop. Will not return {@code null}.
     */
    public LocalDateTime getPlannedArrival();

    /**
     * @return the actual arrival as a {@link LocalDateTime} to this stop. Will not return {@code null}.
     */
    public LocalDateTime getActualArrival();

    /**
     * Updates the current planned arrival as a {@link LocalDateTime} time of the train to the stop.
     * @param newArrival the new arrival as a{@link LocalDateTime} time of the train to the stop.
     */
    public void setNewArrival(LocalDateTime newArrival);

    /**
     * @return the planned departure as a {@link LocalDateTime} from this stop. Will not return {@code null}.
     */
    public LocalDateTime getPlannedDeparture();

    /**
     * @return the actual departure as a {@link LocalDateTime} from this stop. Will not return {@code null}.
     */
    public LocalDateTime getActualDeparture();

    /**
     * Updates the current planned departure {@link LocalDateTime} time of the train from the stop.
     * @param newDeparture the new departure as {@link LocalDateTime} time of the train from the stop.
     */
    public void setNewDeparture(LocalDateTime newDeparture);

    /**
     * @return the planned {@link Platform} of this stop. Will not return {@code null}.
     */
    public Platform getPlannedPlatform();

    /**
     * @return the actual {@link Platform} of this stop. Will not return {@code null}.
     */
    public Platform getActualPlatform();

    /**
     * Updates the current planned {@link Platform} of the train at the stop.
     * @param newPlatform the new {@link Platform} of the train at the stop.
     */
    public void setNewPlatform(Platform newPlatform);
}
