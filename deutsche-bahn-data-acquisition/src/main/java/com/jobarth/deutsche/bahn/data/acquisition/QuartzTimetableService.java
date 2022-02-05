package com.jobarth.deutsche.bahn.data.acquisition;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.jobarth.deutsche.bahn.data.acquisition.jobs.TimetablePlanRunnerJob;
import com.jobarth.deutsche.bahn.data.acquisition.jobs.TimetableRecentChangesJob;
import com.jobarth.deutsche.bahn.data.acquisition.jobs.TimetableWriterJob;
import com.jobarth.deutsche.bahn.data.acquisition.request.TimetableRequest;
import com.jobarth.deutsche.bahn.data.acquisition.request.TimetableRequestImpl;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * An implementation of {@link TimetableService} that uses the Quartz library to run tasks.
 */
public class QuartzTimetableService implements TimetableService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzTimetableService.class);
    private String evaNo;
    private static final String PLAN_RUNNER_JOB = "plan-runner-job";
    private static final String PLAN_RUNNER_TRIGGER = "plan-runner-trigger";
    private static final String RECENT_CHANGES_JOB = "recent-changes-job";
    private static final String RECENT_CHANGES_TRIGGER = "recent-changes-trigger";
    private static final String WRITER_JOB = "writer-job";
    private static final String WRITER_TRIGGER = "writer-trigger";
    private List<JobKey> allJobKeys;

    public QuartzTimetableService(String evaNo) {
        Objects.requireNonNull(evaNo, "The evaNo must not be null.");
        this.evaNo = evaNo;
    }

    public void setEvaNo(String evaNo) {
        this.evaNo = evaNo;
    }

    @Override
    public void start() {
        TimetableManagerImpl listener = new TimetableManagerImpl();

        this.allJobKeys = Lists.newArrayListWithCapacity(3);
        Scheduler scheduler = null;
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();

            TimetableRequest timetableRequest = new TimetableRequestImpl(this.evaNo, listener);

            JobDataMap jobData = new JobDataMap(ImmutableMap.of(
                    "request", timetableRequest));

            JobDetail planJob = newJob(TimetablePlanRunnerJob.class)
                    .withIdentity(PLAN_RUNNER_JOB, evaNo)
                    .usingJobData(jobData)
                    .build();

            allJobKeys.add(JobKey.jobKey(PLAN_RUNNER_JOB, evaNo));

            Trigger planRunnerTrigger = newTrigger()
                    .withIdentity(PLAN_RUNNER_TRIGGER, evaNo)
                    .startNow()
                    //.withSchedule(CronScheduleBuilder.cronSchedule("0 0 0,1 ? * * *"))
                    .build();

            JobDataMap recentChangesJobData = new JobDataMap(ImmutableMap.of(
                    "eva", evaNo,
                    "request", timetableRequest));

            JobDetail recentChangesJob = newJob(TimetableRecentChangesJob.class)
                    .withIdentity(RECENT_CHANGES_JOB, evaNo)
                    .usingJobData(recentChangesJobData)
                    .build();

            allJobKeys.add(JobKey.jobKey(RECENT_CHANGES_JOB, evaNo));

            Trigger recentChangesJobTrigger = newTrigger()
                    .withIdentity(RECENT_CHANGES_TRIGGER, evaNo)
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/30 * * ? * * *"))
                    .build();


            JobDataMap timetableWriterJobData = new JobDataMap(ImmutableMap.of(
                        "evaNo", evaNo,
                    "timetable", listener));

            JobDetail timetableWriterJob = newJob(TimetableWriterJob.class)
                    .withIdentity(WRITER_JOB, evaNo)
                    .usingJobData(timetableWriterJobData)
                    .build();

            allJobKeys.add(JobKey.jobKey(WRITER_JOB, evaNo));

            Trigger timetableWriterTrigger = newTrigger()
                    .withIdentity(WRITER_TRIGGER, evaNo)
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0 1/3 ? * * *"))
                    .build();

            scheduler.scheduleJob(planJob, planRunnerTrigger);
            scheduler.scheduleJob(recentChangesJob, recentChangesJobTrigger);
            scheduler.scheduleJob(timetableWriterJob, timetableWriterTrigger);
        } catch (SchedulerException e) {
            LOGGER.warn("There was a problem with starting the QuartzTimetableService", e);
        }
    }

    @Override
    public void stop() {
        Scheduler scheduler = null;
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.deleteJobs(allJobKeys);
        } catch (SchedulerException e) {
            LOGGER.warn("There was a problem with stopping the QuartzTimetableService", e);
        }
    }

        @Override
    public String getEvaNo() {
        return evaNo;
    }
}
