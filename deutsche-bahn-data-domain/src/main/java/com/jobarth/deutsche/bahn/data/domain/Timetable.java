package com.jobarth.deutsche.bahn.data.domain;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * A timetable for a specific station for a single day.
 */
public interface Timetable {

    /**
     * Adds a {@link TimetableStop} to the timetable. The first stop added will set the {@link Station} for the entire timetable.
     * Subsequent stops must have the same {@link Station} as the first stop.
     * @param timetableStop the {@link TimetableStop} to be added to the timetable. Must not be {@code null}.
     */
    public void addTimetableStop(TimetableStop timetableStop);

    /**
     * @return the {@link Station} of this timetable. Will return {@code null} if no {@link TimetableStop} exists in the
     * timetable.
     */
    public Station getStation();

    /**
     * @return all {@link TimetableStop} for this day. Will not return {@code null}.
     */
    public Collection<TimetableStop> getAllTimetableStops();

    /**
     * @param latestDepartureTime the {@link LocalDateTime} which is the latest departure time to be included.
     * @return all {@link TimetableStop} where the planned departure time is before latestDepartureTime. Will not return {@code null}.
     */
    public Collection<TimetableStop> getPlannedDepartureBefore(LocalDateTime latestDepartureTime);

    /**
     * @param latestDepartureTime the {@link LocalDateTime} which is the latest departure time to be included.
     * @return all {@link TimetableStop} where the actual departure time is before latestDepartureTime. Will not return {@code null}.
     */
    public Collection<TimetableStop> getActualDepartureBefore(LocalDateTime latestDepartureTime);

    /**
     * @param earliestDepartureTime the {@link LocalDateTime} which is the earliest departure time to be included.
     * @return all {@link TimetableStop} where the planned departure time is after latestDepartureTime. Will not return {@code null}.
     */
    public Collection<TimetableStop> getPlannedDepartureAfter(LocalDateTime earliestDepartureTime);

    /**
     * @param earliestDepartureTime the {@link LocalDateTime} which is the earliest departure time to be included.
     * @return all {@link TimetableStop} where the actual departure time is after earliestDepartureTime. Will not return {@code null}.
     */
    public Collection<TimetableStop> getActualDepartureAfter(LocalDateTime earliestDepartureTime);

    /**
     * @param latestArrivalTime the {@link LocalDateTime} which is the latest arrival time to be included.
     * @return all {@link TimetableStop} where the actual arrival time is in the past. Will not return {@code null}.
     */
    public Collection<TimetableStop> getActualArrivalBefore(LocalDateTime latestArrivalTime);

    /**
     * @param latestArrivalTime the {@link LocalDateTime} which is the latest arrival time to be included.
     * @return all {@link TimetableStop} where the planned arrival time is in the past. Will not return {@code null}.
     */
    public Collection<TimetableStop> getPlannedArrivalBefore(LocalDateTime latestArrivalTime);

    /**
     * @param earliestArrivalTime the {@link LocalDateTime} which is the earliest arrival time to be included.
     * @return all {@link TimetableStop} where the actual arrival time is in the past. Will not return {@code null}.
     */
    public Collection<TimetableStop> getActualArrivalAfter(LocalDateTime earliestArrivalTime);

    /**
     * @param earliestArrivalTime the {@link LocalDateTime} which is the earliest arrival time to be included.
     * @return all {@link TimetableStop} where the planned arrival time is in the past. Will not return {@code null}.
     */
    public Collection<TimetableStop> getPlannedArrivalAfter(LocalDateTime earliestArrivalTime);


    /**
     * Updates the arrival time of a {@link TimetableStop}.
     * @param id of the {@link TimetableStop} to be updated. Must not be null.
     * @param newArrival the new arrival time as a {@link LocalDateTime} of the {@link TimetableStop}. Must not be null.
     */
    public void updateArrival(String id, LocalDateTime newArrival);

    /**
     * Updates the departure time of a {@link TimetableStop}.
     * @param id of the {@link TimetableStop} to be updated. Must not be null.
     * @param newDeparture the new departure time as a {@link LocalDateTime} of the {@link TimetableStop}. Must not be null.
     */
    public void updateDeparture(String id, LocalDateTime newDeparture);

    /**
     * Updates the platform of a {@link TimetableStop}.
     * @param id of the {@link TimetableStop} to be updated. Must not be null.
     * @param newPlatform the new {@link Platform} of the {@link TimetableStop}. Must not be null.
     */
    public void updatePlatform(String id, LocalDateTime newPlatform);
}
