package com.jobarth.deutsche.bahn.data.domain;

/**
 * A Deutsche Bahn train station, uniquely identifiable by its eva number.
 */
public class Station {

    private final String name;
    private final String eva;

    public Station(String name, String eva) {
        this.name = name;
        this.eva = eva;
    }

    public String getName() {
        return name;
    }

    public String getEva() {
        return eva;
    }
}
