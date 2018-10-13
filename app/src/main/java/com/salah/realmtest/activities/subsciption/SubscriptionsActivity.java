package com.salah.realmtest.activities.subsciption;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.salah.realmtest.R;
import com.salah.realmtest.adapters.ListSubscriptionsAdapter;
import com.salah.realmtest.models.Athlete;
import com.salah.realmtest.models.Subscription;
import com.salah.realmtest.services.RealmService;

import io.realm.Realm;
import io.realm.RealmResults;

public class SubscriptionsActivity extends AppCompatActivity {

    public static Athlete athlete;
    private ListView lv_subscriptions;
    RealmService realmService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriptions);
        realmService = new RealmService(Realm.getDefaultInstance());
        lv_subscriptions = findViewById(R.id.lv_subscriptions);
        showdata();
    }

    private void showdata() {

        RealmResults<Subscription> subscriptions = athlete.getSubscriptions().where().findAll();
        if (subscriptions.isEmpty()) return;
        ListSubscriptionsAdapter adapter = new ListSubscriptionsAdapter(this,subscriptions);
        lv_subscriptions.setAdapter(adapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realmService.closeRealm();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showdata();
    }



    public void goToAddSubscription(View view) {
        AddSubscriptionActivity.athlete=athlete;
        Intent intent = new Intent(SubscriptionsActivity.this,AddSubscriptionActivity.class);
        startActivity(intent);
    }
}
