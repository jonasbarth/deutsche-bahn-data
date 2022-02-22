package com.jobarth.deutsche.bahn.data.acquisition.jobs;

import com.jobarth.deutsche.bahn.data.db.FileTimetableWriter;
import com.jobarth.deutsche.bahn.data.db.TimetableWriter;
import com.jobarth.deutsche.bahn.data.acquisition.TimetableManagerImpl;
import com.jobarth.deutsche.bahn.data.acquisition.filter.DepartedTimetableFilter;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * An implementation of {@link Job} that writes a timetable to a file.
 */
public class TimetableFileWriterJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        TimetableWriter writer = new FileTimetableWriter();
        TimetableManagerImpl timetable = (TimetableManagerImpl) context.getMergedJobDataMap().get("timetable");
        String evaNo = context.getMergedJobDataMap().getString("evaNo");

        writer.write(new DepartedTimetableFilter().apply(timetable.get(evaNo)));
    }
}
