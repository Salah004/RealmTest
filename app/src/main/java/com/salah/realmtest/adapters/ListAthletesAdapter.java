package com.salah.realmtest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.salah.realmtest.R;
import com.salah.realmtest.models.Athlete;

import io.realm.RealmResults;

public class ListAthletesAdapter extends BaseAdapter {

    private Context context;
    private RealmResults<Athlete> athletes;

    public ListAthletesAdapter(Context context, RealmResults<Athlete> athletes) {
        this.context = context;
        this.athletes = athletes;
    }

    @Override
    public int getCount() {
        return athletes.size();
    }

    @Override
    public Object getItem(int position) {
        return athletes.get(position);
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

        Athlete athlete = athletes.get(position);
        tv.setText(athlete.getFirstName()+" "+athlete.getLastName()+" "+athlete.getPhone());

        Button btn = view.findViewById(R.id.btn);

        btn.setVisibility(View.VISIBLE);

        btn.setTag(athlete);


        return view;

    }
}
