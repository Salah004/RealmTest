package com.salah.realmtest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.salah.realmtest.R;
import com.salah.realmtest.models.Athlete;
import com.salah.realmtest.models.Manager;

import io.realm.RealmResults;

public class ListManagersAdapter extends BaseAdapter {

    private Context context;
    private RealmResults<Manager> managers;

    public ListManagersAdapter(Context context, RealmResults<Manager> managers) {
        this.context = context;
        this.managers = managers;
    }

    @Override
    public int getCount() {
        return managers.size();
    }

    @Override
    public Object getItem(int position) {
        return managers.get(position);
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

        Manager manager = managers.get(position);
        tv.setText(manager.getFirstName()+" "+manager.getLastName()+" "+manager.getPhone());

        return view;

    }
}
