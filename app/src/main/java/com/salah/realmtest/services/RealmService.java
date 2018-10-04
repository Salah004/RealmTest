package com.salah.realmtest.services;

import com.salah.realmtest.models.Subscription;
import com.salah.realmtest.models.Offer;
import com.salah.realmtest.models.Athlete;

import java.util.Date;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmList;
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

    public void addSubscriptionAsync(final Athlete athlete, final Offer offer, final Date startDate,  final int duration) {

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Subscription subscription = realm.createObject(Subscription.class , UUID.randomUUID().toString());
                subscription.setAthlete(athlete);
                subscription.setOffer(offer);
                subscription.setStartDate(startDate);
                subscription.setDuration(duration);
                athlete.getSubscriptions().add(subscription);
                offer.getSubscriptions().add(subscription);
            }
        }/*, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {

            }
        }*/);
    }

    public void addOfferAsync(final String title, final String description, final int duration , final String durationUnit , final double price) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Offer offer = realm.createObject(Offer.class , UUID.randomUUID().toString());
                offer.setTitle(title);
                offer.setDescription(description);
                offer.setDuration(duration);
                offer.setDurationUnit(durationUnit);
                offer.setPrice(price);
            }
        }/*, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {

            }
        }*/);
    }

    public void addAthlete(final String firstName, final String lastName, final String phone) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Athlete athlete = realm.createObject(Athlete.class , UUID.randomUUID().toString());
                athlete.setFirstName(firstName);
                athlete.setLastName(lastName);
                athlete.setPhone(phone);
            }
        }/*, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {

            }
        }*/);
    }

}
