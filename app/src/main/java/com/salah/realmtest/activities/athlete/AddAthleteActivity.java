package com.salah.realmtest.activities.athlete;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.salah.realmtest.R;
import com.salah.realmtest.activities.MainActivity;
import com.salah.realmtest.dialogs.QrCodeViewDialog;
import com.salah.realmtest.models.Athlete;
import com.salah.realmtest.services.RealmService;

import io.realm.Realm;

public class AddAthleteActivity extends AppCompatActivity {

    private EditText et_first_name, et_last_name, et_phone;
    private Spinner sp_gender;
    Button btn_save ;
    private RealmService realmService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_athlete);
        realmService = new RealmService(Realm.getDefaultInstance());

        et_first_name = findViewById(R.id.et_first_name);
        et_last_name = findViewById(R.id.et_user_name);
        et_phone = findViewById(R.id.et_phone);
        sp_gender = findViewById(R.id.sp_gender);
        btn_save = findViewById(R.id.btn_save);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.genders_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_gender.setAdapter(adapter);
    }

    public void addAthlete(View view) {
        String firstName = et_first_name.getText().toString();
        String lastName = et_last_name.getText().toString();
        int gender = sp_gender.getSelectedItemPosition();
        String phone = et_phone.getText().toString();
        String picturePath = "";
        try {
            Athlete athlete= realmService.addAthlete(firstName,lastName,phone,gender,picturePath, MainActivity.manager);
            btn_save.setEnabled(false);
            QrCodeViewDialog dialog = new QrCodeViewDialog(this,athlete.getId()){};
            dialog.show();
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realmService.closeRealm();
    }
}
