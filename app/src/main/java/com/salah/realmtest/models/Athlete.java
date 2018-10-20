package com.salah.realmtest.models;

import android.graphics.Bitmap;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Athlete extends RealmObject{
    @PrimaryKey
    private String id ;
    private String firstName;
    private String lastName;
    private String pick;
    private int gender;
    private String phone;
    private Date creationDate = new Date();
    private RealmList<Subscription> subscriptions;
    private RealmList<Debt> debts;
    private RealmList<TechnicalSheet> sheets;
    private Manager addedManager;


    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public RealmList<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(RealmList<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public Manager getAddedManager() {
        return addedManager;
    }

    public void setAddedManager(Manager addedManager) {
        this.addedManager = addedManager;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public RealmList<Debt> getDebts() {
        return debts;
    }

    public void setDebts(RealmList<Debt> debts) {
        this.debts = debts;
    }

    public String getPick() {
        return pick;
    }

    public void setPick(String pick) {
        this.pick = pick;
    }

    public RealmList<TechnicalSheet> getSheets() {
        return sheets;
    }

    public void setSheets(RealmList<TechnicalSheet> sheets) {
        this.sheets = sheets;
    }
}
