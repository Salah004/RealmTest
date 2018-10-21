package com.salah.realmtest.activities.athlete;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.salah.realmtest.R;
import com.salah.realmtest.models.Athlete;
import com.salah.realmtest.services.RealmService;

import io.realm.Realm;

public class AthleteActivity extends AppCompatActivity {


    public static Athlete athlete;
    private RealmService realmService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athlete);
        realmService = new RealmService(Realm.getDefaultInstance());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realmService.closeRealm();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}