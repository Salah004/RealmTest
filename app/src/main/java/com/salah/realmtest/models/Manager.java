package com.salah.realmtest.models;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Manager extends RealmObject{
    @PrimaryKey
    private String id ;
    private String firstName;
    private String lastName;
    private String picturePath;
    private int sex;
    private String phone;
    private String userName;
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

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
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

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public RealmList<TechnicalSheet> getSheets() {
        return sheets;
    }

    public void setSheets(RealmList<TechnicalSheet> sheets) {
        this.sheets = sheets;
    }
}
