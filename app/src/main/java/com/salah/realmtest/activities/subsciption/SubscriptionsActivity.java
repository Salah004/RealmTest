package com.salah.realmtest.activities.subsciption;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.salah.realmtest.R;
import com.salah.realmtest.adapters.ListSubscriptionsAdapter;
import com.salah.realmtest.models.Athlete;
import com.salah.realmtest.models.Subscription;
import com.salah.realmtest.services.RealmService;

import es.dmoral.toasty.Toasty;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class SubscriptionsActivity extends AppCompatActivity {

    public static Athlete athlete;
    private ListView lv_subscriptions;
    RealmService realmService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriptions);
        realmService = new RealmService(Realm.getDefaultInstance());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        lv_subscriptions = findViewById(R.id.lv_subscriptions);

        lv_subscriptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Subscription subscription = (Subscription) lv_subscriptions.getItemAtPosition(position);
                Toasty.success(SubscriptionsActivity.this,subscription.getId(),Toast.LENGTH_LONG).show();
            }
        });

        lv_subscriptions.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final Subscription subscription = (Subscription) lv_subscriptions.getItemAtPosition(position);
                PopupMenu popup = new PopupMenu(SubscriptionsActivity.this, view);
                popup.getMenuInflater()
                        .inflate(R.menu.menu_action, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.it_delete){
                            boolean del = realmService.deleteSubscription(subscription);
                            if (del){
                                setup();
                                Toasty.success(SubscriptionsActivity.this,"deleted",Toast.LENGTH_LONG).show();
                            }else {
                                setup();
                                Toasty.error(SubscriptionsActivity.this,"failed",Toast.LENGTH_LONG).show();
                            }
                        }
                        if (item.getItemId() == R.id.it_edit){

                        }
                        return true;
                    }
                });

                popup.show();
                return false;
            }
        });

        setup();
    }

    private void setup() {

        RealmResults<Subscription> subscriptions = athlete.getSubscriptions().where().sort("endDate", Sort.DESCENDING).findAll();
        if (subscriptions.isEmpty()) return;
        ListSubscriptionsAdapter adapter = new ListSubscriptionsAdapter(this,subscriptions);
        lv_subscriptions.setAdapter(adapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realmService.closeRealm();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setup();
    }



    public void goToAddSubscription(View view) {
        AddSubscriptionActivity.athlete=athlete;
        Intent intent = new Intent(SubscriptionsActivity.this,AddSubscriptionActivity.class);
        startActivity(intent);
    }
}
