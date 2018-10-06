package com.salah.realmtest.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.salah.realmtest.R;
import com.salah.realmtest.activities.athlete.AthletesActivity;
import com.salah.realmtest.activities.offer.OffersActivity;
import com.salah.realmtest.activities.subsciption.SubscriptionsActivity;
import com.salah.realmtest.models.Manager;

public class MainActivity extends AppCompatActivity {

    public static Manager manager;

    //    private EditText et_nom , et_prenom , et_num_tel ;
//    private Button btn_save ;
//    private ListView lv ;
//    Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        realm = Realm.getDefaultInstance();
//        et_nom = findViewById(R.id.et_nom);
//        et_prenom = findViewById(R.id.et_prenom);
//        et_num_tel = findViewById(R.id.et_num_tel);
//        btn_save = findViewById(R.id.btn_save);
//        lv = findViewById(R.id.lv);
//        showData();
//        btn_save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    writeToDB(et_nom.getText().toString()
//                            ,et_prenom.getText().toString()
//                            ,et_num_tel.getText().toString());
//                    showData();
//                }catch (Exception e){
//                    Log.e("Exception",e.getMessage());
//                }
//            }
//        });
    }

    public void goToAthletes(View view) {
        Intent intent = new Intent(this,AthletesActivity.class);
        startActivity(intent);
    }

    public void goToOffers(View view) {
        Intent intent = new Intent(this,OffersActivity.class);
        startActivity(intent);
    }

    public void goToSubscription(View view) {
        Intent intent = new Intent(this,SubscriptionsActivity.class);
        startActivity(intent);
    }

//    private void writeToDB(final String nom, final String prenom, final String telephone) {
//        realm.executeTransactionAsync(new Realm.Transaction() {
//            @Override
//            public void execute(Realm bgRealm) {
//                Person person = bgRealm.createObject(Person.class , UUID.randomUUID().toString());
//                person.setNom(nom);
//                person.setPrenom(prenom);
//                person.setTelephone(telephone);
//            }
//        }, new Realm.Transaction.OnSuccess() {
//            @Override
//            public void onSuccess() {
//                // Transaction was a success.
//                Log.v("Database","Data inserted");
//            }
//        }, new Realm.Transaction.OnError() {
//            @Override
//            public void onError(Throwable error) {
//                // Transaction failed and was automatically canceled.
//                Log.e("Database",error.getMessage());
//            }
//        });
//    }
//
//    private void showData() {
//        realm.beginTransaction();
//        RealmResults<Person> persons = realm.where(Person.class).findAllAsync();
//        persons.load();
//        ListAdapter adapter = new ListAdapter(this,persons);
//        lv.setAdapter(adapter);
//        realm.commitTransaction();
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        realm.close();
//    }
}
