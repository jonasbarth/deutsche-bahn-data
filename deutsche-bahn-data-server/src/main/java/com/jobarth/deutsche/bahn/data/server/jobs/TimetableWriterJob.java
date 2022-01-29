package com.jobarth.deutsche.bahn.data.server.jobs;

import com.jobarth.deutsche.bahn.data.db.FileTimetableWriter;
import com.jobarth.deutsche.bahn.data.db.TimetableWriter;
import com.jobarth.deutsche.bahn.data.domain.Timetable;
import com.jobarth.deutsche.bahn.data.server.TimetableManagerImpl;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TimetableWriterJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        TimetableWriter writer = new FileTimetableWriter();
        TimetableManagerImpl timetable = (TimetableManagerImpl) context.getMergedJobDataMap().get("timetable");

        writer.write(timetable.getFirst());
    }
}
