package com.example.mywhatsapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class splash extends AppCompatActivity {

    Animation zoomin;
    ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window= this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.black));
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent sp= new Intent(splash.this, SignInActivity.class);
                startActivity(sp);
            }
        },3000);
        logo=findViewById(R.id.logo);
        zoomin= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.logo_zoom_in);
        logo.startAnimation(zoomin);


    }
}