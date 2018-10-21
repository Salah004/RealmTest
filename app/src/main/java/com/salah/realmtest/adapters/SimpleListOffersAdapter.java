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

public class SimpleListOffersAdapter extends BaseAdapter{

    private Context context;
    private RealmResults<Offer> offers;

    public SimpleListOffersAdapter(Context context, RealmResults<Offer> offers) {
        this.context = context;
        this.offers = offers;
    }

    @Override
    public int getCount() {
        return offers.size();
    }

    @Override
    public Object getItem(int position) {
        return offers.get(position);
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


        String[] s = context.getResources().getStringArray(R.array.units_array);
        Offer offer = offers.get(position);

        String txt = offer.getTitle()+" ("+offer.getDuration()+" "+s[offer.getDurationUnit()]+") "+" => "+offer.getPrice()+" DZD";

        tv.setText(txt);

        return view;

    }
}
