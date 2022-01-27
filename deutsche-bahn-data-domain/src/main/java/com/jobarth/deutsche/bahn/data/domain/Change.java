package com.jobarth.deutsche.bahn.data.domain;

import java.time.LocalDateTime;

/**
 * Holds a future or recent change for a timetable stop.
 */
public interface Change {

    /**
     * @return the ID of the {@link TimetableStop} that this change is for. Will not be null.
     */
    public String getTimetableStopId();

    /**
     * @return the changed arrival time as a {@link LocalDateTime}. Will not be null.
     */
    public LocalDateTime getChangedArrival();

    /**
     * @return the changed departure time as a {@link LocalDateTime}. Will not be null.
     */
    public LocalDateTime getChangedDeparture();

    /**
     * @return the changed {@link Platform}. Will return {@code null} if no platform change will occur.
     */
    public Platform getChangedPlatform();
}
