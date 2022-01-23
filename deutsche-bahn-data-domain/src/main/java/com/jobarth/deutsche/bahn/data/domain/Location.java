package com.jobarth.deutsche.bahn.data.domain;

import com.google.common.collect.Range;

/**
 * A geographical location identifiable by longitude and latitude
 */
public interface Location {

    public static final double MAX_LATITUDE = 90;
    public static final double MIN_LATITUDE = -90;
    public static final double MAX_LONGITUDE = 180;
    public static final double MIN_LONGITUDE = -180;
    public static final Range<Double> LEGAL_LATITUDES = Range.closed(MIN_LATITUDE, MAX_LATITUDE);
    public static final Range<Double> LEGAL_LONGITUDES = Range.closed(MIN_LONGITUDE, MAX_LONGITUDE);

    /**
     * @return longitude of the location in the interval [-180, 180].
     */
    public double getLongitude();

    /**
     * @return latitude of the location in the interval [-90, 90].
     */
    public double getLatitude();
}
