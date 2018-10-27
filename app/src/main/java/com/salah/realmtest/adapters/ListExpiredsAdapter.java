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
import android.widget.Toast;

import com.salah.realmtest.Informations;
import com.salah.realmtest.R;
import com.salah.realmtest.models.Athlete;
import com.salah.realmtest.models.Subscription;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import io.realm.Case;
import io.realm.RealmResults;

public class ListExpiredsAdapter extends BaseAdapter {

    private Context context;
    private RealmResults<Subscription> subscriptions;
    private RealmResults<Subscription> originalSubscriptions;

    public ListExpiredsAdapter(Context context, RealmResults<Subscription> subscriptions) {
        this.context = context;
        this.subscriptions = subscriptions;
        this.originalSubscriptions = subscriptions;
    }

    @Override
    public int getCount() {
        return subscriptions.size();
    }

    @Override
    public Object getItem(int position) {
        return subscriptions.get(position);
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

        iv.setVisibility(View.GONE);

        CircleImageView iv_athlete = view.findViewById(R.id.iv_athlete);

        Subscription subscription = subscriptions.get(position);
        Athlete athlete = subscription.getAthlete();

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
            Date endDate = subscription.getEndDate();
            tv_end_date.setText(Informations.dateToString(endDate));
        }catch (Exception e){
            tv_end_date.setVisibility(View.GONE);
        }


        return view;

    }

    public void filter(String text, Date date) {

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        Date date1 = cal1.getTime();

        cal1.set(Calendar.HOUR_OF_DAY, 23);
        cal1.set(Calendar.MINUTE, 59);
        cal1.set(Calendar.SECOND, 59);
        Date date2 = cal1.getTime();

        if (text.length()==0){
            subscriptions = originalSubscriptions
                    .where()
                    .between("endDate",date1,date2)
                    .findAll();
        }else {
            subscriptions = originalSubscriptions
                    .where()
                    .between("endDate",date1,date2)
                    .beginGroup()
                        .contains("athlete.firstName",text, Case.INSENSITIVE)
                        .or()
                        .contains("athlete.lastName",text,Case.INSENSITIVE)
                        .or()
                        .contains("offer.title",text,Case.INSENSITIVE)
                    .endGroup()
                    .findAll();
        }
        notifyDataSetChanged();
    }

}
