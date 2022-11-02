package com.example.mywhatsapp.Fragments;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mywhatsapp.R;

public class app_info extends Fragment {

    View view;
    TextView textView,textView2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_app_info, container, false);
        textView = view.findViewById(R.id.textView);
        textView2 = view.findViewById(R.id.textView2);
        textView.setText("\t\tChitChat\n");
        textView2.setText( "we specifically permit and encourage the use of this software\n" +
                "Permission for use of this software is granted \nonly if user accepts full responibility of consequences ");
        return view;
    }
}
