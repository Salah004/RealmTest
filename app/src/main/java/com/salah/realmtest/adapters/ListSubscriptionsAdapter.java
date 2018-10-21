package com.salah.realmtest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.salah.realmtest.R;
import com.salah.realmtest.models.Subscription;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.RealmResults;

public class ListSubscriptionsAdapter extends BaseAdapter {

    private Context context;
    private RealmResults<Subscription> subscriptions;

    public ListSubscriptionsAdapter(Context context, RealmResults<Subscription> subscriptions) {
        this.context = context;
        this.subscriptions = subscriptions;
    }

    @Override
    public int getCount() {
        return subscriptions.size();
    }

    @Override
    public Object getItem(int position) {
        return subscriptions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_list_subscriptions, null);

        TextView tv_offer = view.findViewById(R.id.tv_offer);
        TextView tv_date_start = view.findViewById(R.id.tv_date_start);
        TextView tv_date_end = view.findViewById(R.id.tv_date_end);

        Subscription subscription = subscriptions.get(position);
        tv_offer.setText(subscription.getOffer().getTitle());

        SimpleDateFormat dateFormatter = new SimpleDateFormat(
                "dd/MM/yyyy");

        tv_date_start.setText(dateFormatter.format(subscription.getStartDate()));
        tv_date_end.setText(dateFormatter.format(expirationDate(subscription)));



        return view;

    }

    private Date expirationDate(Subscription subscription){
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
