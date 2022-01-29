package com.jobarth.deutsche.bahn.data.server.jobs;

import com.jobarth.deutsche.bahn.data.server.TimetableRequest;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Queue;

/**
 * A {@link Job} that fetches a timetable and all known future changes.
 */
public class TimetablePlanJob implements Job {

    private final static Logger LOGGER = LoggerFactory.getLogger(TimetablePlanJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Queue<LocalDateTime> dateTimes = (Queue<LocalDateTime>) context.getMergedJobDataMap().get("datetimes");
        TimetableRequest timetableRequest = (TimetableRequest) context.getMergedJobDataMap().get("request");

        try {
            if (dateTimes.isEmpty()) {
                timetableRequest.getFutureChanges();
            } else {
                timetableRequest.getPlan(dateTimes.remove());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
