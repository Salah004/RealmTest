package com.salah.realmtest.activities.subsciption;

import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.philliphsu.bottomsheetpickers.BottomSheetPickerDialog;
import com.philliphsu.bottomsheetpickers.date.DatePickerDialog;
import com.philliphsu.bottomsheetpickers.time.BottomSheetTimePickerDialog;
import com.salah.realmtest.R;
import com.salah.realmtest.activities.MainActivity;
import com.salah.realmtest.adapters.ListOffersAdapter;
import com.salah.realmtest.adapters.SimpleListOffersAdapter;
import com.salah.realmtest.models.Athlete;
import com.salah.realmtest.models.Debt;
import com.salah.realmtest.models.Offer;
import com.salah.realmtest.models.Subscription;
import com.salah.realmtest.services.RealmService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class AddSubscriptionActivity extends AppCompatActivity implements
        BottomSheetTimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{

    private TextView tv_start_date;
    public static Athlete athlete;
    private EditText et_duration, et_paid, et_return;
    private Spinner sp_offers;
    private TextView tv_to_paid, tv_to_return , tv_debt;
    //private TextView tv_user_name , tv_athlete;

    double paid = 0 , _return = 0 , toPaid = 0 , toReturn = 0 , debt = 0 ;
    int duration = 0 ;

    RealmService realmService;

    private Date startDate = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subscription);
        realmService = new RealmService(Realm.getDefaultInstance());

        et_duration = findViewById(R.id.et_duration);
        et_paid = findViewById(R.id.et_paid);
        et_return = findViewById(R.id.et_return);
        tv_to_paid = findViewById(R.id.tv_to_paid);
        tv_to_return = findViewById(R.id.tv_to_return);
        tv_debt = findViewById(R.id.tv_debt);

       // tv_user_name = findViewById(R.id.tv_user_name);
       //tv_athlete = findViewById(R.id.tv_athlete);
        //tv_user_name.setText("Manager "+ MainActivity.manager.getFirstName()+" "+MainActivity.manager.getLastName());
        //tv_athlete.setText("Athlete "+athlete.getFirstName()+" "+athlete.getLastName());

        sp_offers = findViewById(R.id.sp_offer);
        final RealmResults<Offer> offers = realmService.getAllOffers();
        SimpleListOffersAdapter offersAdapter = new SimpleListOffersAdapter(this,offers);
        sp_offers.setAdapter(offersAdapter);



        tv_start_date = findViewById(R.id.tv_start_date);
        SimpleDateFormat dateFormatter = new SimpleDateFormat(
                "dd/MM/yyyy");
        tv_start_date.setText(dateFormatter.format(startDate));



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
        areChanges();

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
            tv_to_paid.setText("toPaid :"+toPaid+" DZD");
            if(toReturn>0) tv_to_return.setText("toReturn : "+toReturn+" DZD") ; else tv_to_return.setText("toReturn : ");
            tv_debt.setText("debt "+Math.abs(debt)+" DZD");
        }catch (Exception e){}
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
        /*
        dateDialog.setHeaderTextColorSelected(0xFFFF4081);
        dateDialog.setHeaderTextColorUnselected(0x4AFF4081);
        dateDialog.setDayOfWeekHeaderTextColorSelected(0xFFFF4081);
        dateDialog.setDayOfWeekHeaderTextColorUnselected(0x4AFF4081);


        dialog.setAccentColor(0xFFFF4081);
        dialog.setBackgroundColor(true? 0xFF90CAF9 : 0xFF2196F3);
        dialog.setHeaderColor(true? 0xFF90CAF9 : 0xFF2196F3);
        */
        dialog.setHeaderTextDark(true);

        dialog.setThemeDark(themeDark);


        return dialog;
    }

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        Calendar cal = new java.util.GregorianCalendar();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        startDate = cal.getTime();
        tv_start_date.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
    }

    @Override
    public void onTimeSet(ViewGroup viewGroup, int hourOfDay, int minute) {
    }

    public void changStartDate(View view) {
        DialogFragment dialog = createDialog();
        dialog.show(getSupportFragmentManager(), "TAG");
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
            Date preview = datePriview(offer,duration);
            int size = athlete.getSubscriptions().where().greaterThan("startDate",preview).findAll().size();
            //if(size==0)
            Subscription subscription = realmService.addSubscription(athlete,offer,startDate,duration,debt,MainActivity.manager);
            if (subscription!=null){
                view.setEnabled(false);
                tv_debt.setTextColor(Color.BLUE);


                SimpleDateFormat dateFormatter = new SimpleDateFormat(
                        "dd/MM/yyyy");
                tv_debt.setText(dateFormatter.format(expirationDate(subscription)));
            }

        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private Date datePriview(Offer offer, int duration) {
        return new Date();
    }

    private Date expirationDate(Subscription subscription){
        Date startDate = subscription.getStartDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        Date endDate ;
        int duration = subscription.getDuration() * subscription.getOffer().getDuration() ;
        int unit = subscription.getOffer().getDurationUnit();
        switch (unit){
            case 0 : //day
                calendar.add(Calendar.DAY_OF_MONTH,duration);
                break;
            case 1 : //week
                calendar.add(Calendar.WEEK_OF_MONTH,duration);
                break;
            case 2 : //month
                calendar.add(Calendar.MONTH,duration);
                break;
            case 3 : //year
                calendar.add(Calendar.YEAR,duration);
                break;
            default:break;
        }
        endDate = calendar.getTime();
        return endDate;
    }

}
