package com.salah.realmtest.models;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Subscription extends RealmObject{
    @PrimaryKey
    private String id;
    private Offer offer;
    private Athlete athlete;
    private Date creationDate = new Date();
    private Date startDate ;
    private int duration ;
    private Manager addedManager;
    private RealmList<Session> sessions;

    public String getId() {
        return id;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public Athlete getAthlete() {
        return athlete;
    }

    public void setAthlete(Athlete athlete) {
        this.athlete = athlete;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Manager getAddedManager() {
        return addedManager;
    }

    public void setAddedManager(Manager addedManager) {
        this.addedManager = addedManager;
    }

    public RealmList<Session> getSessions() {
        return sessions;
    }

    public void setSessions(RealmList<Session> sessions) {
        this.sessions = sessions;
    }
}
