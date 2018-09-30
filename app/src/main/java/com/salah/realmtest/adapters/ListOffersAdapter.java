package com.salah.realmtest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.salah.realmtest.R;
import com.salah.realmtest.models.Offer;

import io.realm.RealmResults;

public class ListOffersAdapter extends BaseAdapter {

    private Context context;
    private RealmResults<Offer> offers;

    public ListOffersAdapter(Context context, RealmResults<Offer> offers) {
        this.context = context;
        this.offers = offers;
    }

    @Override
    public int getCount() {
        return offers.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.list_item, null);

        TextView tv = view.findViewById(R.id.tv);

        Offer offer = offers.get(position);
        tv.setText(offer.getTitre()+" "+offer.getDuration()+" "+offer.getDurationUnit()+" "+offer.getPrice());


        return view;

    }
}
