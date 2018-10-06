package com.salah.realmtest.activities.manager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.salah.realmtest.R;
import com.salah.realmtest.adapters.ListManagersAdapter;
import com.salah.realmtest.models.Manager;
import com.salah.realmtest.services.RealmService;

import io.realm.Realm;
import io.realm.RealmResults;

public class ManagersActivity extends AppCompatActivity {

    private ListView lv_managers;
    private ImageView iv_qr_code;
    private EditText et_search;
    private RealmService realmService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managers);
        realmService = new RealmService(Realm.getDefaultInstance());

        lv_managers = findViewById(R.id.lv_managers);
        et_search = findViewById(R.id.et_search);
        iv_qr_code = findViewById(R.id.iv_qr_code);

        lv_managers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Manager manager = (Manager) lv_managers.getItemAtPosition(position);
            }
        });

        showdata();
    }

    private void showdata() {

        RealmResults<Manager> managers = realmService.getAllManagers();
        if (managers.isEmpty()) return;
        ListManagersAdapter adapter = new ListManagersAdapter(this,managers);
        lv_managers.setAdapter(adapter);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        realmService.closeRealm();
    }

    public void goToAddManager(View view) {
        NewManagerActivity.manager_owner = false;
        Intent intent = new Intent(ManagersActivity.this,NewManagerActivity.class);
        startActivity(intent);
    }

    public void searchByQrCode(View view) {
    }
}