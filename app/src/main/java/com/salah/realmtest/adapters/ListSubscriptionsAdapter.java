package com.salah.realmtest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.salah.realmtest.R;
import com.salah.realmtest.models.Subscription;

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

        View view = inflater.inflate(R.layout.list_item, null);

        TextView tv = view.findViewById(R.id.tv);

        Subscription subscription = subscriptions.get(position);
        tv.setText(subscription.getAthlete().getFirstName()+" "+subscription.getOffer().getTitle());


        return view;

    }
}
