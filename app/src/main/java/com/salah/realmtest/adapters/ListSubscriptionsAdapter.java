package com.salah.realmtest.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        ImageView iv_current = view.findViewById(R.id.iv_current);
        LinearLayout ll_item = view.findViewById(R.id.ll_item);

        Subscription subscription = subscriptions.get(position);

        Date now = new Date();
        Date startDate = subscription.getStartDate();
        Date endDate = subscription.getEndDate();

        if ( now.after(startDate) && now.before(endDate) ) {
            iv_current.setVisibility(View.VISIBLE);
            ll_item.setBackgroundResource(R.color.primary_light);
            tv_offer.setTextColor(Color.WHITE);
            tv_date_start.setTextColor(Color.WHITE);
            tv_date_end.setTextColor(Color.WHITE);
        }

        tv_offer.setText(subscription.getOffer().getTitle());
        tv_date_start.setText(Informations.dateToString(startDate));
        tv_date_end.setText(Informations.dateToString(endDate));



        return view;

    }


}
