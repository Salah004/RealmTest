package com.salah.realmtest.activities.technicalSheet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.salah.realmtest.R;

public class TechnicalSheetsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technical_sheets);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //showdata();
    }
}
