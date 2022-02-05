package com.jobarth.deutsche.bahn.data.acquisition;

/**
 * A service that continuously gathers data for a station.
 */
public interface TimetableService {

    /**
     * Starts the service.
     */
    public void start();

    /**
     * Stops the service.
     */
    public void stop();

    /**
     * @return the eva number of the station. Will not return {@code null}.
     */
    public String getEvaNo();


}
