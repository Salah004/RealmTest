package com.salah.realmtest.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.salah.realmtest.R;
import com.salah.realmtest.services.RealmService;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import es.dmoral.toasty.Toasty;
import io.realm.Realm;

public abstract class AddAthleteDialog extends Dialog implements IPickResult {

    private EditText et_first_name, et_last_name, et_phone;
    private Spinner sp_gender;
    private Button btn_save ;
    private ImageView iv_athlete;
    private String pick = null;
    private Context context;

    public AddAthleteDialog(Context context) {
        super(context , android.R.style.DeviceDefault_Light_ButtonBar );
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.dialog_add_athlete);

        et_first_name = findViewById(R.id.et_first_name);
        et_last_name = findViewById(R.id.et_user_name);
        et_phone = findViewById(R.id.et_phone);
        sp_gender = findViewById(R.id.sp_gender);
        btn_save = findViewById(R.id.btn_save);
        iv_athlete = findViewById(R.id.iv_athlete);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAthlete( et_first_name , et_last_name , sp_gender , et_phone , pick );
            }
        });

        iv_athlete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePick();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.genders_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_gender.setAdapter(adapter);
    }
    public abstract void addAthlete( EditText et_first_name , EditText et_last_name , Spinner sp_gender , EditText et_phone , String pick);


    @Override
    public void onPickResult(PickResult r) {
        if (r.getError() == null) {
            iv_athlete.setImageBitmap(r.getBitmap());
            pick = r.getPath();
        } else {
            Toasty.error(context, r.getError().getMessage(), Toast.LENGTH_SHORT, true).show();
        }
    }

    public void takePick() {
        PickSetup setup = new PickSetup();
        PickImageDialog.build(setup).show(getContext());
    }
}
