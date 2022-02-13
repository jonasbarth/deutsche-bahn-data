package com.jobarth.deutsche.bahn.data.db.repository;

import com.jobarth.deutsche.bahn.data.db.domain.TripCategoryLabel;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Component;

@Component
public interface TripCategoryRepository extends Neo4jRepository<TripCategoryLabel, String> {

    TripCategoryLabel findByTripCategory(String name);
}
