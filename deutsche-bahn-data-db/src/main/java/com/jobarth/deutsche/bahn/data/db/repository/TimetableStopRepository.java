package com.jobarth.deutsche.bahn.data.db.repository;

import com.jobarth.deutsche.bahn.data.db.domain.TimetableStopEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Component;

@Component
public interface TimetableStopRepository extends Neo4jRepository<TimetableStopEntity, String> {
}
