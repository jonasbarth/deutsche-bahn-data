package com.jobarth.deutsche.bahn.data.acquisition.server;

import com.google.common.collect.Lists;
import com.jobarth.deutsche.bahn.data.acquisition.TimetableManagerImpl;
import com.jobarth.deutsche.bahn.data.acquisition.filter.CopyDepartedTimetableFilter;
import com.jobarth.deutsche.bahn.data.acquisition.filter.InPlaceDepartedTimetableFilter;
import com.jobarth.deutsche.bahn.data.domain.TimetableStop;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimetableServiceImpl extends TimetableServiceGrpc.TimetableServiceImplBase {

    @Autowired
    private TimetableManagerImpl timetableManager;

    @Autowired
    private CopyDepartedTimetableFilter timetableFilter;

    @Override
    public void getTimetableStops(Timetable.TimetableRequest request, StreamObserver<Timetable.TimetableResponse> responseObserver) {
        //keep stream open in a while loop

        String eva = request.getEva();
        String stationName = timetableManager.get(eva).getStation();

        timetableManager.get(eva).getTimetableStops().stream()
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

        //responseObserver.onCompleted();
    }

    @Override
    public void getDeparted(Timetable.TimetableRequest request, StreamObserver<Timetable.TimetableResponse> responseObserver) {
        String eva = request.getEva();
        String stationName = timetableManager.get(eva).getStation();
        List<TimetableStop> previousStops = Lists.newArrayList();
        while (true) {
            List<TimetableStop> newStops = timetableFilter.apply(timetableManager.get(eva)).getTimetableStops();//new CopyDepartedTimetableFilter().apply(timetableManager.get(eva)).getTimetableStops();
            List<TimetableStop> finalPreviousStops = previousStops;
            newStops.stream()
                    //.filter(timetableStop -> finalPreviousStops.stream().anyMatch(previousTimetableStop -> previousTimetableStop.getId().equals(timetableStop.getId())))
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
            previousStops = newStops;
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                responseObserver.onError(e);
            }
        }
        //responseObserver.onCompleted();
    }
}
