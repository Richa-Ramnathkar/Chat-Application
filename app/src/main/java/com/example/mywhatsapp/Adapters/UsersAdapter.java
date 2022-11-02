package com.example.mywhatsapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywhatsapp.ChatDetailsActivity;
import com.example.mywhatsapp.Models.Users;
import com.example.mywhatsapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.Viewholder>{

    ArrayList<Users> list;
    Context context;

    public UsersAdapter(ArrayList<Users> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_show_users,parent,false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        // Users is the person on which the user clicked from  the users list.
        Users users = list.get(position);
//        Picasso is used to get images from firebase(Internet) to our application. It is a dependency.
        Picasso.get().load(users.getProfilePic()).placeholder(R.drawable.avatar).into(holder.image);
        holder.userName.setText(users.getUserName());
//        By default firebase stores data in ascending order so the order by clause change it to descending order. Limit tp fetches us only the prescribed no. of tuples.
        // addListerForSingleValueEvent because we want only a single value this time from the snapshot
        FirebaseDatabase.getInstance().getReference().child("Chats").child(FirebaseAuth.getInstance().getUid() + users.getUserId()).orderByChild("timestamp").limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // To prevent a null pointer exception. What if there are no children in the nodes.
                if(snapshot.hasChildren()){
                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
                        holder.lastMessage.setText(snapshot1.child("message").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        Redirect to the chat details page of the clicked user and send data to that chat details activity.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatDetailsActivity.class);
                // Sending data to the chat details activity.
                intent.putExtra("userId", users.getUserId());
                intent.putExtra("profilePic", users.getProfilePic());
                intent.putExtra("userName", users.getUserName());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView userName, lastMessage;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.profile_image);
            userName = itemView.findViewById(R.id.tvUsername);
            lastMessage = itemView.findViewById(R.id.tvLastMessage);
        }
    }

}
