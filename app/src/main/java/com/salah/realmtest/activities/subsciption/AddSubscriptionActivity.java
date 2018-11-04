package com.salah.realmtest.activities.subsciption;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.philliphsu.bottomsheetpickers.date.DatePickerDialog;
import com.philliphsu.bottomsheetpickers.time.BottomSheetTimePickerDialog;
import com.salah.realmtest.Informations;
import com.salah.realmtest.R;
import com.salah.realmtest.activities.MainActivity;
import com.salah.realmtest.adapters.SimpleListOffersAdapter;
import com.salah.realmtest.models.Athlete;
import com.salah.realmtest.models.Offer;
import com.salah.realmtest.models.Subscription;
import com.salah.realmtest.services.RealmService;

import java.util.Calendar;
import java.util.Date;

import es.dmoral.toasty.Toasty;
import io.realm.Realm;
import io.realm.RealmResults;

public class AddSubscriptionActivity extends AppCompatActivity implements
        BottomSheetTimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{

    private TextView tv_start_date , tv_to_pay ;
    public static Athlete athlete;
    private EditText et_duration, et_paid;
    private Spinner sp_offers;

    double paid = 0 , toPay = 0 , toReturn = 0 , debt = 0 ;
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
        tv_to_pay = findViewById(R.id.tv_to_pay);
        //tv_to_paid = findViewById(R.id.tv_to_paid);
        //tv_to_return = findViewById(R.id.tv_to_return);
        //tv_debt = findViewById(R.id.tv_debt);

       // tv_user_name = findViewById(R.id.tv_user_name);
       //tv_athlete = findViewById(R.id.tv_athlete);
        //tv_user_name.setText("Manager "+ MainActivity.manager.getFirstName()+" "+MainActivity.manager.getLastName());
        //tv_athlete.setText("Athlete "+athlete.getFirstName()+" "+athlete.getLastName());

        sp_offers = findViewById(R.id.sp_offer);
        final RealmResults<Offer> offers = realmService.getAllOffers();
        SimpleListOffersAdapter offersAdapter = new SimpleListOffersAdapter(this,offers);
        sp_offers.setAdapter(offersAdapter);



        tv_start_date = findViewById(R.id.tv_start_date);

        tv_start_date.setText(Informations.dateToString(startDate));



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

        areChanges();

    }

    private void areChanges(){
        paid = 0 ;
        toPay = 0 ;
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
            Offer offer = (Offer) sp_offers.getSelectedItem();
            toPay = offer.getPrice() * duration;
            toReturn = paid - toPay ;
            debt = toPay - paid ;

            tv_to_pay.setText(toPay+" DZD");
            //tv_to_paid.setText("toPaid :"+toPaid+" DZD");
            //if(toReturn>0) tv_to_return.setText("toReturn : "+toReturn+" DZD") ; else tv_to_return.setText("toReturn : ");
            //tv_debt.setText("debt "+Math.abs(debt)+" DZD");
        }catch (Exception e){}
    }



    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        Calendar cal = new java.util.GregorianCalendar();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        startDate = cal.getTime();
        tv_start_date.setText(Informations.dateToString(startDate));
    }

    @Override
    public void onTimeSet(ViewGroup viewGroup, int hourOfDay, int minute) {
    }

    public void changStartDate(View view) {
        DialogFragment dialog = Informations.createDialog(this);
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
            Date endDatePriview = expirationDate(startDate,duration,offer);
            int size =  athlete.getSubscriptions()
                    .where()
                    .beginGroup()
                        .lessThan("startDate",startDate)
                        .greaterThan("endDate",startDate)
                    .endGroup()
                    .or()
                    .beginGroup()
                        .lessThan("startDate",endDatePriview)
                        .greaterThan("endDate",endDatePriview)
                    .endGroup()
                    .or()
                    .beginGroup()
                        .greaterThan("startDate",startDate)
                        .lessThan("endDate",endDatePriview)
                    .endGroup()
                    .findAll()
                    .size();

            if(size==0){
                Subscription subscription = realmService.addSubscription(athlete,offer,startDate,duration,debt,MainActivity.manager);
                if (subscription!=null){
                    Toasty.success(this,Informations.dateToString(subscription.getEndDate()),Toast.LENGTH_LONG).show();
                    onBackPressed();
                }
            }else {
                Toasty.error(this,"he has a subscription not finished yet",Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            Toasty.error(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private Date expirationDate(Date startDate , int subDuration , Offer offre){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        Date endDate ;
        int duration = subDuration * offre.getDuration() ;
        int unit = offre.getDurationUnit();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    
}
