package com.jobarth.deutsche.bahn.data.acquisition.server;

import com.jobarth.deutsche.bahn.data.acquisition.TimetableManagerImpl;
import com.jobarth.deutsche.bahn.data.acquisition.filter.CopyDepartedTimetableFilter;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class TimetableServiceImpl extends TimetableServiceGrpc.TimetableServiceImplBase {

    @Autowired
    private TimetableManagerImpl timetableManager;

    @Autowired
    private CopyDepartedTimetableFilter timetableFilter;

    @Override
    public void getDepartedAfter(Timetable.TimetableRequest request, StreamObserver<Timetable.TimetableResponse> responseObserver) {
        try {
            String eva = request.getEva();
            String stationName = timetableManager.get(eva).getStation();
            long timestamp = request.getDepartedAfter();

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
            responseObserver.onError(e);
        }
    }
}
