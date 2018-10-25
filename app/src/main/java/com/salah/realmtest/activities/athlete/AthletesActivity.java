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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import com.salah.realmtest.R;
import com.salah.realmtest.activities.MainActivity;
import com.salah.realmtest.activities.subsciption.SubscriptionsActivity;
import com.salah.realmtest.adapters.ListAthletesAdapter;
import com.salah.realmtest.dialogs.AddAthleteDialog;
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

        setup();
    }

    private void setup() {

        RealmResults<Athlete> athletes = realmService.getAllAthletes();
        if (athletes.isEmpty()) //return;
        adapter = new ListAthletesAdapter(this,athletes);
        lv_athletes.setAdapter(adapter);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        realmService.closeRealm();
    }

    public void goToAddAthlete(View view) {
        AddAthleteDialog dialog = new AddAthleteDialog(AthletesActivity.this) {
            @Override
            public void addAthlete(EditText et_first_name, EditText et_last_name, Spinner sp_gender, EditText et_phone, String pick) {
                try {
                    String firstName = et_first_name.getText().toString();
                    String lastName = et_last_name.getText().toString();
                    int gender = sp_gender.getSelectedItemPosition();
                    String phone = et_phone.getText().toString();
                    Athlete athlete= realmService.addAthlete(firstName,lastName,phone,gender, pick , MainActivity.manager);
                    if (athlete!=null){
                        setup();
                        dismiss();
                        Toasty.success(AthletesActivity.this,athlete.getFirstName(),Toast.LENGTH_LONG).show();
                    }
                    //QrCodeViewDialog dialog = new QrCodeViewDialog(this,athlete.getId()){};
                    //dialog.show();
                }catch (Exception e){
                    Toasty.error(AthletesActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        };
        dialog.show();
    }

    public void searchByQrCode(View view) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        setup();
    }

    public void showMenu(View view){
        final Athlete mathlete = (Athlete) view.getTag();
        PopupMenu popup = new PopupMenu(AthletesActivity.this, view);
        //Inflating the Popup using xml file
        popup.getMenuInflater()
                .inflate(R.menu.menu_operation, popup.getMenu());


        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.it_delete){
                    boolean del = realmService.deleteAthlete(mathlete);
                    if (del){
                        setup();
                        Toasty.success(AthletesActivity.this,"deleted",Toast.LENGTH_LONG).show();
                    }else {
                        setup();
                        Toasty.error(AthletesActivity.this,"failed",Toast.LENGTH_LONG).show();
                    }
                }
                if (item.getItemId() == R.id.it_edit){
                    SubscriptionsActivity.athlete = mathlete ;
                    Intent intent = new Intent(AthletesActivity.this,SubscriptionsActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });

        popup.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
            return super.onOptionsItemSelected(item);
        }
    }
}