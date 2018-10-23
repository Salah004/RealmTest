package com.salah.realmtest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.salah.realmtest.Informations;
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


        tv_date_start.setText(Informations.dateToString(subscription.getStartDate()));
        tv_date_end.setText(Informations.dateToString(subscription.getEndDate()));



        return view;

    }


}
