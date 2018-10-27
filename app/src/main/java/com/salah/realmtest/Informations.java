package com.salah.realmtest;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;

import com.philliphsu.bottomsheetpickers.BottomSheetPickerDialog;
import com.philliphsu.bottomsheetpickers.date.DatePickerDialog;
import com.salah.realmtest.models.Subscription;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Informations {
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat(
            "dd/MM/yyyy");
    static public String dateToString(Date date) {
        return dateFormatter.format(date);
    }

    public static Date expirationDate(Subscription subscription){
        Date startDate = subscription.getStartDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        Date endDate ;
        int duration = subscription.getDuration() * subscription.getOffer().getDuration() ;
        int unit = subscription.getOffer().getDurationUnit();
        switch (unit){
            case 0 : //day
                calendar.add(Calendar.DAY_OF_MONTH,duration);
                break;
            case 1 : //week
                calendar.add(Calendar.WEEK_OF_MONTH,duration);
                break;
            case 2 : //month
                calendar.add(Calendar.MONTH,duration);
                break;
            case 3 : //year
                calendar.add(Calendar.YEAR,duration);
                break;
            default:break;
        }
        endDate = calendar.getTime();
        return endDate;
    }

    public static DialogFragment createDialog(Context context) {
        BottomSheetPickerDialog dialog = null;
        Calendar now = Calendar.getInstance();
        dialog = DatePickerDialog.newInstance(
                (DatePickerDialog.OnDateSetListener) context,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));

        Boolean themeDark = true;
        DatePickerDialog dateDialog = (DatePickerDialog) dialog;
        dateDialog.setMinDate(now);
        Calendar max = Calendar.getInstance();
        max.add(Calendar.YEAR, 10);
        Calendar min = Calendar.getInstance();
        min.add(Calendar.YEAR,-10);
        dateDialog.setMaxDate(max);
        dateDialog.setMinDate(min);
        dateDialog.setYearRange(1970, 2032);

        /*
        dateDialog.setHeaderTextColorSelected(0xFFFF4081);
        dateDialog.setHeaderTextColorUnselected(0x4AFF4081);
        dateDialog.setDayOfWeekHeaderTextColorSelected(0xFFFF4081);
        dateDialog.setDayOfWeekHeaderTextColorUnselected(0x4AFF4081);


        dialog.setAccentColor(0xFFFF4081);
        dialog.setBackgroundColor(true? 0xFF90CAF9 : 0xFF2196F3);
        dialog.setHeaderColor(true? 0xFF90CAF9 : 0xFF2196F3);
        */
        dialog.setHeaderTextDark(true);

        dialog.setThemeDark(themeDark);


        return dialog;
    }

}
