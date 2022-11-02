package com.example.mywhatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mywhatsapp.Adapters.ChatAdapter;
import com.example.mywhatsapp.Models.MessagesModel;
import com.example.mywhatsapp.databinding.ActivityChatDetailsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class GroupChatActivity extends AppCompatActivity {

    ActivityChatDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupChatActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final ArrayList<MessagesModel> messagesModel = new ArrayList<>();

        final String senderId = FirebaseAuth.getInstance().getUid();
        binding.tvUserName.setText("Group");

        final ChatAdapter adapter = new ChatAdapter(messagesModel, this);
        binding.chatRecyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.chatRecyclerView.setLayoutManager(layoutManager);

        //Retrieving the data from the database into the recycler view
        database.getReference().child("Group").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesModel.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    MessagesModel model = dataSnapshot.getValue(MessagesModel.class);
                    messagesModel.add(model);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // Storing the data into the database.
        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(binding.etMessage.getText().toString().isEmpty()){
                    binding.etMessage.setError("Enter message");
                    return;
                }

                final String message = binding.etMessage.getText().toString();
                final MessagesModel model = new MessagesModel(senderId, message);
                model.setTimeStamp(new Date().getTime());

                binding.etMessage.setText("");

                database.getReference().child("Group").push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                });

            }
        });

    }
}