package com.salah.realmtest.dialogs;

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

import es.dmoral.toasty.Toasty;
import io.realm.Realm;

public class NewManagerDialog extends AppCompatActivity implements IPickResult {

    public static boolean manager_owner;
    private EditText et_user_name, et_password, et_first_name, et_last_name, et_phone;
    private Spinner sp_gender, sp_role;
    private RealmService realmService;
    private ImageView iv_manager;
    private String pick = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_new_manager);
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
        String firstName = et_first_name.getText().toString();
        String lastName = et_last_name.getText().toString();
        String userName = et_user_name.getText().toString();
        String password = et_password.getText().toString();
        int gender = sp_gender.getSelectedItemPosition();
        int role = sp_role.getSelectedItemPosition();
        String phone = et_phone.getText().toString();
        if (manager_owner){
            try{
                Manager manager = realmService.AddOwner(userName,password,firstName,lastName,phone,gender,role,pick);
                if (manager!=null){
                    view.setEnabled(false);
                }
            }catch (Exception e){

            }

        }else {
            try{
                Manager manager = realmService.AddManager(userName,password,firstName,lastName,phone,gender,role,pick, MainActivity.manager);
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

    @Override
    public void onPickResult(PickResult r) {
        if (r.getError() == null) {
            //If you want the Uri.
            //Mandatory to refresh image from Uri.
            //getImageView().setImageURI(null);

            //Setting the real returned image.
            //getImageView().setImageURI(r.getUri());

            //If you want the Bitmap.
            //iv_add_pick.setVisibility(View.GONE);
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
}
