package com.jobarth.deutsche.bahn.data.acquisition.request;

import com.jobarth.deutsche.bahn.data.domain.Timetable;

/**
 * A listener that is notified by changes in the {@link TimetableRequest}.
 */
public interface TimetableRequestListener {

    /**
     * Called when the {@link TimetableRequest} has fetched a plan for a timetable
     * @param timetable the {@link Timetable} that was fetched.
     */
    public void onPlan(Timetable timetable);

    /**
     * Called when the {@link TimetableRequest} has fetched recent changes for a timetable.
     * @param timetable the {@link Timetable} that was fetched.
     */
    public void onRecentChanges(Timetable timetable);

    /**
     * Called when the {@link TimetableRequest} has fetched future changes for a timetable.
     * @param timetable the {@link Timetable} that was fetched.
     */
    public void onFutureChanges(Timetable timetable);
}
