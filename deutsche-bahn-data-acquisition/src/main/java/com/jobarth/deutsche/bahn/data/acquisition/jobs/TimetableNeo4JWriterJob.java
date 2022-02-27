package com.jobarth.deutsche.bahn.data.acquisition.jobs;

import com.jobarth.deutsche.bahn.data.acquisition.TimetableManagerImpl;
import com.jobarth.deutsche.bahn.data.acquisition.filter.DepartedTimetableFilter;
import com.jobarth.deutsche.bahn.data.db.api.Neo4jTimetableWriter;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * A {@link Job} that writes a timetable to a neo4j graph database.
 */
@Component
public class TimetableNeo4JWriterJob implements Job {

    @Autowired
    private Neo4jTimetableWriter neo4jTimetableWriter;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        TimetableManagerImpl timetable = (TimetableManagerImpl) context.getMergedJobDataMap().get("timetableManager");
        String eva = context.getMergedJobDataMap().getString("eva");
        neo4jTimetableWriter.write(new DepartedTimetableFilter().apply(timetable.get(eva)));
    }
}
