package com.salah.realmtest.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.salah.realmtest.R;
import com.salah.realmtest.adapters.ListAdapter;
import com.salah.realmtest.models.Person;
import com.salah.realmtest.services.RealmService;

import io.realm.Realm;
import io.realm.RealmResults;

public class PersonsActivity extends AppCompatActivity {

    private EditText et_nom , et_prenom , et_num_tel ;
    private Button btn_save ;
    private ListView lv_persons ;
    private RealmService realmService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persons);
        realmService = new RealmService(Realm.getDefaultInstance());
        et_nom = findViewById(R.id.et_nom);

        et_prenom = findViewById(R.id.et_prenom);
        et_num_tel = findViewById(R.id.et_num_tel);
        btn_save = findViewById(R.id.btn_save);
        lv_persons = findViewById(R.id.lv_persons);
        lv_persons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Person person = (Person) lv_persons.getItemAtPosition(position);
                PersonAbonnementsListActivity.person = person ;
                Intent intent = new Intent(PersonsActivity.this,PersonAbonnementsListActivity.class);
                startActivity(intent);
            }
        });
        showdata();
    }

    private void showdata() {

        RealmResults<Person> persons = realmService.getAllPersons();
        if (persons.isEmpty()) return;
        ListAdapter adapter = new ListAdapter(this,persons);
        lv_persons.setAdapter(adapter);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        realmService.closeRealm();
    }

    public void addPerson(View view) {
        String nom = et_nom.getText().toString();
        String prenom = et_prenom.getText().toString();
        String phone = et_num_tel.getText().toString();
        realmService.addPersonAsync(nom,prenom,phone);
        showdata();
    }
}