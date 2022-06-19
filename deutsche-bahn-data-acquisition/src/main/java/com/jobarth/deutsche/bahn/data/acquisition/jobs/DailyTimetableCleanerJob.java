package com.jobarth.deutsche.bahn.data.acquisition.jobs;

import com.jobarth.deutsche.bahn.data.acquisition.TimetableManager;
import com.jobarth.deutsche.bahn.data.domain.Timetable;
import com.jobarth.deutsche.bahn.data.domain.TimetableStop;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * A job that cleans the stops of a specific station that are older than 24 hours.
 */
public class DailyTimetableCleanerJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String eva = context.getMergedJobDataMap().getString("eva");
        Timetable timetable = ((TimetableManager) context.getMergedJobDataMap().get("timetableManager")).get(eva);

        Collection<TimetableStop> oldStops = timetable.getTimetableStops().stream()
                .filter(stop -> stop.isOlderThan(LocalDateTime.now().minusHours(24)))
                .collect(Collectors.toList());

        timetable.remove(oldStops);
    }
}
