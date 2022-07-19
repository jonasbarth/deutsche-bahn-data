package com.jobarth.deutsche.bahn.data.acquisition.server;

import com.jobarth.deutsche.bahn.data.acquisition.TimetableManagerImpl;
import com.jobarth.deutsche.bahn.data.acquisition.filter.CopyDepartedTimetableFilter;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class TimetableServiceImpl extends TimetableServiceGrpc.TimetableServiceImplBase {

    private final static Logger LOGGER = LoggerFactory.getLogger(TimetableServiceImpl.class);

    @Autowired
    private TimetableManagerImpl timetableManager;

    @Autowired
    private CopyDepartedTimetableFilter timetableFilter;

    @Override
    public void getDepartedAfter(Timetable.TimetableRequest request, StreamObserver<Timetable.TimetableResponse> responseObserver) {
        try {
            String eva = request.getEva();
            long timestamp = request.getDepartedAfter();
            LOGGER.info("Received request for {} and departed after {}", request.getEva(), timestamp);

            String stationName = timetableManager.get(eva).getStation();
            LocalDateTime departedAfter = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault());

            timetableManager.get(eva).getTimetableStops().stream()
                    .filter(t -> t.hasDepartedAfter(departedAfter))
                    .map(timetableStop -> Timetable.TimetableResponse.newBuilder()
                            .setEva(request.getEva())
                            .setStationName(stationName)
                            .setStopId(timetableStop.getId())
                            .setTripCategory(Timetable.TimetableResponse.TripCategory.valueOf(timetableStop.getTripLabel().getTripCategory()))
                            .setTrainNumber(Integer.parseInt(timetableStop.getTripLabel().getTrainNumber()))
                            .setPlannedArrival(timetableStop.getArrival().getPlannedTime())
                            .setActualArrival(timetableStop.getArrival().getChangedTime())
                            .setPlannedDeparture(timetableStop.getDeparture().getPlannedTime())
                            .setActualDeparture(timetableStop.getDeparture().getChangedTime())
                            .setPlannedArrivalPlatform(timetableStop.getArrival().getPlannedPlatform())
                            .setActualArrivalPlatform(timetableStop.getArrival().getChangedPlatform())
                            .setPlannedDeparturePlatform(timetableStop.getDeparture().getPlannedPlatform())
                            .setActualDeparturePlatform(timetableStop.getDeparture().getChangedPlatform())
                            .build())
                    .forEach(responseObserver::onNext);

            responseObserver.onCompleted();
        } catch (Exception e) {
            LOGGER.error("Exception occurred while fetching stops", e);
            responseObserver.onError(e);
        }
    }
}
