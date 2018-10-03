package com.salah.realmtest.services;

import com.salah.realmtest.models.Abonnement;
import com.salah.realmtest.models.Offer;
import com.salah.realmtest.models.Person;

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

    public RealmResults<Person> getAllPersons(){
        return mRealm.where(Person.class).findAll();
    }

    public RealmResults<Offer> getAllOffers(){
        return mRealm.where(Offer.class).findAll();
    }

    public RealmResults<Abonnement> getAllAbonnements(){
        return mRealm.where(Abonnement.class).findAll();
    }

    public void addAbonnementAsync(final Person person, final Offer offer, final int duration) {

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Abonnement abonnement = realm.createObject(Abonnement.class , UUID.randomUUID().toString());
                abonnement.setPerson(person);
                abonnement.setOffer(offer);
                abonnement.setDuration(duration);
                person.getAbonnements().add(abonnement);
                offer.getAbonnements().add(abonnement);
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
                offer.setTitre(title);
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

    public void addPersonAsync(final String nom, final String prenom, final String phone) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Person person = realm.createObject(Person.class , UUID.randomUUID().toString());
                person.setNom(nom);
                person.setPrenom(prenom);
                person.setTelephone(phone);
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
