package com.salah.realmtest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.salah.realmtest.R;
import com.salah.realmtest.models.Person;

import io.realm.RealmResults;

public class ListAdapter extends BaseAdapter {

    private Context context;
    private RealmResults<Person> persons;

    public ListAdapter(Context context, RealmResults<Person> persons) {
        this.context = context;
        this.persons = persons;
    }

    @Override
    public int getCount() {
        return persons.size();
    }

    @Override
    public Object getItem(int position) {
        return persons.get(position);
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

        Person person = persons.get(position);
        tv.setText(person.getNom()+" "+person.getPrenom()+" "+person.getTelephone());


        return view;

    }
}
