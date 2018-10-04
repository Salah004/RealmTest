package com.salah.realmtest.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.salah.realmtest.R;

import com.salah.realmtest.adapters.ListSubscriptionsAdapter;
import com.salah.realmtest.models.Athlete;
import com.salah.realmtest.models.Subscription;

import io.realm.RealmList;

public class AthleteSubscriptionsListActivity extends AppCompatActivity {

    public static Athlete athlete;
    private ListView lv_subscriptions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athlete_subscriptions_list);
        lv_subscriptions = findViewById(R.id.lv_subscriptions);
        RealmList<Subscription> subscriptions = athlete.getSubscriptions();
        ListSubscriptionsAdapter adapter = new ListSubscriptionsAdapter(this,subscriptions.where().findAll());
        lv_subscriptions.setAdapter(adapter);
    }
}
