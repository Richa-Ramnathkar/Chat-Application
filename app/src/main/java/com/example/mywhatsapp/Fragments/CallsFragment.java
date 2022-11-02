package com.example.mywhatsapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.mywhatsapp.Adapters.CallListAdapter;
import com.example.mywhatsapp.R;

public class CallsFragment extends Fragment {
    ListView liView;
    int[] imageId = {R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e,
            R.drawable.f,R.drawable.g,R.drawable.h,R.drawable.i};
    String[] name = {"Roy","Jerry","Naina","Rahul","Pooja","Aasim","Yash","Nidhi","Alpha"};
    String[] lastmsgTime = {"8:45 pm","9:00 am","7:34 pm","6:32 am","5:76 am",
            "5:00 am","7:34 pm","2:32 am","7:76 am"};

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calls, container, false);

        liView = view.findViewById(R.id.listview);

        CallListAdapter listAdapter = new CallListAdapter(getContext(),name,imageId,lastmsgTime);
        liView.setAdapter(listAdapter);
        return view;
    }
}