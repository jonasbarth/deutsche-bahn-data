package com.jobarth.deutsche.bahn.data.domain;

import com.google.common.collect.Lists;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Implementation of {@link Timetable}.
 */
public class TimetableImpl implements Timetable {

    private final Collection<TimetableStop> timetableStops = Lists.newLinkedList();

    @Override
    public void addTimetableStop(TimetableStop timetableStop) {
        Objects.requireNonNull(timetableStop, "The timetableStop must not be null");
        if (timetableStops.isEmpty()) {
            timetableStops.add(timetableStop);
            return;
        }

        boolean matchesAllStations = timetableStops.stream()
                .map(TimetableStop::getStation)
                .allMatch(station -> timetableStop.getStation().equals(station));
        if (matchesAllStations) {
            timetableStops.add(timetableStop);
            return;
        }
        throw new IllegalArgumentException("The station of the time table stop does not match the already existing station of the timetable");
    }

    @Override
    public Station getStation() {
        if (timetableStops.isEmpty()) {
            return null;
        }
        return timetableStops.stream().findFirst().get().getStation();
    }

    @Override
    public Collection<TimetableStop> getAllTimetableStops() {
        return timetableStops;
    }

    @Override
    public Collection<TimetableStop> getPlannedDepartureBefore(LocalDateTime latestDepartureTime) {
        return timetableStops.stream()
                .filter(timetableStop -> timetableStop.getPlannedDeparture().isBefore(latestDepartureTime))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<TimetableStop> getActualDepartureBefore(LocalDateTime latestDepartureTime) {
        return timetableStops.stream()
                .filter(timetableStop -> timetableStop.getActualDeparture().isBefore(latestDepartureTime))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<TimetableStop> getPlannedDepartureAfter(LocalDateTime earliestDepartureTime) {
        return timetableStops.stream()
                .filter(timetableStop -> timetableStop.getPlannedDeparture().isAfter(earliestDepartureTime))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<TimetableStop> getActualDepartureAfter(LocalDateTime earliestDepartureTime) {
        return timetableStops.stream()
                .filter(timetableStop -> timetableStop.getActualDeparture().isAfter(earliestDepartureTime))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<TimetableStop> getActualArrivalBefore(LocalDateTime latestArrivalTime) {
        return timetableStops.stream()
                .filter(timetableStop -> timetableStop.getActualArrival().isBefore(latestArrivalTime))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<TimetableStop> getPlannedArrivalBefore(LocalDateTime latestArrivalTime) {
        return timetableStops.stream()
                .filter(timetableStop -> timetableStop.getPlannedArrival().isBefore(latestArrivalTime))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<TimetableStop> getActualArrivalAfter(LocalDateTime earliestArrivalTime) {
        return timetableStops.stream()
                .filter(timetableStop -> timetableStop.getActualArrival().isAfter(earliestArrivalTime))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<TimetableStop> getPlannedArrivalAfter(LocalDateTime earliestArrivalTime) {
        return timetableStops.stream()
                .filter(timetableStop -> timetableStop.getPlannedArrival().isAfter(earliestArrivalTime))
                .collect(Collectors.toList());
    }

    @Override
    public void updateArrival(String id, LocalDateTime newArrival) {

    }

    @Override
    public void updateDeparture(String id, LocalDateTime newDeparture) {

    }

    @Override
    public void updatePlatform(String id, LocalDateTime newPlatform) {

    }
}
