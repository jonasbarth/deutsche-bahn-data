package com.jobarth.deutsche.bahn.data.acquisition.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TimetableServer {

    @Autowired
    private TimetableServiceImpl timetableService;

    public void start() throws InterruptedException, IOException {
        Server server = ServerBuilder
                .forPort(8080)
                .addService(timetableService)
                .build();
        server.start();
        server.awaitTermination();
    }
}
