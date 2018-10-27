package com.salah.realmtest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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

        View view = inflater.inflate(R.layout.item_list_offers, null);

        TextView tv_name = view.findViewById(R.id.tv_name);
        TextView tv_duration = view.findViewById(R.id.tv_duration);
        TextView tv_price = view.findViewById(R.id.tv_price);
        ImageView iv = view.findViewById(R.id.iv);

        String[] s = context.getResources().getStringArray(R.array.units_array);
        Offer offer = offers.get(position);
        tv_name.setText(offer.getTitle());
        tv_duration.setText(offer.getDuration()+" "+s[offer.getDurationUnit()]);
        tv_price.setText(offer.getPrice()+" DZD");


        iv.setTag(offer);

        return view;

    }
}
