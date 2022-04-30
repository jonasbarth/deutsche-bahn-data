package com.jobarth.deutsche.bahn.data.acquisition.server;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.TimeUnit;

public class TimetableClient {

    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .keepAliveTime(2, TimeUnit.MINUTES)
                .usePlaintext()
                .build();

        //HelloWorldServiceGrpc.HelloWorldServiceStub stub = HelloWorldServiceGrpc.newStub(channel);
        TimetableServiceGrpc.TimetableServiceStub stub = TimetableServiceGrpc.newStub(channel);

        stub.getDeparted(Timetable.TimetableRequest.newBuilder()
                .setEva("8011160").build(), new StreamObserver<Timetable.TimetableResponse>() {
            @Override
            public void onNext(Timetable.TimetableResponse value) {
                System.out.println(value.getEva());

            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getLocalizedMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Completed");
            }
        });

        while (true) {

        }
        //channel.shutdown();
    }
}
