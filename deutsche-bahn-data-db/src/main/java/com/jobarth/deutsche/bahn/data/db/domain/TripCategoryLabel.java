package com.jobarth.deutsche.bahn.data.db.domain;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

/**
 *
 */
@Node("TripCategory")
public class TripCategoryLabel {

    @Id
    private final String tripCategory;

    public TripCategoryLabel(String tripCategory) {
        this.tripCategory = tripCategory;
    }

    public String getTripCategory() {
        return tripCategory;
    }
}
