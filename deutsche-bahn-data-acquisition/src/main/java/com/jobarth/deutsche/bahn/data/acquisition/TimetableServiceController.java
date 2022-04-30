package com.jobarth.deutsche.bahn.data.acquisition;

import org.springframework.stereotype.Component;

/**
 * Manages all {@link TimetableService} in the system.
 */
@Component
public interface TimetableServiceController {

    /**
     * Adds a service to the manager
     * @param service the {@link TimetableService} to be added.
     */
    public void add(TimetableService service);

    /**
     *
     */
    public void start();
}
