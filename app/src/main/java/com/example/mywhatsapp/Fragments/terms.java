package com.example.mywhatsapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mywhatsapp.R;

public class terms extends android.app.Fragment {

    View view;
    TextView textView;
    TextView textView2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_help_center, container, false);
        textView = view.findViewById(R.id.textView);
        textView2=view.findViewById(R.id.textView2);

        textView.setText("\nWe have built our services with strong privacy and security principles in mind" );
        textView2.setText( "\n\nwe always strive to improve,ways for you to communicate with people");

        return view;
    }
}
