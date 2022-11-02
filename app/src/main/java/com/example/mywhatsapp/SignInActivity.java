package com.example.mywhatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.mywhatsapp.Models.Users;
import com.example.mywhatsapp.databinding.ActivitySignInBinding;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignInActivity extends AppCompatActivity {

    ActivitySignInBinding binding;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Signing In");
        progressDialog.setMessage("You are being signed in");

        //getString(R.string.default_web_client_id)
        // "144610336051-ilo032ldi202cgvma2q0fa7ohmb75p5p.apps.googleusercontent.com"
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("144610336051-ilo032ldi202cgvma2q0fa7ohmb75p5p.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);
        mGoogleSignInClient.revokeAccess();

        // SignIN with email and password
        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(binding.etEMail.getText().toString().isEmpty()){
                    binding.etEMail.setError("Enter your email");
                    return;
                }
                if(binding.etPassword.getText().toString().isEmpty()){
                    binding.etPassword.setError("Enter password");
                    return;
                }



                progressDialog.show();
                auth.signInWithEmailAndPassword(binding.etEMail.getText().toString(), binding.etPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            Intent intent = new Intent(SignInActivity.this,MainActivity.class);
                            startActivity(intent);      //Redirecting to from SignUpActivity to MainActivity
                        } else{
                            Toast.makeText(SignInActivity.this, "User does not exist", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(SignInActivity.this,SignInActivity.class);
//                            startActivity(intent);
                        }
                    }
                });
            }
        });

        // To redirect the user who is already logged in to the main activity rather than the sigInActivity.
        if(auth.getCurrentUser() != null){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }

        binding.tvClickSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });


    }

    int RC_SIGN_IN = 65;
    private void signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCOde, Intent data){
        super.onActivityResult(requestCode,resultCOde,data);

        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount  account = task.getResult(ApiException.class);
                Log.d("TAG","firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch(ApiException e){
                Log.w("TAG","Google SignIn failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken){
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d("TAG","SignInWIthCredential: success");
                            // Getting the current user details who is signing in from the google db
                            FirebaseUser user = auth.getCurrentUser();
                            // Creating a new user model instance from the Users class
                            Users users = new Users();
                            users.setUserId(user.getUid());
                            users.setUserName(user.getDisplayName());
                            users.setProfilePic(user.getPhotoUrl().toString());
                            database.getReference().child("Users").child(user.getUid()).setValue(users);

                            Intent intent = new Intent(SignInActivity.this,MainActivity.class);
                            startActivity(intent);      //Redirecting to from SignUpActivity to MainActivity
                            Toast.makeText(SignInActivity.this, "Signed in with Google", Toast.LENGTH_SHORT).show();
                            //updateUI(user);
                        } else{
                            Log.w("TAG","signIn with Credential:failure",task.getException());
                            //updateUI(null);
                        }
                    }
                });
    }


}
