package com.salah.realmtest.activities.manager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.salah.realmtest.R;
import com.salah.realmtest.activities.MainActivity;
import com.salah.realmtest.models.Manager;
import com.salah.realmtest.services.RealmService;

import io.realm.Realm;

public class LoginActivity extends AppCompatActivity {

    //public static Manager manager;
    private EditText et_username, et_password;
    private RealmService realmService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        realmService = new RealmService(Realm.getDefaultInstance());

        et_username=findViewById(R.id.et_username);
        et_password=findViewById(R.id.et_password);
    }

    public void onLogin(View view) {
        String userName = et_username.getText().toString();
        String password = et_password.getText().toString();
        Manager loginManager = null ;
        loginManager = realmService.Login(userName,password);
        if (loginManager!=null){
            MainActivity.manager = loginManager;
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void goToNewManager(View view) {
        NewManagerActivity.manager_owner = true ;
        Intent intent = new Intent(LoginActivity.this,NewManagerActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realmService.closeRealm();
    }
}
