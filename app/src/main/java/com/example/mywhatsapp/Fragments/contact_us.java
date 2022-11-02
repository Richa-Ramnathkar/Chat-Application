package com.example.mywhatsapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mywhatsapp.R;

public class contact_us extends android.app.Fragment {

    View view;
    TextView textView;
    TextView textview2;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_help_center, container, false);
        textView = view.findViewById(R.id.textView);
        textview2=view.findViewById(R.id.textView2);

        textView.setText("Tell us how can we help" );
        textview2.setText("\n\n"+"Technical details like your model and settings can help us answer your question");

        return view;
    }
}
