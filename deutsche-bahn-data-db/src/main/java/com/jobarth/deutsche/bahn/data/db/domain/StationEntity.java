package com.jobarth.deutsche.bahn.data.db.domain;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

/**
 * A Neo4J entity representing a station.
 */
@Node("Station")
public class StationEntity {

    @Id
    private final String eva;

    @Property
    private final String name;

    @Property
    private final double longitude;

    @Property
    private final double latitude;

    public StationEntity(String eva, String name, double longitude, double latitude) {
        this.eva = eva;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getEva() {
        return eva;
    }

    public String getName() {
        return name;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
