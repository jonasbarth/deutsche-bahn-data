package com.jobarth.deutsche.bahn.data.acquisition;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.jobarth.deutsche.bahn.data.acquisition.jobs.DailyTimetableCleanerJob;
import com.jobarth.deutsche.bahn.data.acquisition.jobs.TimetablePlanRunnerJob;
import com.jobarth.deutsche.bahn.data.acquisition.jobs.TimetableRecentChangesJob;
import com.jobarth.deutsche.bahn.data.acquisition.request.TimetableRequest;
import com.jobarth.deutsche.bahn.data.acquisition.request.TimetableRequestImpl;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * An implementation of {@link TimetableService} that uses the Quartz library to run tasks.
 */
@Component
@Scope("prototype")
public class QuartzTimetableService implements TimetableService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzTimetableService.class);

    private static final String PLAN_RUNNER_JOB = "plan-runner-job";
    private static final String PLAN_RUNNER_TRIGGER = "plan-runner-trigger";
    private static final String RECENT_CHANGES_JOB = "recent-changes-job";
    private static final String RECENT_CHANGES_TRIGGER = "recent-changes-trigger";
    private static final String DAILY_TIMETABLE_CLEANER_JOB = "daily-timetable-cleaner-job";
    private static final String DAILY_TIMETABLE_CLEANER_TRIGGER = "daily-timetable-cleaner-trigger";
    private List<JobKey> allJobKeys;
    private String eva;
    private int recentChangesStartAt;
    private int numberOfStations;

    @Value("${bearer.token}")
    private String bearerToken;

    @Autowired
    private TimetableManagerImpl timetableManager;

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    public QuartzTimetableService() {
    }

    public QuartzTimetableService(String eva) {
        this.eva = eva;
    }

    @Override
    public void start() {
        this.allJobKeys = Lists.newArrayListWithCapacity(4);
        Scheduler scheduler = null;
        try {
            //https://stackoverflow.com/questions/6990767/inject-bean-reference-into-a-quartz-job-in-spring/15211030#15211030
            AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
            jobFactory.setApplicationContext(ApplicationContextProvider.getApplicationContext());
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.setJobFactory(jobFactory);

            TimetableRequest timetableRequest = new TimetableRequestImpl(this.eva, timetableManager, System.getProperty("bearer.token"));

            JobDataMap jobData = new JobDataMap(ImmutableMap.of(
                    "request", timetableRequest));

            JobDetail planJob = newJob(TimetablePlanRunnerJob.class)
                    .withIdentity(PLAN_RUNNER_JOB, eva)
                    .usingJobData(jobData)
                    .build();

            allJobKeys.add(JobKey.jobKey(PLAN_RUNNER_JOB, eva));

            LocalDateTime now = LocalDateTime.now().plusMinutes(2);
            int cronMinutes = now.getMinute();
            int cronHour = now.getHour();
            Trigger planRunnerTrigger = newTrigger()
                    .withIdentity(PLAN_RUNNER_TRIGGER, eva)
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule(String.format("0 %d %d ? * * *", cronMinutes, cronHour)))
                    .build();

            JobDataMap recentChangesJobData = new JobDataMap(ImmutableMap.of(
                    "eva", eva,
                    "request", timetableRequest));

            JobDetail recentChangesJob = newJob(TimetableRecentChangesJob.class)
                    .withIdentity(RECENT_CHANGES_JOB, eva)
                    .usingJobData(recentChangesJobData)
                    .build();

            allJobKeys.add(JobKey.jobKey(RECENT_CHANGES_JOB, eva));
            //how many minutes does it take? number of stations
            // 60 / (20 / number of stations) * number of stations
            //int startInSeconds = 60 / (20 / numberOfStations) * 25 * numberOfStations;
            //the job should not run if any future changes or
            Trigger recentChangesJobTrigger = newTrigger()
                    .withIdentity(RECENT_CHANGES_TRIGGER, eva)
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule(recentChangesStartAt + "/30 * * ? * * *"))
                    .build();

            JobDataMap dailyTimetableCleanerJobData = new JobDataMap(ImmutableMap.of(
                    "eva", eva,
                    "timetableManager", timetableManager));

            JobDetail dailyTimetableCleanerJob = newJob(DailyTimetableCleanerJob.class)
                    .withIdentity(DAILY_TIMETABLE_CLEANER_JOB, eva)
                    .usingJobData(dailyTimetableCleanerJobData)
                    .build();

            Trigger dailyTimetableCleanerTrigger = newTrigger()
                    .withIdentity(DAILY_TIMETABLE_CLEANER_TRIGGER, eva)
                    .startAt(Date.from(LocalDateTime.now().plusHours(24).atZone(ZoneId.systemDefault()).toInstant()))
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 ? * * *"))
                    .build();

            scheduler.scheduleJob(planJob, planRunnerTrigger);
            scheduler.scheduleJob(recentChangesJob, recentChangesJobTrigger);
            scheduler.scheduleJob(dailyTimetableCleanerJob, dailyTimetableCleanerTrigger);
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
    public String getEva() {
        return eva;
    }

    public void setEva(String eva) {
        this.eva = eva;
    }

    public void setTimetableManager(TimetableManagerImpl timetableManager) {
        this.timetableManager = timetableManager;
    }

    public void setSchedulerFactoryBean(SchedulerFactoryBean schedulerFactoryBean) {
        this.schedulerFactoryBean = schedulerFactoryBean;
    }

    public int getRecentChangesStartAt() {
        return recentChangesStartAt;
    }

    public void setRecentChangesStartAt(int recentChangesStartAt) {
        this.recentChangesStartAt = recentChangesStartAt;
    }

    public int getNumberOfStations() {
        return numberOfStations;
    }

    public void setNumberOfStations(int numberOfStations) {
        this.numberOfStations = numberOfStations;
    }
}
