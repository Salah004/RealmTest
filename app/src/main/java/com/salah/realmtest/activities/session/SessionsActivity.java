package com.salah.realmtest.activities.session;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.salah.realmtest.R;
import com.salah.realmtest.activities.MainActivity;
import com.salah.realmtest.adapters.ListSessionsAdapter;
import com.salah.realmtest.models.Session;
import com.salah.realmtest.models.Subscription;
import com.salah.realmtest.services.RealmService;

import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class SessionsActivity extends AppCompatActivity {

    public static Subscription subscription;
    private ListView lv_sessions;
    private RealmService realmService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions);
        realmService = new RealmService(Realm.getDefaultInstance());

        lv_sessions = findViewById(R.id.lv_sessions);
        showdata();

    }

    private void showdata() {

        //RealmResults<Session> sessions = realmService.getAllSessions();
        RealmResults<Session> sessions = subscription.getSessions().where().findAll();
        if (sessions.isEmpty()) return;
        ListSessionsAdapter adapter = new ListSessionsAdapter(this,sessions);
        lv_sessions.setAdapter(adapter);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        realmService.closeRealm();
    }
    public void addSession(View view) {

        if (checkDate()){
            if (checkNumberSession()){
                realmService.addSession(subscription, MainActivity.manager);
                showdata();
            }else {
                //number of sessions
            }
        }else {
            // end date
        }
    }

    private boolean checkNumberSession() {
        if (subscription.getOffer().isOpen()) return true;
        if (subscription.getSessions().size()<subscription.getOffer().getSessionNumber()*subscription.getDuration()) return true;
        return false;
    }

    private Boolean checkDate(){
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
        return endDate.after(new Date());
    }

    @Override
    protected void onResume() {
        super.onResume();
        showdata();
    }

}
