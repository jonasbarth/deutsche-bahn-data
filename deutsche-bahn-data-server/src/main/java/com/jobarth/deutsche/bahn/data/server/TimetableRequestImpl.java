package com.jobarth.deutsche.bahn.data.server;

import com.jobarth.deutsche.bahn.data.domain.Change;
import com.jobarth.deutsche.bahn.data.domain.Timetable;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.Collection;

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
    public Timetable getPlan(LocalDateTime datetime) throws IOException, InterruptedException, ParserConfigurationException, SAXException {
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

        XmlTimetableParser parser = new XmlTimetableParser(response.body());
        parser.parsePlannedTimetableStops();
        return null;
    }

    @Override
    public Collection<Change> getRecentChanges() throws IOException, InterruptedException, ParserConfigurationException, SAXException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(String.format(RECENT_CHANGES_REQUEST_URL_FORMAT, evaNo)))
                .header("Authorization", "Bearer dec0445e9c29bf5bc4e8c1641c6ba1fc")
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        TimetableParser parser = new XmlTimetableParser(response.body());
        return parser.parseChanges();
    }

    @Override
    public Collection<Change> getFutureChanges() throws IOException, InterruptedException, ParserConfigurationException, SAXException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(String.format(FUTURE_CHANGES_REQUEST_URL_FORMAT, evaNo)))
                .header("Authorization", "Bearer dec0445e9c29bf5bc4e8c1641c6ba1fc")
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        TimetableParser parser = new XmlTimetableParser(response.body());
        return parser.parseChanges();
    }

    public static void main(String[] args) throws IOException, InterruptedException, ParserConfigurationException, SAXException {
        String berlinEva = "8011160";
        TimetableRequest timetableRequest = new TimetableRequestImpl(berlinEva);
        //timetableRequest.getPlan(LocalDateTime.now());
        timetableRequest.getRecentChanges();
    }
}
