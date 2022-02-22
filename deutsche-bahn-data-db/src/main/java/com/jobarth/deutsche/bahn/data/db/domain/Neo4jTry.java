package com.jobarth.deutsche.bahn.data.db.domain;

import com.jobarth.deutsche.bahn.data.db.Neo4jTimetableWriter;
import org.neo4j.driver.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.neo4j.core.DatabaseSelectionProvider;
import org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.repository.config.Neo4jRepositoryConfigurationExtension;
import org.springframework.transaction.TransactionManager;

//@SpringBootApplication
//@ComponentScan("com.jobarth.deutsche.bahn.data.db")
public class Neo4jTry {

    @Autowired
    private Neo4jTimetableWriter neo4jTimetableWriter;

    public static void main(String[] args) {
        SpringApplication.run(Neo4jTry.class, args);
    }
    /*
    @Bean
    CommandLineRunner test(/*StationRepository repository, TripCategoryRepository tripRepository) {
        return args -> {
            int a = 10;
            neo4jTimetableWriter.write(null);

            stationRepository.deleteAll();

            StationEntity berlin = new StationEntity("8011160", "Berlin Hbf", 23.35653, 65.5135135);
            stationRepository.save(berlin);

            StationEntity search = stationRepository.findByEva("8011160");
            System.out.println("Searched " + search.toString());

            tripCategoryRepository.deleteAll();

            TripCategoryLabel ICE = new TripCategoryLabel("ICE");
            tripCategoryRepository.save(ICE);
        };
    } */

    @Bean(Neo4jRepositoryConfigurationExtension.DEFAULT_TRANSACTION_MANAGER_BEAN_NAME)
    public TransactionManager reactiveTransactionManager(
            Driver driver,
            DatabaseSelectionProvider databaseNameProvider) {
        return new Neo4jTransactionManager(driver, databaseNameProvider);
    }
}
