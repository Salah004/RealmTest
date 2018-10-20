package com.salah.realmtest.models;

import android.graphics.Bitmap;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Manager extends RealmObject{
    @PrimaryKey
    private String userName;
    private String firstName;
    private String lastName;
    private String pick;
    private int gender;
    private String phone;
    private String password;
    private Date creationDate = new Date();
    private RealmList<Athlete> athletes;
    private RealmList<Offer> offers;
    private RealmList<Subscription> subscriptions;
    private RealmList<Session> sessions;
    private RealmList<Debt> debts;
    private RealmList<TechnicalSheet> sheets;
    private RealmList<Manager> managers;
    private Manager addedManager;
    private int role;

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

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public RealmList<Athlete> getAthletes() {
        return athletes;
    }

    public void setAthletes(RealmList<Athlete> athletes) {
        this.athletes = athletes;
    }

    public RealmList<Offer> getOffers() {
        return offers;
    }

    public void setOffers(RealmList<Offer> offers) {
        this.offers = offers;
    }

    public RealmList<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(RealmList<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public RealmList<Session> getSessions() {
        return sessions;
    }

    public void setSessions(RealmList<Session> sessions) {
        this.sessions = sessions;
    }

    public Manager getAddedManager() {
        return addedManager;
    }

    public void setAddedManager(Manager addedManager) {
        this.addedManager = addedManager;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public RealmList<Debt> getDebts() {
        return debts;
    }

    public void setDebts(RealmList<Debt> debts) {
        this.debts = debts;
    }

    public RealmList<Manager> getManagers() {
        return managers;
    }

    public void setManagers(RealmList<Manager> managers) {
        this.managers = managers;
    }

    public String  getPick() {
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
