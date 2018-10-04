package com.salah.realmtest.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.salah.realmtest.R;
import com.salah.realmtest.adapters.ListAbonnementsAdapter;
import com.salah.realmtest.models.Abonnement;
import com.salah.realmtest.models.Offer;

import io.realm.RealmList;

public class OfferAbonnementsListActivity extends AppCompatActivity {

    public static Offer offer;
    private ListView lv_abonnements ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_abonnements_list);
        lv_abonnements = findViewById(R.id.lv_abonnements);
        RealmList<Abonnement> abonnements = offer.getAbonnements();
        ListAbonnementsAdapter adapter = new ListAbonnementsAdapter(this,abonnements.where().findAll());
        lv_abonnements.setAdapter(adapter);
    }
}
