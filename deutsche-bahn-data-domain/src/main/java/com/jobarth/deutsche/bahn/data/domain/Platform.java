package com.jobarth.deutsche.bahn.data.domain;

/**
 * The platform at a train station.
 */
public interface Platform {

    /**
     * @return the number of the platform as a {@link String}. Will not be null. Might not only contain numbers.
     */
    public String getNumber();
}
