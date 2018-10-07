package com.salah.realmtest.activities.offer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.salah.realmtest.R;
import com.salah.realmtest.adapters.ListOffersAdapter;
import com.salah.realmtest.models.Offer;
import com.salah.realmtest.services.RealmService;

import io.realm.Realm;
import io.realm.RealmResults;

public class OffersActivity extends AppCompatActivity {

    private ListView lv_offers ;
    private RealmService realmService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
        realmService = new RealmService(Realm.getDefaultInstance());

        lv_offers = findViewById(R.id.lv_offers);

        lv_offers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Offer offer = (Offer) lv_offers.getItemAtPosition(position);
                OfferSubscriptionsListActivity.offer = offer ;
                Intent intent = new Intent(OffersActivity.this,OfferSubscriptionsListActivity.class);
                startActivity(intent);
            }
        });
        showdata();
    }

    private void showdata() {

        RealmResults<Offer> offers = realmService.getAllOffers();
        if (offers.isEmpty()) return;
        ListOffersAdapter adapter = new ListOffersAdapter(this,offers);
        lv_offers.setAdapter(adapter);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        realmService.closeRealm();
    }

    public void goToAddOffer(View view) {
        Intent intent = new Intent(OffersActivity.this,AddOfferActivity.class);
        startActivity(intent);
    }

    public void searchByQrCode(View view) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        showdata();
    }

}
