package com.jobarth.deutsche.bahn.data.acquisition.jobs;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Queues;
import com.jobarth.deutsche.bahn.data.acquisition.request.TimetableRequest;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.time.LocalDateTime;
import java.util.Queue;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * An implementation of {@link Job} that fetches all planned stops of a station for the next 24 hours and all known future
 * changes for that station.
 */
public class TimetablePlanRunnerJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        TimetableRequest timetableRequest = (TimetableRequest) context.getMergedJobDataMap().get("request");
        Queue<LocalDateTime> requestTimes = Queues.newArrayDeque();
        int MAX_DATE_TIMES = 24;

        for (int i = 0; i < MAX_DATE_TIMES; i++) {
            requestTimes.add(LocalDateTime.now().plusHours(i));
        }

        JobDataMap jobData = new JobDataMap(ImmutableMap.of(
                "request", timetableRequest,
                "datetimes", requestTimes));
        // define the job and tie it to our HelloJob class
        JobDetail planJob = newJob(TimetablePlanJob.class)
                .withIdentity("plan", "plan " + timetableRequest.getEvaNo())
                .usingJobData(jobData)
                .build();

        

        try {
            int numberOfStations = context.getScheduler().getJobGroupNames().size();
            int requestInterval = (int) Math.ceil(60 / (20.0 / numberOfStations)) + 1;
            //max 20 requests per minute. 60 / 20. Requests for this is 20 / 
            Trigger trigger = newTrigger()
                    .withIdentity("plan trigger", "plan " + timetableRequest.getEvaNo())
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.repeatSecondlyForTotalCount(MAX_DATE_TIMES + 1, requestInterval))
                    .build();
            
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.scheduleJob(planJob, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
