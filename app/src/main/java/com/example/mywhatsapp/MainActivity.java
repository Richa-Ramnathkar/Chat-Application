package com.example.mywhatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mywhatsapp.Adapters.FragmentsAdapter;
import com.example.mywhatsapp.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        auth = FirebaseAuth.getInstance();
        FloatingActionButton fab=findViewById(R.id.fab);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        setContentView(binding.getRoot());

        binding.viewPager.setAdapter(new FragmentsAdapter(getSupportFragmentManager()));
        binding.tabLayout.setupWithViewPager(binding.viewPager);
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,contacts.class);
                startActivity(i);
            }
        });

    }

    // To get the menu.xml file into the mainActivity.xml file
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Listener when an item from the menu is selected
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.settings:
//                Toast.makeText(this, "Settings MenuItem Selected", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent2);
                break;

            case R.id.logout:
                auth.signOut();
                Intent intent = new Intent(MainActivity.this,SignInActivity.class);
                Toast.makeText(this, "Logout successful", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;


        }
        return true;
    }
}