package com.jobarth.deutsche.bahn.data.acquisition.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StationServer {

    @Autowired
    private StationServiceImpl stationService;

    public void start() throws InterruptedException, IOException {
        Server server = ServerBuilder
                .forPort(5001)
                .addService(stationService)
                .build();
        server.start();
        server.awaitTermination();
    }
}
