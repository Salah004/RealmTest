package com.salah.realmtest.activities.athlete;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.salah.realmtest.Informations;
import com.salah.realmtest.R;
import com.salah.realmtest.activities.debt.DebtsActivity;
import com.salah.realmtest.activities.subsciption.SubscriptionsActivity;
import com.salah.realmtest.models.Athlete;
import com.salah.realmtest.models.Debt;
import com.salah.realmtest.services.RealmService;

import java.io.File;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;

public class AthleteActivity extends AppCompatActivity {

    private TextView tv_manager,tv_phone,tv_date_end,tv_debt,tv_name;
    CircleImageView iv_athlete;
    public static Athlete athlete;
    private RealmService realmService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athlete);
        realmService = new RealmService(Realm.getDefaultInstance());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tv_manager = findViewById(R.id.tv_manager);
        tv_phone = findViewById(R.id.tv_phone);
        tv_date_end = findViewById(R.id.tv_date_end);
        tv_debt = findViewById(R.id.tv_debt);
        tv_name = findViewById(R.id.tv_name);
        iv_athlete = findViewById(R.id.iv_athlete);

        setup();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realmService.closeRealm();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setup();
    }

    private void setup(){

        try {
            File imgFile = new File(athlete.getPick());
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                iv_athlete.setImageBitmap(myBitmap);
            }
        }catch (Exception e){
            Log.e("ImageERR",athlete.getPick()+"\n"+e.getMessage());
        }


        tv_manager.setText(athlete.getAddedManager().getFirstName()+" "+athlete.getAddedManager().getLastName());
        tv_phone.setText(athlete.getPhone());
        try{
            Date endDate = athlete.getSubscriptions().maxDate("endDate");
            tv_date_end.setText(Informations.dateToString(endDate));
        }catch (Exception ex){
            tv_date_end.setText("");
        }

        double amount = (double)athlete.getDebts().sum("amount");
        tv_debt.setText(amount+" DZD");
        tv_name.setText(athlete.getFirstName()+" "+athlete.getLastName());
    }

    public void goToDetbs(View view) {
        DebtsActivity.athlete = athlete ;
        Intent intent = new Intent(AthleteActivity.this,DebtsActivity.class);
        startActivity(intent);
    }

    public void goToSubscription(View view) {
        SubscriptionsActivity.athlete = athlete ;
        Intent intent = new Intent(AthleteActivity.this,SubscriptionsActivity.class);
        startActivity(intent);
    }

    public void call(View view) {
    }
}