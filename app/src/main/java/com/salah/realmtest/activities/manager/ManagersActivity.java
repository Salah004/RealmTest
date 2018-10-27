package com.salah.realmtest.activities.manager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.salah.realmtest.R;
import com.salah.realmtest.adapters.ListManagersAdapter;
import com.salah.realmtest.models.Manager;
import com.salah.realmtest.services.RealmService;

import es.dmoral.toasty.Toasty;
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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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

    public void showActionMenu(View view){
        final Manager mManager = (Manager) view.getTag();
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater()
                .inflate(R.menu.menu_action, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.it_delete){

                }
                if (item.getItemId() == R.id.it_edit){

                }
                return true;
            }
        });

        popup.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showdata();
    }
}