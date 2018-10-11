package com.salah.realmtest.activities.debt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.salah.realmtest.R;
import com.salah.realmtest.activities.MainActivity;
import com.salah.realmtest.activities.manager.LoginActivity;
import com.salah.realmtest.models.Athlete;
import com.salah.realmtest.models.Debt;
import com.salah.realmtest.services.RealmService;

import io.realm.Realm;

public class AddDebtActivity extends AppCompatActivity {

    private RealmService realmService;
    private EditText et_sum, et_description;
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
        et_sum = findViewById(R.id.et_sum);
        tv_operation = findViewById(R.id.tv_operation);
        tv_total = findViewById(R.id.tv_total);
    }

    public void goToAddDebt(View view) {
        double sum = Double.parseDouble(et_sum.getText().toString());
        if(!operation) sum = sum * -1 ;
        String description = et_description.getText().toString();
        Debt debt = realmService.addDebt(sum,description,athlete, MainActivity.manager);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        realmService.closeRealm();
    }
}
