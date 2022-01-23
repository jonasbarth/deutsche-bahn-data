package com.jobarth.deutsche.bahn.data.domain;

import java.util.Objects;

/**
 * An immutable implementation of {@link Platform}.
 */
public class ImmutablePlatform implements Platform {

    private final String number;

    /**
     * Constructor for {@link ImmutablePlatform}.
     * @param platformNumber the number of the platform. Must not be {@link null}.
     */
    public ImmutablePlatform(String platformNumber) {
        Objects.requireNonNull(platformNumber, "The platformNumber must not be null");
        this.number = platformNumber;
    }

    @Override
    public String getNumber() {
        return this.number;
    }
}
