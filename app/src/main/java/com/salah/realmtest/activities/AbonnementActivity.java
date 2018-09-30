package com.salah.realmtest.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.salah.realmtest.R;
import com.salah.realmtest.adapters.ListAbonnementsAdapter;
import com.salah.realmtest.models.Abonnement;

import io.realm.Realm;
import io.realm.RealmResults;

public class AbonnementActivity extends AppCompatActivity {

    private EditText et_duree , et_mt_paye , et_mt_return;
    private Spinner sp_membre , sp_offer ;
    private TextView tv_mt_apaye , tv_mt_areturn;
    private Button btn_save ;
    private ListView lv_abonnements ;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abonnement);
        realm = Realm.getDefaultInstance();
        et_duree = findViewById(R.id.et_duree);
        et_mt_paye = findViewById(R.id.et_mt_paye);
        et_mt_return = findViewById(R.id.et_mt_return);
        tv_mt_apaye = findViewById(R.id.tv_mt_apaye);
        tv_mt_areturn = findViewById(R.id.tv_mt_areturn);
        sp_membre = findViewById(R.id.sp_membre);
        sp_offer = findViewById(R.id.sp_offer);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.units_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_membre.setAdapter(adapter);
        sp_offer.setAdapter(adapter);
        btn_save = findViewById(R.id.btn_save);
        lv_abonnements = findViewById(R.id.lv_abonnements);
        showdata();
    }

    private void showdata() {

        RealmResults<Abonnement> abonnements = realm.where(Abonnement.class).findAll();
        if (abonnements.isEmpty()) return;
        ListAbonnementsAdapter adapter = new ListAbonnementsAdapter(this,abonnements);
        lv_abonnements.setAdapter(adapter);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
