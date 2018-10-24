package com.salah.realmtest.activities.debt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.salah.realmtest.R;
import com.salah.realmtest.activities.MainActivity;
import com.salah.realmtest.adapters.ListDebtsAdapter;
import com.salah.realmtest.dialogs.AddDebtDialog;
import com.salah.realmtest.models.Athlete;
import com.salah.realmtest.models.Debt;
import com.salah.realmtest.services.RealmService;

import io.realm.Realm;
import io.realm.RealmResults;

public class DebtsActivity extends AppCompatActivity {

    public static Athlete athlete;
    private ListView lv_debts ;
    private RealmService realmService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debts);
        realmService = new RealmService(Realm.getDefaultInstance());

        lv_debts = findViewById(R.id.lv_debts);
        lv_debts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Debt debt = (Debt) lv_debts.getItemAtPosition(position);
            }
        });
        showdata();
    }

    private void showdata() {
        //RealmResults<Debt> debts = realmService.getAllDebts();
        RealmResults<Debt> debts = athlete.getDebts().where().findAll();

        try {
            double sum = (double)debts.sum("amount");
            TextView tv = findViewById(R.id.tv_total);
            tv.setText(sum+"");
        }catch (Exception e){

        }


        if (debts.isEmpty()) return;
        ListDebtsAdapter adapter = new ListDebtsAdapter(this,debts);
        lv_debts.setAdapter(adapter);
    }
    
    public void goToAddDebt(View view) {
       AddDebtDialog dialog = new AddDebtDialog(this) {
           @Override
           public void addDebt(EditText et_amount, EditText et_description) {
               double amount = Double.parseDouble(et_amount.getText().toString());
              // if(!operation) amount = amount * -1 ;
               String description = et_description.getText().toString();
               try {
                   Debt debt = realmService.addDebt(amount,description,athlete, MainActivity.manager);
                   if (debt!=null){
                       //view.setEnabled(false);
                       dismiss();
                   }
               }catch (Exception e){

               }

           }
       };
       dialog.show();
    }

    public void goToPayDebt(View view) {
        AddDebtDialog dialog = new AddDebtDialog(this) {
            @Override
            public void addDebt(EditText et_amount, EditText et_description) {
                double amount = Double.parseDouble(et_amount.getText().toString());
                amount = amount * -1 ;
                String description = et_description.getText().toString();
                try {
                    Debt debt = realmService.addDebt(amount,description,athlete, MainActivity.manager);
                    if (debt!=null){
                        //view.setEnabled(false);
                        dismiss();
                    }
                }catch (Exception e){

                }

            }
        };
        dialog.show();
    }
    public void selectDate(View view) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realmService.closeRealm();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showdata();
    }


}