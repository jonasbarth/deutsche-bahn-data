package com.jobarth.deutsche.bahn.data.domain;

/**
 * An immutable implementation of {@link Location}
 */
public class ImmutableLocation implements Location {

    private final double longitude;
    private final double latitude;

    /**
     * Constructor for {@link ImmutableLocation}.
     * @param longitude the longitude of the location. Must be in interval [-180, 180].
     * @param latitude the latitude of the location. Must be in interval [-90, 90].
     */
    public ImmutableLocation(double longitude, double latitude) {
        if (!LEGAL_LATITUDES.contains(latitude)) {
            throw new IllegalArgumentException(String.format("The given latitude %f is not within the accepted range %s", latitude, LEGAL_LATITUDES.toString()));
        }
        if (!LEGAL_LONGITUDES.contains(longitude)) {
            throw new IllegalArgumentException(String.format("The given longitude %f is not within the accepted range %s", longitude, LEGAL_LONGITUDES.toString()));
        }
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }

    @Override
    public double getLatitude() {
        return latitude;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof Location) {
            Location otherLocation = (Location) other;
            return this.longitude == otherLocation.getLongitude()
                    && this.latitude == otherLocation.getLatitude();
        }
        return false;
    }
}
