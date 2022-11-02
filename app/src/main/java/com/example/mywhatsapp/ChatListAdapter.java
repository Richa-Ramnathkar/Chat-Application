package com.example.mywhatsapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChatListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    String[] Names;
    int[] profilePics;
    String[] timeStamp;
    String[] LastMessage;

    public ChatListAdapter(Context c, String[] names, int[] img, String[] lastmsg, String[] timeStamp) {
        context = c;
        Names = names;
        profilePics = img;
        LastMessage = lastmsg;
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
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (view == null) {
            view = inflater.inflate(R.layout.list_item, null);
            ImageView imgV = view.findViewById(R.id.profile_pic);
            TextView name = view.findViewById(R.id.personName);
            TextView time = view.findViewById(R.id.msgtime);
            //TextView msg = view.findViewById(R.id.lastmsg);

            imgV.setImageResource(profilePics[i]);
            name.setText(Names[i]);
            //msg.setText(LastMessage[i]);
            time.setText(timeStamp[i]);

        }
        return view;
    }
}
