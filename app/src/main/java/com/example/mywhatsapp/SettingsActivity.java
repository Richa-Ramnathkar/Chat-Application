package com.example.mywhatsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mywhatsapp.Models.Users;
import com.example.mywhatsapp.databinding.ActivityChatDetailsBinding;
import com.example.mywhatsapp.databinding.ActivitySettingsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class SettingsActivity extends AppCompatActivity {

    ActivitySettingsBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    TextView home,logout,themes,help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
        home=findViewById(R.id.home);
        logout=findViewById(R.id.logout);
        help=findViewById(R.id.help);
        themes=findViewById(R.id.themes);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SettingsActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SettingsActivity.this,help_page.class);
                startActivity(i);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SettingsActivity.this,SignInActivity.class);
                startActivity(i);
            }
        });
        themes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SettingsActivity.this,themespage.class);
                startActivity(i);
            }
        });

        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String status = binding.etStatus.getText().toString();
                String username = binding.etUserName.getText().toString();

//                To update data in firebase we use hashmaps.
                HashMap<String, Object> obj = new HashMap<>();
                obj.put("userName", username);
                obj.put("status", status);

                database.getReference().child("Users").child(auth.getUid()).updateChildren(obj);

                Toast.makeText(SettingsActivity.this, "Data updated successfully", Toast.LENGTH_SHORT).show();
            }
        });

        database.getReference().child("Users").child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                Picasso.get().load(users.getProfilePic()).placeholder(R.drawable.avatar).into(binding.profileImage);
                binding.etUserName.setText(users.getUserName());
                binding.etStatus.setText(users.getStatus());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.plusImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Opening gallery from app.
                Intent intent = new Intent();
//                Opens the gallery of the device.
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 33);
            }
        });
    }

//    Every activity has this onActivityResult method. This method is used with startActivityForResult.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        data is the original data. getData() returns the path of the selected data.
        if(data.getData() != null){
            Uri sFile = data.getData();
            binding.profileImage.setImageURI(sFile);

//            Keeping the name of the profile picture as the uid of the user, saves us space in the db as the next time a pic is uploaded it will override rather than a new node being created. To create a new name for each new inserted node we use the push() method in the firebase.

            final StorageReference reference = storage.getReference().child("profilePictures").child(FirebaseAuth.getInstance().getUid());
            reference.putFile(sFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            database.getReference().child("Users").child(auth.getUid()).child("profilePic").setValue(uri.toString());
                            Toast.makeText(SettingsActivity.this, "Profile Picture Updated", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            });
        }
    }
}