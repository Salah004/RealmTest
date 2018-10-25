package com.salah.realmtest.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import com.salah.realmtest.R;

public abstract class AddOfferDialog extends Dialog {

    private EditText et_title, et_description, et_duration, et_price, et_nb_session;
    private Spinner sp_unit;
    private CheckBox cb_open;
    private Button btn_add;
    private Context context;

    public AddOfferDialog(Context context) {
        super(context , android.R.style.DeviceDefault_Light_ButtonBar );
        this.context = context ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.dialog_add_offer);

        et_title = findViewById(R.id.et_title);
        et_description = findViewById(R.id.et_description);
        et_duration = findViewById(R.id.et_duration);
        et_price = findViewById(R.id.et_price);
        et_nb_session = findViewById(R.id.et_nb_session);
        cb_open = findViewById(R.id.cb_open);
        et_nb_session.setEnabled(!cb_open.isChecked());
        sp_unit = findViewById(R.id.sp_unit);
        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOffer( et_title, et_description, et_duration, et_price, et_nb_session, sp_unit, cb_open);
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.units_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_unit.setAdapter(adapter);
        cb_open.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                et_nb_session.setEnabled(!isChecked);
            }
        });
    }

    public abstract void addOffer( EditText et_title,EditText et_description,EditText et_duration,EditText et_price,EditText et_nb_session,Spinner sp_unit,CheckBox cb_open);
}
