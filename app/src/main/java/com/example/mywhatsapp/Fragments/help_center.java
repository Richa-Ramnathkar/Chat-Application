package com.example.mywhatsapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mywhatsapp.R;

public class help_center extends android.app.Fragment {

    View view;
    TextView textView;
    TextView textview2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_help_center, container, false);
        textView = view.findViewById(R.id.textView);
        textview2=view.findViewById(R.id.textView2);

        textView.setText("How to Login or Logout\n1.open Chitchat and login using email and password ,if You are new User then sign up is must\n\n\n" );
        textview2.setText("\n\n2.Logout can be done by clicking the logout option in settings page");

        return view;
    }
}
