package com.jobarth.deutsche.bahn.data.server.jobs;

import com.jobarth.deutsche.bahn.data.server.TimetableRequest;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A {@link Job} that runs and fetches recent changes to a timetable.
 */
public class TimetableRecentChangesJob implements Job {

    private final static Logger LOGGER = LoggerFactory.getLogger(TimetableRecentChangesJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            Trigger planJobTrigger = context.getScheduler().getTrigger(TriggerKey.triggerKey("plan trigger", "plan 8011160"));
            if (planJobTrigger == null || !planJobTrigger.mayFireAgain()) {
                TimetableRequest timetableRequest = (TimetableRequest) context.getMergedJobDataMap().get("request");
                timetableRequest.getRecentChanges();
            }
        } catch (Exception e) {
            LOGGER.warn("An exception occurred while fetching the recent changes for {}.", "a", e);
        }


    }
}
