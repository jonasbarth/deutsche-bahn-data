package com.jobarth.deutsche.bahn.data.acquisition.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * A service for serving the {@link TimetableServiceImpl}
 */
@Component
public class TimetableServer {

    private final static Logger LOGGER = LoggerFactory.getLogger(TimetableServer.class);
    private final static int PORT = 5000;

    @Autowired
    private TimetableServiceImpl timetableService;

    public void start() throws InterruptedException, IOException {
        LOGGER.info("Starting TimetableServer on port {}", PORT);
        Server server = ServerBuilder
                .forPort(PORT)
                .addService(timetableService)
                .build();
        server.start();
        server.awaitTermination();
    }
}
