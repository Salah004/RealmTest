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

import com.salah.realmtest.R;
import com.salah.realmtest.models.Athlete;
import com.salah.realmtest.models.Manager;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
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

        View view = inflater.inflate(R.layout.item_list_managers, null);

        TextView tv_name = view.findViewById(R.id.tv_name);
        CircleImageView iv_manager = view.findViewById(R.id.iv_manager);

        Manager manager = managers.get(position);
        tv_name.setText(manager.getFirstName()+" "+manager.getLastName());

        try {
            File imgFile = new File(manager.getPick());
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                iv_manager.setImageBitmap(myBitmap);
            }
        }catch (Exception e){
            Log.e("ImageERR",manager.getPick()+"\n"+e.getMessage());
        }


        return view;

    }
}
