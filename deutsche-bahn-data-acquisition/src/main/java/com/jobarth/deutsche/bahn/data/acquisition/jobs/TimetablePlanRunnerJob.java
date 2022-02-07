package com.jobarth.deutsche.bahn.data.acquisition.jobs;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Queues;
import com.jobarth.deutsche.bahn.data.acquisition.request.TimetableRequest;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        int MAX_DATE_TIMES = 24;

        Queue<LocalDateTime> requestTimes = IntStream.range(0, MAX_DATE_TIMES)
                .mapToObj(i -> LocalDateTime.now().plusHours(i))
                .collect(Collectors.toCollection(ArrayDeque::new));

        JobDataMap jobData = new JobDataMap(ImmutableMap.of(
                "request", timetableRequest,
                "datetimes", requestTimes));

        JobDetail planJob = newJob(TimetablePlanJob.class)
                .withIdentity("plan", "plan " + timetableRequest.getEvaNo())
                .usingJobData(jobData)
                .build();

        try {
            int numberOfStations = context.getScheduler().getJobGroupNames().size();
            double requestsPerMinute = 20.0 / numberOfStations;
            int requestInterval = (int) Math.ceil(60 / requestsPerMinute) + 1;
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
