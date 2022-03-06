package com.jobarth.deutsche.bahn.data.acquisition.jobs;

import com.jobarth.deutsche.bahn.data.acquisition.request.TimetableRequest;
import com.jobarth.deutsche.bahn.data.db.api.FileTimetableWriter;
import com.jobarth.deutsche.bahn.data.db.api.TimetableWriter;
import com.jobarth.deutsche.bahn.data.acquisition.TimetableManagerImpl;
import com.jobarth.deutsche.bahn.data.acquisition.filter.DepartedTimetableFilter;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An implementation of {@link Job} that writes a timetable to a file.
 */
public class TimetableFileWriterJob implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimetableFileWriterJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        String eva = context.getMergedJobDataMap().getString("eva");

        try {
            Trigger planJobTrigger = context.getScheduler().getTrigger(TriggerKey.triggerKey("plan trigger", "plan " + eva));
            if (planJobTrigger == null || !planJobTrigger.mayFireAgain()) {
                TimetableWriter writer = new FileTimetableWriter();
                TimetableManagerImpl timetable = (TimetableManagerImpl) context.getMergedJobDataMap().get("timetable");
                writer.write(new DepartedTimetableFilter().apply(timetable.get(eva)));
            }
        } catch (Exception e) {
            LOGGER.warn("An exception occurred while writing timetable tops of {} to file.", eva, e);
        }
    }
}
