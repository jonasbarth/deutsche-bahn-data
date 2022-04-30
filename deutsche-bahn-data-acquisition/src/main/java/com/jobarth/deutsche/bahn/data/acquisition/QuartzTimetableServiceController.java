package com.jobarth.deutsche.bahn.data.acquisition;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * A {@link TimetableServiceController} that handles {@link QuartzTimetableService}.
 */
public class QuartzTimetableServiceController implements TimetableServiceController {

    private final Set<TimetableService> services = Sets.newHashSet();

    @Override
    public void add(TimetableService service) {
        services.add(service);
    }

    @Override
    public void start() {
        //what problem do I want to solve?
        //Each service needs to know how many other services there are
        //I only want to start the recent changes once all future changes have been added
        //how do the recent changes know whether all future changes and plans have been called
    }
}
