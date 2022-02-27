package com.jobarth.deutsche.bahn.data.db.repository;

import com.jobarth.deutsche.bahn.data.db.domain.TimetableStopEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public interface TimetableStopRepository extends Neo4jRepository<TimetableStopEntity, String> {

    @Query("MATCH (stop:TimetableStop)-[:AT_STATION]-(station:Station) WHERE station.eva=$eva RETURN stop")
    Collection<TimetableStopEntity> findByStation(@Param("eva") String eva);
}
