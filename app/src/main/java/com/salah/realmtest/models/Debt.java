package com.salah.realmtest.models;

import java.util.Date;

public class Debt {
    private String id;
    private Athlete athlete;
    private double sum;
    private String description;
    private Date date = new Date();
    private Manager addedManager;

    public String getId() {
        return id;
    }

    public Athlete getAthlete() {
        return athlete;
    }

    public void setAthlete(Athlete athlete) {
        this.athlete = athlete;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public Manager getAddedManager() {
        return addedManager;
    }

    public void setAddedManager(Manager addedManager) {
        this.addedManager = addedManager;
    }
}
