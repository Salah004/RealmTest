package com.salah.realmtest.activities.manager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.salah.realmtest.R;
import com.salah.realmtest.activities.MainActivity;
import com.salah.realmtest.models.Manager;
import com.salah.realmtest.services.RealmService;

import io.realm.Realm;

public class NewManagerActivity extends AppCompatActivity {

    public static boolean manager_owner;
    private EditText et_user_name, et_password, et_first_name, et_last_name, et_phone;
    private Spinner sp_gender, sp_role;
    private RealmService realmService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_manager);
        realmService = new RealmService(Realm.getDefaultInstance());

        et_first_name = findViewById(R.id.et_first_name);
        et_last_name = findViewById(R.id.et_last_name);
        et_phone = findViewById(R.id.et_phone);
        et_user_name = findViewById(R.id.et_user_name);
        et_password = findViewById(R.id.et_password);

        sp_gender = findViewById(R.id.sp_gender);
        sp_role = findViewById(R.id.sp_role);

        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this,
                R.array.genders_array, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_gender.setAdapter(genderAdapter);

        ArrayAdapter<CharSequence> RoleAdapter = ArrayAdapter.createFromResource(this,
                R.array.roles_array, android.R.layout.simple_spinner_item);
        RoleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_role.setAdapter(RoleAdapter);
        sp_role.setEnabled(!manager_owner);
    }

    public void addManager(View view) {
        String firstName = et_first_name.getText().toString();
        String lastName = et_last_name.getText().toString();
        String userName = et_user_name.getText().toString();
        String password = et_password.getText().toString();
        int gender = sp_gender.getSelectedItemPosition();
        int role = sp_role.getSelectedItemPosition();
        String phone = et_phone.getText().toString();
        String picturePath = "";
        if (manager_owner){
            try{
                Manager manager = realmService.AddOwner(userName,password,firstName,lastName,phone,gender,role,picturePath);
                if (manager!=null){
                    view.setEnabled(false);
                }
            }catch (Exception e){

            }

        }else {
            try{
                Manager manager = realmService.AddManager(userName,password,firstName,lastName,phone,gender,role,picturePath, MainActivity.manager);
                if (manager!=null){
                    view.setEnabled(false);
                }
            }catch (Exception e){

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realmService.closeRealm();
    }
}
