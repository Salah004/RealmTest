package com.salah.realmtest.activities.athlete;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.salah.realmtest.R;
import com.salah.realmtest.activities.MainActivity;
import com.salah.realmtest.dialogs.QrCodeViewDialog;
import com.salah.realmtest.models.Athlete;
import com.salah.realmtest.services.RealmService;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import io.realm.Realm;

public class AddAthleteActivity extends AppCompatActivity  implements IPickResult {

    private EditText et_first_name, et_last_name, et_phone;
    private Spinner sp_gender;
    private Button btn_save ;
    private CircleImageView iv_athlete;
    private String pick = null;
    private RealmService realmService;

    static public boolean update = false ;
    static public Athlete athlete;

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
        iv_athlete = findViewById(R.id.iv_athlete);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.genders_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_gender.setAdapter(adapter);
    }

    public void addAthlete(View view) {
        try {
            String firstName = et_first_name.getText().toString();
            String lastName = et_last_name.getText().toString();
            int gender = sp_gender.getSelectedItemPosition();
            String phone = et_phone.getText().toString();
            Athlete mAthlete = null ;
            if (update){
                mAthlete = realmService.updateAthlete(firstName,lastName,phone,gender, pick ,athlete);
            }else {
                mAthlete = realmService.addAthlete(firstName,lastName,phone,gender, pick, MainActivity.manager);
            }

            Toasty.success(this,mAthlete.getFirstName(),Toast.LENGTH_LONG).show();
            onBackPressed();
            //btn_save.setEnabled(false);
            //QrCodeViewDialog dialog = new QrCodeViewDialog(this,athlete.getId()){};
            //dialog.show();
        }catch (Exception e){
            Toasty.error(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPickResult(PickResult r) {
        if (r.getError() == null) {
            iv_athlete.setImageBitmap(r.getBitmap());
            pick = r.getPath();
        } else {
            Toasty.error(this, r.getError().getMessage(), Toast.LENGTH_SHORT, true).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realmService.closeRealm();
    }

    public void takePick(View view) {
        PickSetup setup = new PickSetup();
        PickImageDialog.build(setup).show(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
