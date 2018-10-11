package com.salah.realmtest.activities;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.philliphsu.bottomsheetpickers.BottomSheetPickerDialog;
import com.philliphsu.bottomsheetpickers.date.DatePickerDialog;
import com.philliphsu.bottomsheetpickers.time.BottomSheetTimePickerDialog;
import com.salah.realmtest.R;

import java.util.Calendar;

public class TestActivity extends AppCompatActivity implements
        BottomSheetTimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{

    private Button btn;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = createDialog();
                dialog.show(getSupportFragmentManager(), TAG);
            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        Calendar cal = new java.util.GregorianCalendar();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        btn.setText("Date set: " + DateFormat.getDateFormat(this).format(cal.getTime()));
    }

    @Override
    public void onTimeSet(ViewGroup viewGroup, int hourOfDay, int minute) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private DialogFragment createDialog() {
        BottomSheetPickerDialog dialog = null;
        Calendar now = Calendar.getInstance();
        dialog = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));
        Boolean themeDark = true;
        DatePickerDialog dateDialog = (DatePickerDialog) dialog;
        dateDialog.setMinDate(now);
        Calendar max = Calendar.getInstance();
        max.add(Calendar.YEAR, 10);
        dateDialog.setMaxDate(max);
        dateDialog.setYearRange(1970, 2032);
            dateDialog.setHeaderTextColorSelected(0xFFFF4081);
            dateDialog.setHeaderTextColorUnselected(0x4AFF4081);
            dateDialog.setDayOfWeekHeaderTextColorSelected(0xFFFF4081);
            dateDialog.setDayOfWeekHeaderTextColorUnselected(0x4AFF4081);


        dialog.setAccentColor(0xFFFF4081);
        dialog.setBackgroundColor(true? 0xFF90CAF9 : 0xFF2196F3);
        dialog.setHeaderColor(true? 0xFF90CAF9 : 0xFF2196F3);
        dialog.setHeaderTextDark(true);

        dialog.setThemeDark(themeDark);


        return dialog;
    }
}
