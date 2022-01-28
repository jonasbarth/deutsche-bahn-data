package com.jobarth.deutsche.bahn.data.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "tl")
public class TripLabel {

    private String filterFlag;
    private String tripType;
    private String owner;
    private String tripCategory;
    private String trainNumber;

    public TripLabel(String filterFlag, String tripType, String owner, String tripCategory, String trainNumber) {
        this.filterFlag = filterFlag;
        this.tripType = tripType;
        this.owner = owner;
        this.tripCategory = tripCategory;
        this.trainNumber = trainNumber;
    }

    public TripLabel() {
    }

    @XmlAttribute(name = "f")
    public String getFilterFlag() {
        return filterFlag;
    }

    public void setFilterFlag(String filterFlag) {
        if (filterFlag != null)
            this.filterFlag = filterFlag;
    }

    @XmlAttribute(name = "t")
    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        if (tripType != null)
            this.tripType = tripType;
    }

    @XmlAttribute(name = "o")
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        if (owner != null)
            this.owner = owner;
    }

    @XmlAttribute(name = "c")
    public String getTripCategory() {
        return tripCategory;
    }

    public void setTripCategory(String tripCategory) {
        if (tripCategory != null)
            this.tripCategory = tripCategory;
    }

    @XmlAttribute(name = "n")
    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        if (trainNumber != null)
            this.trainNumber = trainNumber;
    }

    public void update(TripLabel tripLabel) {
        if (tripLabel == null) {
            return;
        }
        this.setFilterFlag(tripLabel.getFilterFlag());
        this.setOwner(tripLabel.getOwner());
        this.setTripCategory(tripLabel.getTripCategory());
        this.setTrainNumber(tripLabel.getTrainNumber());
        this.setTripType(tripLabel.getTripType());
    }
}
