package com.salah.realmtest.services;

import com.salah.realmtest.models.Debt;
import com.salah.realmtest.models.Manager;
import com.salah.realmtest.models.Session;
import com.salah.realmtest.models.Subscription;
import com.salah.realmtest.models.Offer;
import com.salah.realmtest.models.Athlete;

import java.util.Date;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmService {

    private final Realm mRealm;

    public RealmService(final Realm realm) {
        mRealm = realm;
    }

    public void closeRealm() {
        mRealm.close();
    }

    public RealmResults<Athlete> getAllAthletes(){
        return mRealm.where(Athlete.class).findAll();
    }

    public RealmResults<Offer> getAllOffers(){
        return mRealm.where(Offer.class).findAll();
    }

    public RealmResults<Subscription> getAllSubscriptions(){
        return mRealm.where(Subscription.class).findAll();
    }

    public Subscription addSubscription(final Athlete athlete, final Offer offer, final Date startDate, final int duration, final double amountDebt, final Manager manager) {
        final Subscription[] mSubscriptions = new Subscription[1];
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Subscription subscription = realm.createObject(Subscription.class , UUID.randomUUID().toString());
                subscription.setAthlete(athlete);
                subscription.setOffer(offer);
                subscription.setStartDate(startDate);
                subscription.setDuration(duration);
                subscription.setAddedManager(manager);
                athlete.getSubscriptions().add(subscription);
                offer.getSubscriptions().add(subscription);
                manager.getSubscriptions().add(subscription);
                if (amountDebt!=0){
                    Debt debt = realm.createObject(Debt.class , UUID.randomUUID().toString());
                    debt.setAmount(amountDebt);
                    debt.setDescription("Subscription");
                    debt.setAthlete(athlete);
                    debt.setAddedManager(manager);
                    manager.getDebts().add(debt);
                    athlete.getDebts().add(debt);
                }
                mSubscriptions[0]=subscription;
                //if (debt!=0)addDebt(debt,"subscription",athlete,manager);
            }
        });
        return mSubscriptions[0];
    }

    public Offer addOffer(final String title, final String description, final int duration, final int durationUnit, final double price, final Boolean open, final int numberSessions, final Manager manager) {
        final Offer[] mOffers = new Offer[1];
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Offer offer = realm.createObject(Offer.class , UUID.randomUUID().toString());
                offer.setTitle(title);
                offer.setDescription(description);
                offer.setDuration(duration);
                offer.setDurationUnit(durationUnit);
                offer.setPrice(price);
                offer.setOpen(open);
                offer.setSessionNumber(numberSessions);
                offer.setAddedManager(manager);
                manager.getOffers().add(offer);
                mOffers[0] = offer;
            }
        });
        return mOffers[0];
    }

    public Athlete addAthlete(final String firstName, final String lastName, final String phone, final int gender, final String picturePath, final Manager manager) {
        final Athlete[] mAthlete = new Athlete[1];
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Athlete athlete = realm.createObject(Athlete.class , UUID.randomUUID().toString());
                athlete.setFirstName(firstName);
                athlete.setLastName(lastName);
                athlete.setPhone(phone);
                athlete.setGender(gender);
                athlete.setPicturePath(picturePath);
                athlete.setAddedManager(manager);
                manager.getAthletes().add(athlete);
                mAthlete[0] = athlete;
            }
        });
        return mAthlete[0];
    }

    public Manager Login(String userName, String password) {
        return mRealm.where(Manager.class).equalTo("userName",userName).equalTo("password",password).findFirst();
        //return mRealm.where(Manager.class).findFirst();

    }

    public Manager AddManager(final String userName, final String password, final String firstName, final String lastName, final String phone, final int gender, final int role, final String picturePath, final Manager addedManager) {
        final Manager[] mManagers = new Manager[1];
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Manager manager = realm.createObject(Manager.class , userName);
                manager.setPassword(password);
                manager.setFirstName(firstName);
                manager.setLastName(lastName);
                manager.setPhone(phone);
                manager.setGender(gender);
                manager.setRole(role);
                manager.setPicturePath(picturePath);
                manager.setAddedManager(addedManager);
                addedManager.getManagers().add(manager);
                mManagers[0] = manager;
            }
        });
        return mManagers[0];
    }

    public RealmResults<Manager> getAllManagers() {
        return mRealm.where(Manager.class).findAll();
    }

    public RealmResults<Debt> getAllDebts() {
        return mRealm.where(Debt.class).findAll();
    }

    public Debt addDebt(final double amount, final String description, final Athlete athlete, final Manager manager) {
        final Debt[] mDebts = new Debt[1];
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Debt debt = realm.createObject(Debt.class , UUID.randomUUID().toString());
                debt.setAmount(amount);
                debt.setDescription(description);
                debt.setAthlete(athlete);
                debt.setAddedManager(manager);
                manager.getDebts().add(debt);
                athlete.getDebts().add(debt);
                mDebts[0] = debt;
            }
        });
        return mDebts[0];
    }

    public Manager AddOwner(final String userName, final String password, final String firstName, final String lastName, final String phone, final int gender, final int role, final String picturePath) {
        final Manager[] mManagers = new Manager[1];
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Manager manager = realm.createObject(Manager.class , userName);
                manager.setPassword(password);
                manager.setFirstName(firstName);
                manager.setLastName(lastName);
                manager.setPhone(phone);
                manager.setGender(gender);
                manager.setRole(role);
                manager.setPicturePath(picturePath);
                mManagers[0] = manager;
            }
        });
        return mManagers[0];
    }

    public RealmResults<Session> getAllSessions() {
        return mRealm.where(Session.class).findAll();
    }

    public void addSession(final Subscription subscription, final Manager manager) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Session session = realm.createObject(Session.class , UUID.randomUUID().toString());
                session.setSubscriptions(subscription);
                session.setAddedManager(manager);
                session.setTrainingDate(new Date());
                manager.getSessions().add(session);
                subscription.getSessions().add(session);
            }
        });
    }
}
