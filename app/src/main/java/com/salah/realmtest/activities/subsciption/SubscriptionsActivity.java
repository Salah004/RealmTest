package com.salah.realmtest.activities.subsciption;

import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.philliphsu.bottomsheetpickers.BottomSheetPickerDialog;
import com.philliphsu.bottomsheetpickers.date.DatePickerDialog;
import com.philliphsu.bottomsheetpickers.time.BottomSheetTimePickerDialog;
import com.salah.realmtest.R;
import com.salah.realmtest.activities.MainActivity;
import com.salah.realmtest.adapters.ListOffersAdapter;
import com.salah.realmtest.adapters.ListSubscriptionsAdapter;
import com.salah.realmtest.models.Athlete;
import com.salah.realmtest.models.Offer;
import com.salah.realmtest.models.Subscription;
import com.salah.realmtest.services.RealmService;

import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class SubscriptionsActivity extends AppCompatActivity implements
        BottomSheetTimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{

    public static Athlete athlete;
    private EditText et_duration, et_paid, et_return;
    private Spinner /*sp_athletes,*/ sp_offers;
    private TextView tv_to_paid, tv_to_return , tv_debt;
    private TextView tv_user_name , tv_athlete;
    private Button btn_save ;
    private Date startDate = new Date();
    private ListView lv_subscriptions;
    RealmService realmService;

    double paid = 0 , _return = 0 , toPaid = 0 , toReturn = 0 , debt = 0 ;
    int duration = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriptions);
        realmService = new RealmService(Realm.getDefaultInstance());
        et_duration = findViewById(R.id.et_duration);
        et_paid = findViewById(R.id.et_paid);
        et_return = findViewById(R.id.et_return);
        tv_to_paid = findViewById(R.id.tv_to_paid);
        tv_to_return = findViewById(R.id.tv_to_return);
        tv_debt = findViewById(R.id.tv_debt);
        tv_user_name = findViewById(R.id.tv_user_name);
        tv_athlete = findViewById(R.id.tv_athlete);
        tv_user_name.setText("Manager "+MainActivity.manager.getFirstName()+" "+MainActivity.manager.getLastName());
        tv_athlete.setText("Athlete "+athlete.getFirstName()+" "+athlete.getLastName());
        //sp_athletes = findViewById(R.id.sp_membre);
        sp_offers = findViewById(R.id.sp_offer);
        //RealmResults<Athlete> athletes = realmService.getAllAthletes();
        final RealmResults<Offer> offers = realmService.getAllOffers();
        //ListAthletesAdapter athletesAdapter = new ListAthletesAdapter(this,athletes);
        ListOffersAdapter offersAdapter = new ListOffersAdapter(this,offers);
        //sp_athletes.setAdapter(athletesAdapter);
        sp_offers.setAdapter(offersAdapter);
        //
        sp_offers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                areChanges();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        et_duration.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                areChanges();
            }
        });

        et_paid.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                areChanges();

            }
        });

        et_return.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                areChanges();

            }
        });

        btn_save = findViewById(R.id.ll);
        lv_subscriptions = findViewById(R.id.lv_subscriptions);
        showdata();
        areChanges();
    }

    private void showdata() {

        //RealmResults<Subscription> subscriptions = realmService.getAllSubscriptions();
        RealmResults<Subscription> subscriptions = athlete.getSubscriptions().where().findAll();
        if (subscriptions.isEmpty()) return;
        ListSubscriptionsAdapter adapter = new ListSubscriptionsAdapter(this,subscriptions);
        lv_subscriptions.setAdapter(adapter);

    }

    private void areChanges(){
        paid = 0 ;
        _return = 0 ;
        toPaid = 0 ;
        toReturn = 0 ;
        debt = 0 ;
        duration = 0 ;
        try{
            duration = Integer.parseInt(et_duration.getText().toString()) ;
        }catch (Exception e){}
        try{
            paid = Double.parseDouble(et_paid.getText().toString());
        }catch (Exception e){}
        try{
             _return = Double.parseDouble(et_return.getText().toString());
        }catch (Exception e){}
        try{
            Offer offer = (Offer) sp_offers.getSelectedItem();
            toPaid = offer.getPrice() * duration;
            toReturn = paid - toPaid ;
            debt = _return - toReturn ;
            tv_to_paid.setText(toPaid+" DZD");
            if(toReturn>0) tv_to_return.setText(toReturn+" DZD") ; else tv_to_return.setText("");
            if( debt>0 ) tv_debt.setTextColor(Color.RED) ;else tv_debt.setTextColor(Color.GREEN);
            tv_debt.setText(Math.abs(debt)+" DZD");
        }catch (Exception e){}
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realmService.closeRealm();
    }

    public void onSave(View view) {
        try {
            int duration = Integer.parseInt(et_duration.getText().toString()) ;
            Offer offer = (Offer) sp_offers.getSelectedItem();
            realmService.addSubscription(athlete,offer,startDate,duration,debt,MainActivity.manager);
            realmService.addDebt(debt,"subscription",athlete,MainActivity.manager);
            showdata();
        }catch (Exception e){

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showdata();
    }

    public void displayCalender(View view) {
        DialogFragment dialog = createDialog();
        dialog.show(getSupportFragmentManager(), "TAG");
    }

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        Calendar cal = new java.util.GregorianCalendar();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        startDate = cal.getTime();
        TextView tv =findViewById(R.id.tv_date_start);
        tv.setText(startDate.toString());
    }

    @Override
    public void onTimeSet(ViewGroup viewGroup, int hourOfDay, int minute) {

    }

    private DialogFragment createDialog() {
        BottomSheetPickerDialog dialog = null;
        Calendar now = Calendar.getInstance();
        dialog = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));
        Boolean themeDark = true;
        DatePickerDialog dateDialog = (DatePickerDialog) dialog;
        dateDialog.setMinDate(now);
        Calendar max = Calendar.getInstance();
        max.add(Calendar.YEAR, 10);
        dateDialog.setMaxDate(max);
        dateDialog.setYearRange(1970, 2032);
        dateDialog.setHeaderTextColorSelected(0xFFFF4081);
        dateDialog.setHeaderTextColorUnselected(0x4AFF4081);
        dateDialog.setDayOfWeekHeaderTextColorSelected(0xFFFF4081);
        dateDialog.setDayOfWeekHeaderTextColorUnselected(0x4AFF4081);


        dialog.setAccentColor(0xFFFF4081);
        dialog.setBackgroundColor(true? 0xFF90CAF9 : 0xFF2196F3);
        dialog.setHeaderColor(true? 0xFF90CAF9 : 0xFF2196F3);
        dialog.setHeaderTextDark(true);

        dialog.setThemeDark(themeDark);


        return dialog;
    }
}
