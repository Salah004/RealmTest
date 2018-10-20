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
import com.salah.realmtest.activities.debt.DetectAthleteDebtActivity;
import com.salah.realmtest.activities.manager.ManagersActivity;
import com.salah.realmtest.activities.offer.OffersActivity;
import com.salah.realmtest.activities.subsciption.DetectAthleteSubscriptionActivity;
import com.salah.realmtest.activities.subsciption.SubscriptionsActivity;
import com.salah.realmtest.models.Manager;
import com.salah.realmtest.services.RealmService;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    public static Manager manager;
    private TextView tv_amount_debts, tv_nb_offers, tv_nb_current_sub, tv_nb_athletes;
    private RealmService realmService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realmService = new RealmService(Realm.getDefaultInstance());
        tv_amount_debts = findViewById(R.id.tv_amount_debts);
        tv_nb_offers = findViewById(R.id.tv_nb_offers);
        tv_nb_current_sub = findViewById(R.id.tv_nb_current_sub);
        tv_nb_athletes = findViewById(R.id.tv_nb_athletes);
        refresh();
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

    public void goToSubscription(View view) {
        Intent intent = new Intent(this,DetectAthleteSubscriptionActivity.class);
        startActivity(intent);
    }

    public void gotToDebts(View view) {
        Intent intent = new Intent(this,DetectAthleteDebtActivity.class);
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

    private void refresh(){
        int nbAthletes = realmService.getAllAthletes().size();
        double amountDebts = (Double) realmService.getAllDebts().sum("amount");
        int nbOffers = realmService.getAllOffers().size();
        int nbSubscriptions = realmService.getAllSubscriptions().size();//****

        tv_nb_athletes.setText(nbAthletes+" athletes");
        tv_amount_debts.setText(amountDebts+" DZD of debts");
        tv_nb_offers.setText(nbOffers+" offers");
        tv_nb_current_sub.setText(nbSubscriptions+" subscriptions");
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        refresh();
    }

    public void goToSessions(View view) {
    }

    public void goToManagers(View view) {
        Intent intent = new Intent(this,ManagersActivity.class);
        startActivity(intent);
    }
}
