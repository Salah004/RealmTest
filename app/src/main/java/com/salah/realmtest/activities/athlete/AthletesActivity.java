package com.salah.realmtest.activities.athlete;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.salah.realmtest.R;
import com.salah.realmtest.activities.MainActivity;
import com.salah.realmtest.activities.subsciption.SubscriptionsActivity;
import com.salah.realmtest.adapters.ListAthletesAdapter;
import com.salah.realmtest.models.Athlete;
import com.salah.realmtest.services.RealmService;

import java.util.Calendar;
import java.util.Date;

import es.dmoral.toasty.Toasty;
import io.realm.Realm;
import io.realm.RealmResults;

public class AthletesActivity extends AppCompatActivity {

    private ListView lv_athletes;
    private EditText et_search;
    private RealmService realmService;
    private ListAthletesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athletes);
        realmService = new RealmService(Realm.getDefaultInstance());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        lv_athletes = findViewById(R.id.lv_athletes);
        et_search = findViewById(R.id.et_search);

        lv_athletes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Athlete athlete = (Athlete) lv_athletes.getItemAtPosition(position);
                AthleteActivity.athlete = athlete ;
                Intent intent = new Intent(AthletesActivity.this,AthleteActivity.class);
                startActivity(intent);
            }
        });

        setup();
    }

    private void setup() {

        RealmResults<Athlete> athletes = realmService.getAllAthletes();
        //if (athletes.isEmpty()) //return;
        adapter = new ListAthletesAdapter(this,athletes);
        lv_athletes.setAdapter(adapter);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        realmService.closeRealm();
    }

    public void goToAddAthlete(View view) {
        Intent intent = new Intent(AthletesActivity.this,AddAthleteActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setup();
    }

    public void showActionMenu(View view){
        final Athlete mAthlete = (Athlete) view.getTag();
        PopupMenu popup = new PopupMenu(AthletesActivity.this, view);
        popup.getMenuInflater()
                .inflate(R.menu.menu_action, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.it_delete){
                    double debt = (double)mAthlete.getDebts().where().sum("amount");
                    Calendar cal1 = Calendar.getInstance();
                    cal1.setTime(new Date());
                    cal1.set(Calendar.HOUR_OF_DAY, 0);
                    cal1.set(Calendar.MINUTE, 0);
                    cal1.set(Calendar.SECOND, 0);
                    Date date = cal1.getTime();
                    int nbSub = mAthlete.getSubscriptions().where().greaterThan("endDate",date).findAll().size();
                    if (debt==0 && nbSub==0){
                        boolean del = realmService.deleteAthlete(mAthlete);
                        if (del){
                            setup();
                            Toasty.success(AthletesActivity.this,"deleted",Toast.LENGTH_LONG).show();
                        }else {
                            setup();
                            Toasty.error(AthletesActivity.this,"failed",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toasty.error(AthletesActivity.this,"have debt or sub not expired",Toast.LENGTH_LONG).show();
                    }
                }
                if (item.getItemId() == R.id.it_edit){

                }
                return true;
            }
        });

        popup.show();
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
                //Intent accountIntent = new Intent(AthletesActivity.this,ManagerActivity.class);
                //startActivity(accountIntent);
                return true;
            default:
            return super.onOptionsItemSelected(item);
        }
    }
}