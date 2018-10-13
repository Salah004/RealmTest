package com.salah.realmtest.activities.debt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.salah.realmtest.R;
import com.salah.realmtest.activities.MainActivity;
import com.salah.realmtest.models.Athlete;
import com.salah.realmtest.models.Debt;
import com.salah.realmtest.services.RealmService;

import io.realm.Realm;

public class AddDebtActivity extends AppCompatActivity {

    private RealmService realmService;
    private EditText et_amount, et_description;
    private TextView tv_operation, tv_total;
    public static Athlete athlete;
    public static boolean operation;
    //operation = true => debt +
    //operation = false => -
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_debt);
        realmService = new RealmService(Realm.getDefaultInstance());

        et_description = findViewById(R.id.et_description);
        et_amount = findViewById(R.id.et_amount);
        //tv_operation = findViewById(R.id.tv_operation);
        //tv_total = findViewById(R.id.tv_total);
    }

    public void addDebt(View view) {
        double amount = Double.parseDouble(et_amount.getText().toString());
        if(!operation) amount = amount * -1 ;
        String description = et_description.getText().toString();
        try {
            Debt debt = realmService.addDebt(amount,description,athlete, MainActivity.manager);
            if (debt!=null){
                view.setEnabled(false);
            }
        }catch (Exception e){

        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        realmService.closeRealm();
    }
}
