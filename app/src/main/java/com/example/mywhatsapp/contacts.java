package com.example.mywhatsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class contacts extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Contacts");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_contacts);


        int[] imageId = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e,
                R.drawable.f, R.drawable.g, R.drawable.h, R.drawable.i};
        String[] name = {"Lisa", "Rahul", "Jihan", "Manish", "Neha", "Megha", "Tina", "Imran", "Arhaan"};

        String[] phoneNo = {"7656610000", "9999043232", "7834354323", "9876543211", "5434432343",
                "9439043232", "7534354323", "6545543211", "7654432343"};
        String[] country = {"United States", "Russia", "India", "Israel", "Germany", "Thailand", "Canada", "France", "Switzerland"};
        listView = (ListView) findViewById(R.id.listview);

        ChatListAdapter listAdapter = new ChatListAdapter(contacts.this,name,imageId,phoneNo,country);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(contacts.this,ChatDetailsActivity.class);
                startActivity(intent);
            }
        });

    }
}