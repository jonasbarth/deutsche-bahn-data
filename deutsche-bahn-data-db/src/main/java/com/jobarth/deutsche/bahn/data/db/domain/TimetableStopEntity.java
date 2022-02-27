package com.jobarth.deutsche.bahn.data.db.domain;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDateTime;

/**
 * A Neo4J entity representing a timetable stop
 */
@Node("TimetableStop")
public class TimetableStopEntity {

    @Id
    private final String id;

    @Relationship("IS_TRIP_CATEGORY")
    private final TripCategoryLabel tripCategory;

    @Relationship("AT_STATION")
    private final StationEntity station;

    @Relationship("NEXT_STATION")
    private final StationEntity nextStation;

    @Relationship("PREVIOUS_STATION")
    private final StationEntity previousStation;

    @Property
    private final String trainNumber;

    @Property
    private final LocalDateTime plannedArrival;

    @Property
    private final LocalDateTime actualArrival;

    @Property
    private final LocalDateTime plannedDeparture;

    @Property
    private final LocalDateTime actualDeparture;

    @Property
    private final String plannedPlatform;

    @Property
    private final String actualPlatform;

    public TimetableStopEntity(String id, TripCategoryLabel tripCategory, StationEntity station, StationEntity nextStation, StationEntity previousStation, String trainNumber, LocalDateTime plannedArrival, LocalDateTime actualArrival, LocalDateTime plannedDeparture, LocalDateTime actualDeparture, String plannedPlatform, String actualPlatform) {
        this.id = id;
        this.tripCategory = tripCategory;
        this.station = station;
        this.nextStation = nextStation;
        this.previousStation = previousStation;
        this.trainNumber = trainNumber;
        this.plannedArrival = plannedArrival;
        this.actualArrival = actualArrival;
        this.plannedDeparture = plannedDeparture;
        this.actualDeparture = actualDeparture;
        this.plannedPlatform = plannedPlatform;
        this.actualPlatform = actualPlatform;
    }

    public String getId() {
        return id;
    }

    public TripCategoryLabel getTripCategory() {
        return tripCategory;
    }

    public StationEntity getStation() {
        return station;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public LocalDateTime getPlannedArrival() {
        return plannedArrival;
    }

    public LocalDateTime getActualArrival() {
        return actualArrival;
    }

    public LocalDateTime getPlannedDeparture() {
        return plannedDeparture;
    }

    public LocalDateTime getActualDeparture() {
        return actualDeparture;
    }

    public String getPlannedPlatform() {
        return plannedPlatform;
    }

    public String getActualPlatform() {
        return actualPlatform;
    }
}
