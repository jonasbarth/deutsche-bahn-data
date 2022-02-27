package com.jobarth.deutsche.bahn.data.db;

import com.jobarth.deutsche.bahn.data.db.api.Neo4jTimetableWriter;
import com.jobarth.deutsche.bahn.data.db.api.TimetableWriter;
import com.jobarth.deutsche.bahn.data.db.repository.StationRepository;
import com.jobarth.deutsche.bahn.data.db.repository.TimetableStopRepository;
import com.jobarth.deutsche.bahn.data.db.repository.TripCategoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@Configuration
@EnableNeo4jRepositories("com.jobarth.deutsche.bahn.data.db.repository")
public class RepositoryConfiguration {

    @Bean
    public TimetableWriter neo4jWriter(StationRepository stationRepository, TripCategoryRepository tripRepository, TimetableStopRepository timetableStopRepository) {
        return new Neo4jTimetableWriter(stationRepository, tripRepository, timetableStopRepository);
    }
}
