package com.example.mywhatsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class themespage extends AppCompatActivity {
    private Button btntoggledark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themespage);
        getSupportActionBar().setTitle("Themes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btntoggledark=findViewById(R.id.button1);
        SharedPreferences sharedPreferences=getSharedPreferences("sharedprefs",MODE_PRIVATE);
        final SharedPreferences.Editor editor=sharedPreferences.edit();
        final Boolean isDarkModeOn=sharedPreferences.getBoolean("isDarkModeOn",false);
        if(isDarkModeOn)
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            btntoggledark.setText("Disable Dark Mode");
        }
        else
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            btntoggledark.setText("Enable Dark Mode");
        }
        btntoggledark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isDarkModeOn) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putBoolean("isDarkModeOn", false);
                    editor.apply();

                    btntoggledark.setText("Enable Dark Mode");
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor.putBoolean("isDarkModeOn", true);
                    editor.apply();
                    btntoggledark.setText("Disable Dark Mode");
                }
            }
        });
    }
}
