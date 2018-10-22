package com.salah.realmtest.activities.athlete;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.salah.realmtest.R;
import com.salah.realmtest.models.Athlete;
import com.salah.realmtest.services.RealmService;

import io.realm.Realm;

public class AthleteActivity extends AppCompatActivity {

    private TextView tv_manager,tv_phone,tv_date_end,tv_debt,tv_name;
    public static Athlete athlete;
    private RealmService realmService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athlete);
        realmService = new RealmService(Realm.getDefaultInstance());

        tv_manager = findViewById(R.id.tv_manager);
        tv_phone = findViewById(R.id.tv_phone);
        tv_date_end = findViewById(R.id.tv_date_end);
        tv_debt = findViewById(R.id.tv_debt);
        tv_name = findViewById(R.id.tv_name);

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

    private void setup(){
        tv_manager.setText(athlete.getAddedManager().getFirstName()+" "+athlete.getAddedManager().getLastName());
        tv_phone.setText(athlete.getPhone());
        tv_date_end.setText(R.id.tv_date_end);
        tv_debt.setText(R.id.tv_debt);
        tv_name.setText(athlete.getFirstName()+" "+athlete.getAddedManager().getLastName());
    }
}