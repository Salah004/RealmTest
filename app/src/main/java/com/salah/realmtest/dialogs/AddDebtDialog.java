package com.salah.realmtest.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.salah.realmtest.R;

public abstract class AddDebtDialog extends Dialog {


    private EditText et_amount, et_description;
    private Button btn_add;
    //private TextView tv_operation, tv_total;

    //operation = true => debt +
    //operation = false => -

    public AddDebtDialog(Context context) {
        super(context , android.R.style.DeviceDefault_Light_ButtonBar );
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.dialog_add_debt);

        et_description = findViewById(R.id.et_description);
        et_amount = findViewById(R.id.et_amount);
        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDebt( et_amount, et_description) ;
            }
        });
        //tv_operation = findViewById(R.id.tv_operation);
        //tv_total = findViewById(R.id.tv_total);
    }

    abstract public void addDebt( EditText et_amount, EditText et_description) ;

}
