package com.salah.realmtest.activities.offer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import com.salah.realmtest.R;
import com.salah.realmtest.activities.MainActivity;
import com.salah.realmtest.activities.manager.LoginActivity;
import com.salah.realmtest.models.Manager;
import com.salah.realmtest.services.RealmService;

import io.realm.Realm;

public class AddOfferActivity extends AppCompatActivity {

    private EditText et_title, et_description, et_duration, et_price, et_nb_session;
    private Spinner sp_unit;
    private CheckBox cb_open;
    private RealmService realmService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_offer);
        realmService = new RealmService(Realm.getDefaultInstance());

        et_title = findViewById(R.id.et_title);
        et_description = findViewById(R.id.et_description);
        et_duration = findViewById(R.id.et_duration);
        et_price = findViewById(R.id.et_price);
        et_nb_session = findViewById(R.id.et_nb_session);
        cb_open = findViewById(R.id.cb_open);
        et_nb_session.setEnabled(!cb_open.isChecked());
        sp_unit = findViewById(R.id.sp_unit);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.units_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_unit.setAdapter(adapter);
        cb_open.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                et_nb_session.setEnabled(!isChecked);
            }
        });
    }

    public void addOffer(View view) {
        String title = et_title.getText().toString();
        String description = et_description.getText().toString();
        int duration = Integer.parseInt(et_duration.getText().toString());
        int unit = sp_unit.getSelectedItemPosition();
        double price = Double.parseDouble(et_price.getText().toString());
        Boolean open = cb_open.isChecked();
        int numberSessions = 0;
        if (!open){
            try {
                numberSessions = Integer.parseInt(et_nb_session.getText().toString());
            }catch (Exception ex){

            }
        }
        realmService.addOffer(title,description,duration,unit,price,open,numberSessions, MainActivity.manager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realmService.closeRealm();
    }
}
