package com.salah.realmtest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.salah.realmtest.R;
import com.salah.realmtest.models.Abonnement;
import com.salah.realmtest.models.Offer;

import io.realm.RealmResults;

public class ListAbonnementsAdapter extends BaseAdapter {

    private Context context;
    private RealmResults<Abonnement> abonnements;

    public ListAbonnementsAdapter(Context context, RealmResults<Abonnement> abonnements) {
        this.context = context;
        this.abonnements = abonnements;
    }

    @Override
    public int getCount() {
        return abonnements.size();
    }

    @Override
    public Object getItem(int position) {
        return abonnements.get(position);
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

        Abonnement abonnement = abonnements.get(position);
        tv.setText(abonnement.getPerson().getNom()+" "+abonnement.getOffer().getTitre());


        return view;

    }
}
