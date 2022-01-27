package com.jobarth.deutsche.bahn.data.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * An immutable implementation of {@link Change}.
 */
public class ImmutableChange implements Change {

    private final String timetableStopId;
    private final LocalDateTime changedArrival;
    private final LocalDateTime changedDeparture;
    private final Platform changedPlatform;

    /**
     * Constructor for {@link ImmutableChange}.
     * @param timetableStopId the ID of the {@link TimetableStop} of this change. Must not be null.
     * @param changedArrival the changed arrival time as a {@link LocalDateTime}. Must not be null.
     * @param changedDeparture the changed departure time as a {@link LocalDateTime}. Must not be null.
     * @param changedPlatform the changed {@link Platform}. Can be null.
     */
    public ImmutableChange(String timetableStopId, LocalDateTime changedArrival, LocalDateTime changedDeparture, Platform changedPlatform) {
        Objects.requireNonNull(timetableStopId, "The timetableStopId must not be null");
        //Objects.requireNonNull(changedArrival, "The changedArrival must not be null");
        //Objects.requireNonNull(changedDeparture, "The changedDeparture must not be null");
        this.timetableStopId = timetableStopId;
        this.changedArrival = changedArrival;
        this.changedDeparture = changedDeparture;
        this.changedPlatform = changedPlatform;
    }

    @Override
    public String getTimetableStopId() {
        return timetableStopId;
    }

    @Override
    public LocalDateTime getChangedArrival() {
        return changedArrival;
    }

    @Override
    public LocalDateTime getChangedDeparture() {
        return changedDeparture;
    }

    @Override
    public Platform getChangedPlatform() {
        return changedPlatform;
    }

    private ImmutableChange(Builder builder) {
        this.timetableStopId = builder.timetableStopId;
        this.changedArrival = builder.changedArrival;
        this.changedDeparture = builder.changedDeparture;
        this.changedPlatform = builder.changedPlatform;
    }
    
    public static class Builder {
        private String timetableStopId;
        private LocalDateTime changedArrival;
        private LocalDateTime changedDeparture;
        private Platform changedPlatform;
        private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmm");

        public Builder timetableStopId(String timetableStopId) {
            this.timetableStopId = timetableStopId;
            return this;
        }

        public Builder changedArrival(String changedArrival) {
            this.changedArrival = changedArrival.equals("") ? null : LocalDateTime.parse(changedArrival, formatter);
            return this;
        }

        public Builder changedDeparture(String changedDeparture) {
            this.changedDeparture = changedDeparture.equals("") ? null : LocalDateTime.parse(changedDeparture, formatter);
            return this;
        }

        public Builder changedPlatform(String platform) {
            this.changedPlatform = new ImmutablePlatform(platform);
            return this;
        }

        public Change build() {
            return new ImmutableChange(this);
        }
    }
}
