package com.jobarth.deutsche.bahn.data.server;

import com.google.common.collect.ImmutableMap;
import com.jobarth.deutsche.bahn.data.server.jobs.TimetablePlanRunnerJob;
import com.jobarth.deutsche.bahn.data.server.jobs.TimetableRecentChangesJob;
import com.jobarth.deutsche.bahn.data.server.jobs.TimetableWriterJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public class QuartzTest {

    public static void main(String[] args) {

        try {
            TimetableManagerImpl listener = new TimetableManagerImpl();
            // Grab the Scheduler instance from the Factory
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            TimetableRequest timetableRequest = new TimetableRequestImpl("8011160", listener);

            JobDataMap jobData = new JobDataMap(ImmutableMap.of(
                    "request", timetableRequest));
            // define the job and tie it to our HelloJob class
            JobDetail planJob = newJob(TimetablePlanRunnerJob.class)
                    .withIdentity("plan runner", "plan 8011160")
                    .usingJobData(jobData)
                    .build();

            Trigger planRunnerTrigger = newTrigger()
                    .withIdentity("plan runner trigger", "plan 8011160")
                    .startNow()
                    //.withSchedule(CronScheduleBuilder.cronSchedule("0 0 0,1 ? * * *"))
                    .build();


            JobDataMap recentChangesJobData = new JobDataMap(ImmutableMap.of(
                    "request", timetableRequest));

            JobDetail recentChangesJob = newJob(TimetableRecentChangesJob.class)
                    .withIdentity("recent changes", "8011160")
                    .usingJobData(recentChangesJobData)
                    .build();

            Trigger recentChangesJobTrigger = newTrigger()
                    .withIdentity("recent changes trigger", "8011160")
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/30 * * ? * * *"))
                    .build();


            JobDataMap timetableWriterJobData = new JobDataMap(ImmutableMap.of(
                    "timetable", listener));

            JobDetail timetableWriterJob = newJob(TimetableWriterJob.class)
                    .withIdentity("writer", "8011160")
                    .usingJobData(timetableWriterJobData)
                    .build();

            Trigger timetableWriterTrigger = newTrigger()
                    .withIdentity("writer trigger", "8011160")
                    .startAt(Date.from(Instant.now().plus(3, ChronoUnit.MINUTES)))
                    .build();


            scheduler.scheduleJob(planJob, planRunnerTrigger);
            scheduler.scheduleJob(recentChangesJob, recentChangesJobTrigger);
            scheduler.scheduleJob(timetableWriterJob, timetableWriterTrigger);

            // and start it off
            scheduler.start();

            // as long as you don't shutdown the scheduler, it will keep running
            //scheduler.shutdown();

        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }

    private static LocalDateTime tomorrow1am() {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalTime twoAm = LocalTime.of(2, 0);
        if (localDateTime.getHour() > 2) {
            LocalDate tomorrow = localDateTime.plusDays(1).toLocalDate();
            return LocalDateTime.of(tomorrow, twoAm);
        }
        return LocalDateTime.of(localDateTime.toLocalDate(), twoAm);
    }
}
