package com.jobarth.deutsche.bahn.data.acquisition.request;

import com.jobarth.deutsche.bahn.data.domain.Timetable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private final static Logger LOGGER = LoggerFactory.getLogger(TimetableRequestImpl.class);

    private final String evaNo;
    private final TimetableRequestListener timetableRequestListener;
    private static final String DATE_FORMAT = "%s%s%s";
    private static final String PLAN_REQUEST_URL_FORMAT = "https://api.deutschebahn.com/timetables/v1/plan/%s/%s/%s";
    private static final String FUTURE_CHANGES_REQUEST_URL_FORMAT = "https://api.deutschebahn.com/timetables/v1/fchg/%s";
    private static final String RECENT_CHANGES_REQUEST_URL_FORMAT = "https://api.deutschebahn.com/timetables/v1/rchg/%s";

    private String bearerToken;

    public TimetableRequestImpl(String evaNo, TimetableRequestListener listener, String bearerToken) {
        this.evaNo = evaNo;
        this.timetableRequestListener = listener;
        this.bearerToken = bearerToken;
    }


    @Override
    public String getEvaNo() {
        return evaNo;
    }

    @Override
    public Timetable getPlan(LocalDateTime datetime) throws IOException, InterruptedException, JAXBException {
        String year = String.valueOf(datetime.getYear() - 2000);
        String month = prefixInt(datetime.getMonthValue());
        String day = prefixInt(datetime.getDayOfMonth());
        String hour = prefixInt(datetime.getHour());

        HttpRequest request = buildRequest(String.format(PLAN_REQUEST_URL_FORMAT, evaNo, String.format(DATE_FORMAT, year, month, day), hour));

        HttpClient httpClient = HttpClient.newHttpClient();
        LOGGER.info("Making HTTP request to get the timetable plan: {}.", request.uri());
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        switch (response.statusCode()) {

            case 200:
                LOGGER.info("Response {} OK. Unmarshalling XML response.", response.statusCode());
                Timetable timetable = unmarshalResponse(response.body());
                timetableRequestListener.onPlan(timetable);
                break;

            case 404:
                LOGGER.warn("Response {} Not found. Body {}", response.statusCode(), response.body());
                break;
            case 500:
                LOGGER.warn("Response {} Internal server error. Body {}", response.statusCode(), response.body());
                break;
            default:
                break;

        }
        return null;
    }

    @Override
    public Timetable getRecentChanges() throws IOException, InterruptedException, JAXBException {
        HttpRequest request = buildRequest(String.format(RECENT_CHANGES_REQUEST_URL_FORMAT, evaNo));

        HttpClient httpClient = HttpClient.newHttpClient();
        LOGGER.info("Making HTTP request to get recent changes: {}.", request.uri());
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        switch (response.statusCode()) {

            case 200:
                LOGGER.info("Response {} OK. Unmarshalling XML response.", response.statusCode());
                Timetable timetable = unmarshalResponse(response.body());
                timetableRequestListener.onRecentChanges(timetable);
                break;

            case 404:
                LOGGER.warn("Response {} Not found. Body {}", response.statusCode(), response.body());
                break;
            case 500:
                LOGGER.warn("Response {} Internal server error. Body {}", response.statusCode(), response.body());
                break;
            default:
                break;

        }
        return null;
    }

    @Override
    public Timetable getFutureChanges() throws IOException, InterruptedException, JAXBException {
        HttpRequest request = buildRequest(String.format(FUTURE_CHANGES_REQUEST_URL_FORMAT, evaNo));

        HttpClient httpClient = HttpClient.newHttpClient();
        LOGGER.info("Making HTTP request to get future changes: {}.", request.uri());
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        switch (response.statusCode()) {

            case 200:
                LOGGER.info("Response {} OK. Unmarshalling XML response.", response.statusCode());
                Timetable timetable = unmarshalResponse(response.body());
                timetableRequestListener.onFutureChanges(timetable);
                break;

            case 404:
                LOGGER.warn("Response {} Not found. Body {}", response.statusCode(), response.body());
                break;
            case 500:
                LOGGER.warn("Response {} Internal server error. Body {}", response.statusCode(), response.body());
                break;
            default:
                break;
        }
        return null;
    }

    private HttpRequest buildRequest(String uri) {
        return HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri))
                .header("Authorization", "Bearer " + bearerToken)
                .build();
    }
    
    private Timetable unmarshalResponse(String responseBody) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Timetable.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (Timetable) unmarshaller.unmarshal(new StringReader(responseBody));
    }

    private static String prefixInt(int i) {
        return i > 9 ? String.valueOf(i) : "0" + i;
    }
}
