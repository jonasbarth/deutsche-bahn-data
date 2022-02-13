package com.jobarth.deutsche.bahn.data.db;

import com.jobarth.deutsche.bahn.data.db.repository.StationRepository;
import com.jobarth.deutsche.bahn.data.db.repository.TripCategoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@Configuration
@EnableNeo4jRepositories("com.jobarth.deutsche.bahn.data.db.repository")
public class RepositoryConfiguration {

    @Bean
    public TimetableWriter neo4jWriter(StationRepository stationRepository, TripCategoryRepository tripRepository) {
        return new Neo4jTimetableWriter(stationRepository, tripRepository);
    }
}
