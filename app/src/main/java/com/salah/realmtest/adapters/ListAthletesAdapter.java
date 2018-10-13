package com.salah.realmtest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
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

        View view = inflater.inflate(R.layout.item_list_athletes, null);

        TextView tv_name = view.findViewById(R.id.tv_name);
        TextView tv_debt = view.findViewById(R.id.tv_debt);
        ImageView iv_safe = view.findViewById(R.id.iv_safe);

        Athlete athlete = athletes.get(position);
        tv_name.setText(athlete.getFirstName()+" "+athlete.getLastName());

        try {
            double sum = (double)athlete.getDebts().sum("amount");
            if (sum!=0) tv_debt.setText(sum+" DZD");
            else {
                tv_debt.setVisibility(View.GONE);
                iv_safe.setVisibility(View.VISIBLE);
            }
        }catch (Exception e){

        }

        ImageView iv = view.findViewById(R.id.iv);
        iv.setTag(athlete);


        return view;

    }
}
