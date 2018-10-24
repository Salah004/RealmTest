package com.salah.realmtest.activities.subsciption;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.philliphsu.bottomsheetpickers.date.DatePickerDialog;
import com.philliphsu.bottomsheetpickers.time.BottomSheetTimePickerDialog;
import com.salah.realmtest.Informations;
import com.salah.realmtest.R;
import com.salah.realmtest.activities.athlete.AthleteActivity;
import com.salah.realmtest.adapters.ListAthletesAdapter;
import com.salah.realmtest.adapters.ListExpiredsAdapter;
import com.salah.realmtest.models.Athlete;
import com.salah.realmtest.models.Subscription;
import com.salah.realmtest.services.RealmService;

import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class ExpiredActivity extends AppCompatActivity implements
        BottomSheetTimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{

    private ListView lv_athletes;
    private EditText et_search;
    private RealmService realmService;
    private ListExpiredsAdapter adapter;
    private Date date = null ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expired);
        realmService = new RealmService(Realm.getDefaultInstance());

        date = new Date();

        lv_athletes = findViewById(R.id.lv_athletes);
        et_search = findViewById(R.id.et_search);

        et_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                adapter.filter(s.toString(),date);

            }
        });

        lv_athletes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Subscription subscription = (Subscription) lv_athletes.getItemAtPosition(position);
                Athlete athlete = subscription.getAthlete();
                AthleteActivity.athlete = athlete ;
                Intent intent = new Intent(ExpiredActivity.this,AthleteActivity.class);
                startActivity(intent);
            }
        });

        showdata();
    }

    private void showdata() {
        RealmResults<Subscription> subscriptions = realmService.getAllSubscriptions();
        if (subscriptions.isEmpty()) return;
        adapter = new ListExpiredsAdapter(this,subscriptions);
        String text = et_search.getText().toString();
        adapter.filter(text,date);
        lv_athletes.setAdapter(adapter);

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

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,monthOfYear,dayOfMonth);
        date = calendar.getTime();
        String text = et_search.getText().toString();
        adapter.filter(text,date);
    }

    @Override
    public void onTimeSet(ViewGroup viewGroup, int hourOfDay, int minute) {

    }

    public void onFilterWithDate(View view) {
        DialogFragment dialog = Informations.createDialog(this);
        dialog.show(getSupportFragmentManager(), "TAG");
    }
}