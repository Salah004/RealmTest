package com.salah.realmtest;

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
}
