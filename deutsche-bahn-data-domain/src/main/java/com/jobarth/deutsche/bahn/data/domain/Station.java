package com.jobarth.deutsche.bahn.data.domain;

/**
 * A train station.
 */
public interface Station {

    /**
     * @return the unique eva number of this station.
     */
    public int getEvaNumber();

    /**
     * @return the name of this station. Will not return {@code null}.
     */
    public String getName();

    /**
     * @return the {@link Location} of this station. Will not return {@code null}.
     */
    public Location getLocation();
}
