package com.salah.realmtest.models;

import android.provider.ContactsContract;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Offer extends RealmObject{
    @PrimaryKey
    private String id;
    @Required
    private String titre;
    private String description;
    @Required
    private int duration ;
    @Required
    private String durationUnit;
    @Required
    private double price;
    private Date creationDate = new Date();
    private RealmList<Abonnement> abonnements;

    public String getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDurationUnit() {
        return durationUnit;
    }

    public void setDurationUnit(String durationUnit) {
        this.durationUnit = durationUnit;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public RealmList<Abonnement> getAbonnements() {
        return abonnements;
    }

    public void setAbonnements(RealmList<Abonnement> abonnements) {
        this.abonnements = abonnements;
    }
}
