package com.salah.realmtest.models;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TechnicalSheet extends RealmObject{
    @PrimaryKey
    private String id;
    private double weight;
    private double length;
    private String picturePath;
    private String otherInformations;
    private Date creationDate = new Date();
    private Athlete athlete;
    private Manager addedManager;

    public String getId() {
        return id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getOtherInformations() {
        return otherInformations;
    }

    public void setOtherInformations(String otherInformations) {
        this.otherInformations = otherInformations;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Athlete getAthlete() {
        return athlete;
    }

    public void setAthlete(Athlete athlete) {
        this.athlete = athlete;
    }

    public Manager getAddedManager() {
        return addedManager;
    }

    public void setAddedManager(Manager addedManager) {
        this.addedManager = addedManager;
    }
}
