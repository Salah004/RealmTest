package com.salah.realmtest.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.salah.realmtest.R;
import com.salah.realmtest.adapters.ListAthletesAdapter;
import com.salah.realmtest.models.Athlete;
import com.salah.realmtest.services.RealmService;

import io.realm.Realm;
import io.realm.RealmResults;

public class AthletesActivity extends AppCompatActivity {

    private EditText et_first_name, et_last_name, et_phone;
    private Button btn_save ;
    private ListView lv_athletes;
    private RealmService realmService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athletes);
        realmService = new RealmService(Realm.getDefaultInstance());
        et_first_name = findViewById(R.id.et_first_name);

        et_last_name = findViewById(R.id.et_last_name);
        et_phone = findViewById(R.id.et_phone);
        btn_save = findViewById(R.id.btn_save);
        lv_athletes = findViewById(R.id.lv_athletes);
        lv_athletes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Athlete athlete = (Athlete) lv_athletes.getItemAtPosition(position);
                AthleteSubscriptionsListActivity.athlete = athlete ;
                Intent intent = new Intent(AthletesActivity.this,AthleteSubscriptionsListActivity.class);
                startActivity(intent);
            }
        });
        showdata();
    }

    private void showdata() {

        RealmResults<Athlete> athletes = realmService.getAllAthletes();
        if (athletes.isEmpty()) return;
        ListAthletesAdapter adapter = new ListAthletesAdapter(this,athletes);
        lv_athletes.setAdapter(adapter);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        realmService.closeRealm();
    }

    public void addAthlete(View view) {
        String firstName = et_first_name.getText().toString();
        String lastName = et_last_name.getText().toString();
        String phone = et_phone.getText().toString();
        realmService.addAthlete(firstName,lastName,phone);
        showdata();
    }
}