package com.salah.realmtest.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.salah.realmtest.R;
import com.salah.realmtest.adapters.ListOffersAdapter;
import com.salah.realmtest.models.Offer;

import io.realm.Realm;
import io.realm.RealmResults;

public class OfferActivity extends AppCompatActivity {

    private EditText et_titre , et_description , et_duration , et_price;
    private Spinner sp_unit;
    private Button btn_save ;
    private ListView lv_offers ;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        realm = Realm.getDefaultInstance();
        et_titre = findViewById(R.id.et_titre);
        et_description = findViewById(R.id.et_description);
        et_duration = findViewById(R.id.et_duration);
        et_price = findViewById(R.id.et_price);
        sp_unit = findViewById(R.id.sp_unit);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.units_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_unit.setAdapter(adapter);
        btn_save = findViewById(R.id.btn_save);
        lv_offers = findViewById(R.id.lv_offers);
        showdata();
    }

    private void showdata() {

        RealmResults<Offer> offers = realm.where(Offer.class).findAll();
        if (offers.isEmpty()) return;
        ListOffersAdapter adapter = new ListOffersAdapter(this,offers);
        lv_offers.setAdapter(adapter);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
