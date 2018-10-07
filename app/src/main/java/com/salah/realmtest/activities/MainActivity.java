package com.salah.realmtest.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.salah.realmtest.R;
import com.salah.realmtest.activities.athlete.AthletesActivity;
import com.salah.realmtest.activities.offer.OffersActivity;
import com.salah.realmtest.activities.subsciption.SubscriptionsActivity;
import com.salah.realmtest.models.Manager;

public class MainActivity extends AppCompatActivity {

    private TextView tv_user_name;
    public static Manager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_user_name = findViewById(R.id.tv_user_name);
        tv_user_name.setText("Manager : "+manager.getFirstName()+" "+manager.getLastName());
    }

    public void goToAthletes(View view) {
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
}
