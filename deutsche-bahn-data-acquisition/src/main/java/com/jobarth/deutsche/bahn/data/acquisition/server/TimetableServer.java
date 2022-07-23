package com.jobarth.deutsche.bahn.data.acquisition.server;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
        ExecutorService executor = new ThreadPoolExecutor(100, 1000, 60L, TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                new ThreadFactoryBuilder()
                        .setDaemon(true)
                        .setNameFormat("deutsche-bahn-data-acquisition-executor-%")
                        .build());

        LOGGER.info("Starting TimetableServer on port {}", PORT);
        Server server = ServerBuilder
                .forPort(PORT)
                .addService(timetableService)
                .executor(executor)
                .build();
        server.start();
        server.awaitTermination();
    }
}
