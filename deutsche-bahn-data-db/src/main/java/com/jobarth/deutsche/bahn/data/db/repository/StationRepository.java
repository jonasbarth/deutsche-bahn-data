package com.jobarth.deutsche.bahn.data.db.repository;

import com.jobarth.deutsche.bahn.data.db.domain.StationEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Component;

@Component
public interface StationRepository extends Neo4jRepository<StationEntity, String> {
    StationEntity findByEva(String eva);

    StationEntity findByName(String name);
}
