package com.salah.realmtest.models;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Session extends RealmObject{
    @PrimaryKey
    private String id;
    private Date creationDate = new Date();
    private Date trainingDate;
    private Subscription subscriptions;
    private Manager addedManager;

    public String getId() {
        return id;
    }

    public Date getTrainingDate() {
        return trainingDate;
    }

    public void setTrainingDate(Date trainingDate) {
        this.trainingDate = trainingDate;
    }

    public Subscription getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Subscription subscriptions) {
        this.subscriptions = subscriptions;
    }

    public Manager getAddedManager() {
        return addedManager;
    }

    public void setAddedManager(Manager addedManager) {
        this.addedManager = addedManager;
    }

    public Date getCreationDate() {
        return creationDate;
    }
}
