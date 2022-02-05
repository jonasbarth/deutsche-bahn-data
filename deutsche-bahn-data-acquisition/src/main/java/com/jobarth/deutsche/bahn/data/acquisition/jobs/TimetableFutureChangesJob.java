package com.jobarth.deutsche.bahn.data.acquisition.jobs;

import com.jobarth.deutsche.bahn.data.acquisition.request.TimetableRequest;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A {@link Job} that runs and fetches recent changes to a timetable.
 */
public class TimetableFutureChangesJob implements Job {

    private final static Logger LOGGER = LoggerFactory.getLogger(TimetableFutureChangesJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        TimetableRequest timetableRequest = (TimetableRequest) context.getMergedJobDataMap().get("request");
        try {
            JobDetail plan = context.getScheduler().getJobDetail(JobKey.jobKey("plan"));
            timetableRequest.getFutureChanges();
        } catch (Exception e) {
            LOGGER.warn("An exception occurred while fetching the recent changes for {}.", timetableRequest.getEvaNo(), e);
        }
    }


}
