package com.salah.realmtest.activities.offer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import com.salah.realmtest.R;
import com.salah.realmtest.activities.MainActivity;
import com.salah.realmtest.adapters.ListOffersAdapter;
import com.salah.realmtest.dialogs.AddOfferDialog;
import com.salah.realmtest.models.Offer;
import com.salah.realmtest.services.RealmService;

import es.dmoral.toasty.Toasty;
import io.realm.Realm;
import io.realm.RealmResults;

public class OffersActivity extends AppCompatActivity {

    private ListView lv_offers ;
    private RealmService realmService;
    private ListOffersAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
        realmService = new RealmService(Realm.getDefaultInstance());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        lv_offers = findViewById(R.id.lv_offers);

        lv_offers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Offer offer = (Offer) lv_offers.getItemAtPosition(position);
                //OfferSubscriptionsListActivity.offer = offer ;
                //Intent intent = new Intent(OffersActivity.this,OfferSubscriptionsListActivity.class);
                //startActivity(intent);
            }
        });
        setup();
    }

    private void setup() {
        RealmResults<Offer> offers = realmService.getAllOffers();
        if (offers.isEmpty()) return;
        adapter = new ListOffersAdapter(this,offers);
        lv_offers.setAdapter(adapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        realmService.closeRealm();
    }

    public void addOffer(View view) {
        AddOfferDialog dialog = new AddOfferDialog(OffersActivity.this) {
            @Override
            public void addOffer(EditText et_title, EditText et_description, EditText et_duration, EditText et_price, EditText et_nb_session, Spinner sp_unit, CheckBox cb_open) {
                try{
                    String title = et_title.getText().toString();
                    String description = et_description.getText().toString();
                    int duration = Integer.parseInt(et_duration.getText().toString());
                    int unit = sp_unit.getSelectedItemPosition();
                    double price = Double.parseDouble(et_price.getText().toString());
                    Boolean open = cb_open.isChecked();
                    int numberSessions = 0;
                    if (!open){
                        numberSessions = Integer.parseInt(et_nb_session.getText().toString());
                    }
                    Offer offer = realmService.addOffer(title,description,duration,unit,price,open,numberSessions, MainActivity.manager);
                    if (offer!=null){
                        setup();
                        dismiss();
                        Toasty.success(OffersActivity.this,offer.getTitle(), Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Toasty.error(OffersActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        };
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setup();
    }

    public void showActionMenu(View view){
        final Offer mOffer = (Offer) view.getTag();
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater()
                .inflate(R.menu.menu_action, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.it_delete){
                    boolean del = realmService.deleteOffer(mOffer);
                    if (del){
                        setup();
                        Toasty.success(OffersActivity.this,"deleted",Toast.LENGTH_LONG).show();
                    }else {
                        setup();
                        Toasty.error(OffersActivity.this,"failed",Toast.LENGTH_LONG).show();
                    }
                }
                if (item.getItemId() == R.id.it_edit){

                }
                return true;
            }
        });

        popup.show();
    }

}
