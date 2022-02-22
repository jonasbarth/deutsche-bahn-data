package com.jobarth.deutsche.bahn.data.acquisition;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.jobarth.deutsche.bahn.data.acquisition.jobs.TimetableFileWriterJob;
import com.jobarth.deutsche.bahn.data.acquisition.jobs.TimetableNeo4JWriterJob;
import com.jobarth.deutsche.bahn.data.acquisition.jobs.TimetablePlanRunnerJob;
import com.jobarth.deutsche.bahn.data.acquisition.jobs.TimetableRecentChangesJob;
import com.jobarth.deutsche.bahn.data.acquisition.request.TimetableRequest;
import com.jobarth.deutsche.bahn.data.acquisition.request.TimetableRequestImpl;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

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

    private String eva;
    private static final String PLAN_RUNNER_JOB = "plan-runner-job";
    private static final String PLAN_RUNNER_TRIGGER = "plan-runner-trigger";
    private static final String RECENT_CHANGES_JOB = "recent-changes-job";
    private static final String RECENT_CHANGES_TRIGGER = "recent-changes-trigger";
    private static final String WRITER_JOB = "writer-job";
    private static final String WRITER_TRIGGER = "writer-trigger";
    private List<JobKey> allJobKeys;

    @Autowired
    private TimetableManagerImpl timetableManager;

    @Autowired
    private TimetableNeo4JWriterJob neo4jJob;

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

            JobDetail neo4jJob = newJob(TimetableNeo4JWriterJob.class)
                    .withIdentity("neo4j", eva)
                    .usingJobData(new JobDataMap(ImmutableMap.of(
                            "eva", eva,
                            "timetableManager", timetableManager)))
                    .build();

            Trigger neo4jTrigger = newTrigger()
                    .withIdentity("neo4jTrigger", eva)
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0 3/3 ? * * *"))
                    .build();

            TimetableRequest timetableRequest = new TimetableRequestImpl(this.eva, timetableManager);

            JobDataMap jobData = new JobDataMap(ImmutableMap.of(
                    "request", timetableRequest));

            JobDetail planJob = newJob(TimetablePlanRunnerJob.class)
                    .withIdentity(PLAN_RUNNER_JOB, eva)
                    .usingJobData(jobData)
                    .build();

            allJobKeys.add(JobKey.jobKey(PLAN_RUNNER_JOB, eva));

            Trigger planRunnerTrigger = newTrigger()
                    .withIdentity(PLAN_RUNNER_TRIGGER, eva)
                    .startNow()
                    //.withSchedule(CronScheduleBuilder.cronSchedule("0 0 0,1 ? * * *"))
                    .build();

            JobDataMap recentChangesJobData = new JobDataMap(ImmutableMap.of(
                    "eva", eva,
                    "request", timetableRequest));

            JobDetail recentChangesJob = newJob(TimetableRecentChangesJob.class)
                    .withIdentity(RECENT_CHANGES_JOB, eva)
                    .usingJobData(recentChangesJobData)
                    .build();

            allJobKeys.add(JobKey.jobKey(RECENT_CHANGES_JOB, eva));

            Trigger recentChangesJobTrigger = newTrigger()
                    .withIdentity(RECENT_CHANGES_TRIGGER, eva)
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/30 * * ? * * *"))
                    .build();


            JobDataMap timetableWriterJobData = new JobDataMap(ImmutableMap.of(
                        "evaNo", eva,
                    "timetable", timetableManager));

            JobDetail timetableWriterJob = newJob(TimetableFileWriterJob.class)
                    .withIdentity(WRITER_JOB, eva)
                    .usingJobData(timetableWriterJobData)
                    .build();

            allJobKeys.add(JobKey.jobKey(WRITER_JOB, eva));

            Trigger timetableWriterTrigger = newTrigger()
                    .withIdentity(WRITER_TRIGGER, eva)
                    //.withSchedule(CronScheduleBuilder.cronSchedule("0 3/10 * ? * * *"))
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0 19/3 ? * * *"))
                    .build();

            scheduler.scheduleJob(planJob, planRunnerTrigger);
            scheduler.scheduleJob(recentChangesJob, recentChangesJobTrigger);
            scheduler.scheduleJob(timetableWriterJob, timetableWriterTrigger);
            scheduler.scheduleJob(neo4jJob, neo4jTrigger);
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

    public void setNeo4jJob(TimetableNeo4JWriterJob neo4jJob) {
        this.neo4jJob = neo4jJob;
    }

    public void setSchedulerFactoryBean(SchedulerFactoryBean schedulerFactoryBean) {
        this.schedulerFactoryBean = schedulerFactoryBean;
    }
}
