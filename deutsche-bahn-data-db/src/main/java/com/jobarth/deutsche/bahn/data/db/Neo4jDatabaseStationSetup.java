package com.jobarth.deutsche.bahn.data.db;

import com.jobarth.deutsche.bahn.data.db.domain.StationEntity;
import com.jobarth.deutsche.bahn.data.db.domain.TripCategoryLabel;
import com.jobarth.deutsche.bahn.data.db.repository.StationRepository;
import com.jobarth.deutsche.bahn.data.db.repository.TimetableStopRepository;
import com.jobarth.deutsche.bahn.data.db.repository.TripCategoryRepository;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class Neo4jDatabaseStationSetup {

    public static void main(String[] args) {
        SpringApplication.run(Neo4jDatabaseStationSetup.class, args);
    }
    /*
    @Bean
    CommandLineRunner stationRunner(StationRepository stationRepository, ResourceLoader resourceLoader) {
        return args -> {
            stationRepository.deleteAll();
            Resource resource = resourceLoader.getResource("db_all_stations.csv");
            List<CsvStation> beans = new CsvToBeanBuilder(new FileReader(resource.getFile()))
                    .withType(CsvStation.class)
                    .build()
                    .parse();

            Collection<StationEntity> stations = beans.stream()
                    .map(bean -> new StationEntity(bean.getEva(), bean.getName(), bean.getLongitude(), bean.getLatitude()))
                    .collect(Collectors.toList());
            stationRepository.saveAll(stations);
        };
    }

    @Bean
    CommandLineRunner tripCategoryRunner(TripCategoryRepository tripCategoryRepository) {
        return args -> {
            tripCategoryRepository.deleteAll();
            Collection<TripCategoryLabel> tripCategoryLabels = Arrays.stream(new String[]{"ICE", "RB", "RE", "IC", "EC"})
                    .map(TripCategoryLabel::new)
                    .collect(Collectors.toList());

            tripCategoryRepository.saveAll(tripCategoryLabels);
        };
    }

    @Bean
    CommandLineRunner timetableStopRunner(TimetableStopRepository timetableStopRepository) {
        return args -> {
            timetableStopRepository.deleteAll();
        };
    }

    public static class CsvStation {

        @CsvBindByName(column = "EVA_NR")
        private String eva;

        @CsvBindByName(column = "NAME")
        private String name;

        @CsvBindByName(column = "Laenge")
        private double longitude;

        @CsvBindByName(column = "Breite")
        private double latitude;

        public CsvStation(String eva, String name, double longitude, double latitude) {
            this.eva = eva;
            this.name = name;
            this.longitude = longitude;
            this.latitude = latitude;
        }

        public CsvStation() {
        }

        public String getEva() {
            return eva;
        }

        public void setEva(String eva) {
            this.eva = eva;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        @Override
        public String toString() {
            return "CsvStation{" +
                    "eva='" + eva + '/' +
                    ", name='" + name + '/' +
                    ", longitude=" + longitude +
                    ", latitude=" + latitude +
                    '}';
        }
    } */
}
