package com.jobarth.deutsche.bahn.data.acquisition.server;

import com.jobarth.deutsche.bahn.data.acquisition.TimetableManagerImpl;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StationServiceImpl extends StationServiceGrpc.StationServiceImplBase {

    @Autowired
    private TimetableManagerImpl timetableManager;

    @Override
    public void getAllStations(Station.StationRequest request, StreamObserver<Station.StationResponse> responseObserver) {
        try {
            timetableManager.getStations().stream()
                    .map(station -> Station.StationResponse.newBuilder()
                            .setEva(station.getEva())
                            .setStationName(station.getName())
                            .build())
                    .forEach(responseObserver::onNext);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}
