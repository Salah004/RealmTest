package com.salah.realmtest.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.salah.realmtest.R;
import com.salah.realmtest.models.Debt;

import java.text.SimpleDateFormat;

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

        View view = inflater.inflate(R.layout.item_list_debts, null);

        TextView tv_amount = view.findViewById(R.id.tv_amount);
        TextView tv_date = view.findViewById(R.id.tv_date);
        ImageView iv_operation = view.findViewById(R.id.iv_operation);

        Debt debt = debts.get(position);

        if (debt.getAmount()>0){
            tv_amount.setTextColor(Color.RED);
            iv_operation.setImageResource(R.drawable.ic_arrow_upward_black_24dp);
        }
        else{
            tv_amount.setTextColor(Color.GREEN);
            iv_operation.setImageResource(R.drawable.ic_arrow_downward_black_24dp);
        }
        tv_amount.setText(Math.abs(debt.getAmount())+"");
        SimpleDateFormat dateFormatter = new SimpleDateFormat(
                "dd/MM/yyyy");
        tv_date.setText(dateFormatter.format(debt.getDate()));

        return view;

    }
}
