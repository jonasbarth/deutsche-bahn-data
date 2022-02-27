package com.jobarth.deutsche.bahn.data.db;

import com.jobarth.deutsche.bahn.data.db.domain.StationEntity;
import com.jobarth.deutsche.bahn.data.db.domain.TimetableStopEntity;
import com.jobarth.deutsche.bahn.data.db.repository.StationRepository;
import com.jobarth.deutsche.bahn.data.db.repository.TimetableStopRepository;
import com.jobarth.deutsche.bahn.data.db.repository.TripCategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Collection;

@SpringBootApplication
@ComponentScan("com.jobarth.deutsche.bahn.data.db")
public class ExtractDataTry {

    public static void main(String[] args) {
        SpringApplication.run(ExtractDataTry.class);
    }

    @Bean
    CommandLineRunner extract(StationRepository stationRepository, TripCategoryRepository tripCategoryRepository, TimetableStopRepository timetableStopRepository) {
        return args -> {
            String berlinEva = "8011160";
            StationEntity berlin = stationRepository.findByEva(berlinEva);
            Collection<TimetableStopEntity> stops = timetableStopRepository.findByStation(berlinEva);
            System.out.println(stops.size());
        };
    }
}
