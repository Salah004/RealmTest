package com.salah.realmtest.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.salah.realmtest.R;
import com.salah.realmtest.adapters.ListAbonnementsAdapter;
import com.salah.realmtest.models.Abonnement;
import com.salah.realmtest.models.Person;

import io.realm.RealmList;
import io.realm.RealmResults;

public class PersonAbonnementsListActivity extends AppCompatActivity {

    public static Person person;
    private ListView lv_abonnements ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_abonnements_list);
        lv_abonnements = findViewById(R.id.lv_abonnements);
        RealmList<Abonnement> abonnements = person.getAbonnements();
        ListAbonnementsAdapter adapter = new ListAbonnementsAdapter(this,abonnements.where().findAll());
        lv_abonnements.setAdapter(adapter);
    }
}
