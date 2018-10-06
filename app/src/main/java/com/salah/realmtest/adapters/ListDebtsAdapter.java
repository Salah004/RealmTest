package com.salah.realmtest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.salah.realmtest.R;
import com.salah.realmtest.models.Debt;
import com.salah.realmtest.models.Manager;

import io.realm.RealmResults;

public class ListDebtsAdapter extends BaseAdapter {

    private Context context;
    private RealmResults<Debt> debts;

    public ListDebtsAdapter(Context context, RealmResults<Debt> debts) {
        this.context = context;
        this.debts = debts;
    }

    @Override
    public int getCount() {
        return debts.size();
    }

    @Override
    public Object getItem(int position) {
        return debts.get(position);
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

        Debt debt = debts.get(position);
        tv.setText(debt.getDate()+" "+debt.getSum());

        return view;

    }
}
