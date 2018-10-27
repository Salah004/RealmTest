package com.salah.realmtest.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.salah.realmtest.R;
import com.salah.realmtest.activities.athlete.AthletesActivity;
import com.salah.realmtest.activities.athlete.DetectAthleteActivity;
import com.salah.realmtest.activities.manager.ManagersActivity;
import com.salah.realmtest.activities.offer.OffersActivity;
import com.salah.realmtest.activities.subsciption.ExpiredActivity;
import com.salah.realmtest.models.Manager;
import com.salah.realmtest.services.RealmService;

import es.dmoral.toasty.Toasty;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    public static Manager manager;
    private RealmService realmService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realmService = new RealmService(Realm.getDefaultInstance());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    public void goToAthletes(View view) {
        Intent intent = new Intent(MainActivity.this,AthletesActivity.class);
        startActivity(intent);
    }

    public void goToOffers(View view) {
        Intent intent = new Intent(MainActivity.this,OffersActivity.class);
        startActivity(intent);
    }

    public void goToManagers(View view) {
        if(manager.getRole()==0){
            Intent intent = new Intent(MainActivity.this,ManagersActivity.class);
            startActivity(intent);
        }else {
            Toasty.error(MainActivity.this,"dont have permission",Toast.LENGTH_LONG).show();
        }
    }

    public void goToExpireds(View view) {
        Intent intent = new Intent(MainActivity.this,ExpiredActivity.class);
        startActivity(intent);
    }

    public void gotToAthlete(View view) {
        Intent intent = new Intent(MainActivity.this,DetectAthleteActivity.class);
        startActivity(intent);
    }

    public void addSession(View view){

    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh(){
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.it_account :
                //Intent accountIntent = new Intent(AthletesActivity.this,SubscriptionsActivity.class);
                //startActivity(accountIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realmService.closeRealm();
    }

}
