package com.salah.realmtest.activities.offer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.salah.realmtest.R;
import com.salah.realmtest.adapters.ListSubscriptionsAdapter;
import com.salah.realmtest.models.Offer;
import com.salah.realmtest.models.Subscription;

import io.realm.RealmList;

public class OfferSubscriptionsListActivity extends AppCompatActivity {

    public static Offer offer;
    private ListView lv_subscriptions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_subscriptions_list);
        lv_subscriptions = findViewById(R.id.lv_subscriptions);
        RealmList<Subscription> subscriptions = offer.getSubscriptions();
        ListSubscriptionsAdapter adapter = new ListSubscriptionsAdapter(this,subscriptions.where().findAll());
        lv_subscriptions.setAdapter(adapter);
    }
}
