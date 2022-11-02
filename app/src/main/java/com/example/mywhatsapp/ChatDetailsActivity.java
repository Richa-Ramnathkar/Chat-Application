package com.example.mywhatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class ChatDetailsActivity extends AppCompatActivity {

    ActivityChatDetailsBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;      // To get the ID's

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        binding = ActivityChatDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

//        Receiving the sent data from the UsersAdapter.
        final String senderId = auth.getUid();
        String receiveId = getIntent().getStringExtra("userId");
        String userName = getIntent().getStringExtra("userName");
        String profilePic = getIntent().getStringExtra("profilePic");

       // binding.botIcon.setImageTintList();

//        Inserting the received values to their respective components.
        binding.tvUserName.setText(userName);
        Picasso.get().load(profilePic).placeholder(R.drawable.avatar).into(binding.profileImage);

        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatDetailsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        final ArrayList<MessagesModel> messagesModels = new ArrayList<>();

        final ChatAdapter chatAdapter = new ChatAdapter(messagesModels, this, receiveId);
        binding.chatRecyclerView.setAdapter(chatAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.chatRecyclerView.setLayoutManager(linearLayoutManager);

        final String senderRoom = senderId + receiveId;
        final String receiverRoom = receiveId + senderId;

//          Show the sent messages and received in the recycler view.
        database.getReference().child("Chats").child(senderRoom).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesModels.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    MessagesModel model = dataSnapshot.getValue(MessagesModel.class);
                    model.setMessageId(dataSnapshot.getKey());      // key is the random id which we get when we push an object. Here we are setting that value in the message model so that we can delete it later.
                    messagesModels.add(model);
                }
                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(binding.etMessage.getText().toString().isEmpty()){
                    binding.etMessage.setError("Enter message");
                    return;
                }

                String message = binding.etMessage.getText().toString();
                final MessagesModel model = new MessagesModel(senderId, message);
                model.setTimeStamp(new Date().getTime());       // Time
                binding.etMessage.setText("");

                // Push to create a node.
                database.getReference().child("Chats").child(senderRoom).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        database.getReference().child("Chats").child(receiverRoom).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });
                    }
                });
            }
        });

    }


}