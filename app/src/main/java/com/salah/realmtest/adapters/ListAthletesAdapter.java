package com.salah.realmtest.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.salah.realmtest.Informations;
import com.salah.realmtest.R;
import com.salah.realmtest.models.Athlete;

import java.io.File;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Case;
import io.realm.RealmResults;

public class ListAthletesAdapter extends BaseAdapter {

    private Context context;
    private RealmResults<Athlete> athletes;
    private RealmResults<Athlete> originalAthletes;

    public ListAthletesAdapter(Context context, RealmResults<Athlete> athletes) {
        this.context = context;
        this.athletes = athletes;
        this.originalAthletes = athletes;
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
        TextView tv_end_date = view.findViewById(R.id.tv_end_date);
        TextView tv_debt = view.findViewById(R.id.tv_debt);
        ImageView iv_safe = view.findViewById(R.id.iv_safe);
        ImageView iv = view.findViewById(R.id.iv);

        CircleImageView iv_athlete = view.findViewById(R.id.iv_athlete);

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

        try {
            File imgFile = new File(athlete.getPick());
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                iv_athlete.setImageBitmap(myBitmap);
            }
        }catch (Exception e){
            Log.e("ImageERR",athlete.getPick()+"\n"+e.getMessage());
        }
        try{
            Date endDate = athlete.getSubscriptions().maxDate("endDate");
            tv_end_date.setText(Informations.dateToString(endDate));
        }catch (Exception e){
            tv_end_date.setVisibility(View.GONE);
        }

        iv.setTag(athlete);

        return view;

    }

    public void filter(String f){
        if (f.length()>0){
            athletes = originalAthletes
                    .where()
                    .contains("firstName",f, Case.INSENSITIVE)
                    .or()
                    .contains("lastName",f, Case.INSENSITIVE)
                    .findAll();
        }else {
            athletes = originalAthletes;
        }
        notifyDataSetChanged();
    }
}
