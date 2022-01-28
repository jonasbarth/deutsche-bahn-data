package com.jobarth.deutsche.bahn.data.server;

import com.jobarth.deutsche.bahn.data.domain.Timetable;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

/**
 * Implementation of {@link TimetableRequest}.
 */
public class TimetableRequestImpl implements TimetableRequest {

    private final String evaNo;
    private static final String DATE_FORMAT = "%s%s%s";
    private static final String PLAN_REQUEST_URL_FORMAT = "https://api.deutschebahn.com/timetables/v1/plan/%s/%s/%s";
    private static final String FUTURE_CHANGES_REQUEST_URL_FORMAT = "https://api.deutschebahn.com/timetables/v1/fchg/%s";
    private static final String RECENT_CHANGES_REQUEST_URL_FORMAT = "https://api.deutschebahn.com/timetables/v1/rchg/%s";


    public TimetableRequestImpl(String evaNo) {
        this.evaNo = evaNo;
    }

    @Override
    public Timetable getPlan(LocalDateTime datetime) throws IOException, InterruptedException, JAXBException {
        String year = String.valueOf(datetime.getYear() - 2000);
        String month = datetime.getMonthValue() > 9 ? String.valueOf(datetime.getMonthValue()) : "0" + datetime.getMonthValue();
        String day = datetime.getDayOfMonth() > 9 ? String.valueOf(datetime.getDayOfMonth()) : "0" + datetime.getDayOfMonth();
        String hour = datetime.getHour() > 9 ? String.valueOf(datetime.getHour()) : "0" + datetime.getHour();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(String.format(PLAN_REQUEST_URL_FORMAT, evaNo, String.format(DATE_FORMAT, year, month, day), hour)))
                .header("Authorization", "Bearer dec0445e9c29bf5bc4e8c1641c6ba1fc")
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        JAXBContext jaxbContext = JAXBContext.newInstance(Timetable.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (Timetable) unmarshaller.unmarshal(new StringReader(response.body()));
    }

    @Override
    public Timetable getRecentChanges() throws IOException, InterruptedException, JAXBException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(String.format(RECENT_CHANGES_REQUEST_URL_FORMAT, evaNo)))
                .header("Authorization", "Bearer dec0445e9c29bf5bc4e8c1641c6ba1fc")
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        JAXBContext jaxbContext = JAXBContext.newInstance(Timetable.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (Timetable) unmarshaller.unmarshal(new StringReader(response.body()));
    }

    @Override
    public Timetable getFutureChanges() throws IOException, InterruptedException, JAXBException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(String.format(FUTURE_CHANGES_REQUEST_URL_FORMAT, evaNo)))
                .header("Authorization", "Bearer dec0445e9c29bf5bc4e8c1641c6ba1fc")
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        JAXBContext jaxbContext = JAXBContext.newInstance(Timetable.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (Timetable) unmarshaller.unmarshal(new StringReader(response.body()));
    }

    public static void main(String[] args) throws IOException, InterruptedException, JAXBException {
        String berlinEva = "8011160";
        TimetableRequest timetableRequest = new TimetableRequestImpl(berlinEva);
        //timetableRequest.getPlan(LocalDateTime.now());
        Timetable plan = timetableRequest.getPlan(LocalDateTime.now());
        Timetable futureChanges = timetableRequest.getFutureChanges();
        plan.updateTimetable(futureChanges);
    }
}
