package com.salah.realmtest.activities.athlete;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.salah.realmtest.R;
import com.salah.realmtest.activities.MainActivity;
import com.salah.realmtest.activities.debt.DebtsActivity;
import com.salah.realmtest.activities.subsciption.SubscriptionsActivity;
import com.salah.realmtest.adapters.ListAthletesAdapter;
import com.salah.realmtest.models.Athlete;
import com.salah.realmtest.services.RealmService;

import es.dmoral.toasty.Toasty;
import io.realm.Realm;
import io.realm.RealmResults;

public class AthletesActivity extends AppCompatActivity {

    private ListView lv_athletes;
    private ImageView iv_qr_code;
    private EditText et_search;
    private RealmService realmService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athletes);
        realmService = new RealmService(Realm.getDefaultInstance());

        lv_athletes = findViewById(R.id.lv_athletes);
        et_search = findViewById(R.id.et_search);
        iv_qr_code = findViewById(R.id.iv_qr_code);

        lv_athletes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Athlete athlete = (Athlete) lv_athletes.getItemAtPosition(position);
                AthleteActivity.athlete = athlete ;
                Intent intent = new Intent(AthletesActivity.this,AthleteActivity.class);
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

    public void goToAddAthlete(View view) {
        Intent intent = new Intent(AthletesActivity.this,AddAthleteActivity.class);
        startActivity(intent);
    }

    public void searchByQrCode(View view) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        showdata();
    }

    public void showMenu(View view){
        final Athlete mathlete = (Athlete) view.getTag();
        PopupMenu popup = new PopupMenu(AthletesActivity.this, view);
        //Inflating the Popup using xml file
        popup.getMenuInflater()
                .inflate(R.menu.menu, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.it_debts){
                    DebtsActivity.athlete = mathlete ;
                    Intent intent = new Intent(AthletesActivity.this,DebtsActivity.class);
                    startActivity(intent);
                }
                if (item.getItemId() == R.id.it_sub){
                    SubscriptionsActivity.athlete = mathlete ;
                    Intent intent = new Intent(AthletesActivity.this,SubscriptionsActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });

        popup.show();
    }
}