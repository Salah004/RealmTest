package com.salah.realmtest.activities.manager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.salah.realmtest.R;
import com.salah.realmtest.activities.MainActivity;
import com.salah.realmtest.models.Manager;
import com.salah.realmtest.services.RealmService;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import io.realm.Realm;

public class NewManagerActivity extends AppCompatActivity implements IPickResult {

    public static boolean manager_owner;
    private EditText et_user_name, et_password, et_first_name, et_last_name, et_phone;
    private Spinner sp_gender, sp_role;
    private RealmService realmService;
    private CircleImageView iv_manager;
    private String pick = null;
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

        iv_manager = findViewById(R.id.iv_manager);

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
        try {
            if (check()){
                String firstName = et_first_name.getText().toString();
                String lastName = et_last_name.getText().toString();
                String userName = et_user_name.getText().toString();
                String password = et_password.getText().toString();
                int gender = sp_gender.getSelectedItemPosition();
                int role = sp_role.getSelectedItemPosition();
                String phone = et_phone.getText().toString();
                if (manager_owner){
                    Manager manager = realmService.AddOwner(userName,password,firstName,lastName,phone,gender,role,pick);
                    if (manager!=null){
                        onBackPressed();
                        Toasty.success(this,manager.getFirstName(),Toast.LENGTH_LONG).show();
                    }
                }else {
                    Manager manager = realmService.AddManager(userName,password,firstName,lastName,phone,gender,role,pick, MainActivity.manager);
                    if (manager!=null){
                        onBackPressed();
                        Toasty.success(this,manager.getFirstName(),Toast.LENGTH_LONG).show();
                    }
                }
            }else {

            }
        }catch (Exception e){
            Toasty.error(this, e.getMessage(), Toast.LENGTH_SHORT, true).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realmService.closeRealm();
    }

    @Override
    public void onPickResult(PickResult r) {
        if (r.getError() == null) {
            iv_manager.setImageBitmap(r.getBitmap());
            pick = r.getPath();
        } else {
            Toasty.error(this, r.getError().getMessage(), Toast.LENGTH_SHORT, true).show();
        }
    }

    public void takePick(View view) {
        PickSetup setup = new PickSetup();
        PickImageDialog.build(setup).show(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private boolean check() {
        if (et_user_name.getText().toString().length()==0){
            Toasty.error(NewManagerActivity.this,getString(R.string.user_name_empty),Toast.LENGTH_LONG).show();
            return false;
        }
        if (et_password.getText().toString().length()==0){
            Toasty.error(NewManagerActivity.this,getString(R.string.password_empty),Toast.LENGTH_LONG).show();
            return false;
        }

        if (et_first_name.getText().toString().length()==0){
            Toasty.error(NewManagerActivity.this,getString(R.string.first_name_empty),Toast.LENGTH_LONG).show();
            return false;
        }
        if (et_last_name.getText().toString().length()==0){
            Toasty.error(NewManagerActivity.this,getString(R.string.last_name_empty),Toast.LENGTH_LONG).show();
            return false;
        }
        if (et_phone.getText().toString().length()==0){
            Toasty.error(NewManagerActivity.this,getString(R.string.phone_empty),Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
