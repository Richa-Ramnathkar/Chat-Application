package com.example.mywhatsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import com.example.mywhatsapp.Fragments.help_center;
import com.example.mywhatsapp.Fragments.terms;
 import  com.example.mywhatsapp.Fragments.contact_us;
       import  com.example.mywhatsapp.Fragments.app_info;


public class help_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_page);
        getSupportActionBar().setTitle("HELP");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ListView listView = findViewById(R.id.listview1);
        FrameLayout f1 = findViewById(R.id.frameLayout);
        String[] listItem = getResources().getStringArray(R.array.array_list);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, listItem);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String value = adapter.getItem(position);
                if (value.equals("HELP CENTER"))
                    loadFragment(new help_center());
                else if (value.equals("CONTACT US"))
                    loadFragment(new contact_us());
                else if (value.equals("TERMS"))
                    loadFragment(new terms());
                else if (value.equals("APP INFO"))
                    loadFragment(new app_info());


            }
        });
    }


    public void loadFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        // FragmentManager fragmentManager = getFragmentManager();
        //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();

    }
}