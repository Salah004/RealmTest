package com.salah.realmtest.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.salah.realmtest.R;
import com.salah.realmtest.activities.manager.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    private TextView tv;
    private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tv = findViewById(R.id.tv);
        iv = findViewById(R.id.iv);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.splash_transition);
        tv.startAnimation(animation);
        iv.startAnimation(animation);
        final Intent intent = new Intent(this, LoginActivity.class);
        Thread timer = new Thread(){
            public void run(){
                try {
                    sleep(5000);
                }catch (Exception e){

                }finally {
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();
    }
}
