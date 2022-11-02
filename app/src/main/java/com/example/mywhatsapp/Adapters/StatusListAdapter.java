package com.example.mywhatsapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mywhatsapp.R;

public class StatusListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    String[] Names;
    int[] profilePics;
    String[] timeStamp;

    public StatusListAdapter(Context c, String[] names, int[] img,String[] timeStamp) {
        context = c;
        Names = names;
        profilePics=img;
        this.timeStamp = timeStamp;
    }

    @Override
    public int getCount() {
        return Names.length;
    }


    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(view == null) {
            view = inflater.inflate(R.layout.list_item,null);
            ImageView imgV = view.findViewById(R.id.profile_pic);
            TextView name = view.findViewById(R.id.personName);
            TextView time = view.findViewById(R.id.msgtime);

            imgV.setImageResource(profilePics[i]);
            name.setText(Names[i]);
            time.setText(timeStamp[i]);
        }
        return view;
    }
}
