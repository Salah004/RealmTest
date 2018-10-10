package com.salah.realmtest.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.salah.realmtest.R;
import com.salah.realmtest.activities.athlete.AthletesActivity;
import com.salah.realmtest.activities.offer.OffersActivity;
import com.salah.realmtest.activities.subsciption.SubscriptionsActivity;
import com.salah.realmtest.models.Manager;
import com.salah.realmtest.services.RealmService;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    public static Manager manager;
    private RealmService realmService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realmService = new RealmService(Realm.getDefaultInstance());
    }

    public void goToAthletes(View view) {
//        try{
//            Toast.makeText(this,manager.getFirstName(),Toast.LENGTH_LONG).show();
//        }catch (Exception e){
//            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
//        }
        Intent intent = new Intent(this,AthletesActivity.class);
        startActivity(intent);
    }

    public void goToOffers(View view) {
        Intent intent = new Intent(this,OffersActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realmService.closeRealm();
    }
}
