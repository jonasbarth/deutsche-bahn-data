package com.jobarth.deutsche.bahn.data.domain;

import com.google.common.collect.Range;

import java.util.Objects;

/**
 * An immutable implementation of {@link Station}.
 */
public class ImmutableStation implements Station {

    private final int evaNumber;
    private final String name;
    private final Location location;

    public ImmutableStation(int evaNumber, String name, Location location) {
        Objects.requireNonNull(name, "The station name must not be null");
        Objects.requireNonNull(location, "The location must not be null");
        this.evaNumber = evaNumber;
        this.name = name;
        this.location = location;
    }

    @Override
    public int getEvaNumber() {
        return evaNumber;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof Station) {
            Station otherStation = (Station) other;
            return evaNumber == otherStation.getEvaNumber() &&
                    name.equals(otherStation.getName()) &&
                    location.equals(otherStation.getLocation());
        }
        return false;
    }
}
