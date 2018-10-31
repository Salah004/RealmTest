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

        View view = inflater.inflate(R.layout.item_list_offers_simple, null);

        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView tv_price = view.findViewById(R.id.tv_price);
        TextView tv_duration = view.findViewById(R.id.tv_duration);


        String[] s = context.getResources().getStringArray(R.array.units_array);
        Offer offer = offers.get(position);

        tv_title.setText(offer.getTitle());
        tv_price.setText(offer.getPrice()+" DZD");
        tv_duration.setText(offer.getDuration()+" "+s[offer.getDurationUnit()]);

        return view;

    }
}
