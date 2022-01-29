package com.jobarth.deutsche.bahn.data.server.jobs;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Queues;
import com.jobarth.deutsche.bahn.data.server.TimetableRequest;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.time.LocalDateTime;
import java.util.Queue;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public class TimetablePlanRunnerJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        TimetableRequest timetableRequest = (TimetableRequest) context.getMergedJobDataMap().get("request");
        Queue<LocalDateTime> requestTimes = Queues.newArrayDeque();
        int MAX_DATE_TIMES = 2;

        for (int i = 0; i < MAX_DATE_TIMES; i++) {
            requestTimes.add(LocalDateTime.now().plusHours(i));
        }

        JobDataMap jobData = new JobDataMap(ImmutableMap.of(
                "request", timetableRequest,
                "datetimes", requestTimes));
        // define the job and tie it to our HelloJob class
        JobDetail planJob = newJob(TimetablePlanJob.class)
                .withIdentity("plan", "plan 8011160")
                .usingJobData(jobData)
                .build();

        // Trigger the job to run now, and then repeat every 40 seconds
        Trigger trigger = newTrigger()
                .withIdentity("plan trigger", "plan 8011160")
                //.startAt(Date.from(tomorrow1am().atZone(ZoneId.systemDefault()).toInstant()))
                .startNow()
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForTotalCount(MAX_DATE_TIMES + 1, 3))
                .build();

        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.scheduleJob(planJob, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
