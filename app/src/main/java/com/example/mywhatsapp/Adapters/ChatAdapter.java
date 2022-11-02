package com.example.mywhatsapp.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywhatsapp.Models.MessagesModel;
import com.example.mywhatsapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter{

    ArrayList<MessagesModel> messagesModels;
    Context context;
    String recId;

    int SENDER_VIEW_TYPE = 1;
    int RECEIVER_VIEW_TYPE = 2;

    public ChatAdapter(ArrayList<MessagesModel> messagesModels, Context context) {
        this.messagesModels = messagesModels;
        this.context = context;
    }

    public ChatAdapter(ArrayList<MessagesModel> messagesModels, Context context, String recId) {
        this.messagesModels = messagesModels;
        this.context = context;
        this.recId = recId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == SENDER_VIEW_TYPE){
            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
            return new SenderViewHolder(view);
        } else{
            View view = LayoutInflater.from(context).inflate(R.layout.sample_receiver,parent,false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
//        It is the sender if it is true.
        if(messagesModels.get(position).getuId().equals(FirebaseAuth.getInstance().getUid())){
            return SENDER_VIEW_TYPE;
        } else{
            return RECEIVER_VIEW_TYPE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MessagesModel messagesModel = messagesModels.get(position);

//        ItemView because we are adding lister to the whole sample view and not only the textview.
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(context).setTitle("Delete").setMessage("Are you sure you want to delete this message?").setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        String senderRoom = FirebaseAuth.getInstance().getUid() + recId;
//                        This will set the node value to null i.i delete that node.
                        database.getReference().child("Chats").child(senderRoom).child(messagesModel.getMessageId()).setValue(null);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

                return false;
            }
        });

        if(holder.getClass() == SenderViewHolder.class){
            ((SenderViewHolder)holder).tvSenderMsg.setText(messagesModel.getMessage());     // ((SenderViewHolder)holder - Because here we have 2 holders

        } else {
            ((ReceiverViewHolder)holder).tvReceiverMsg.setText(messagesModel.getMessage());

        }

    }

    @Override
    public int getItemCount() {
        return messagesModels.size();
    }

    //    Here because we have two layouts i.e of the sender and the receiver so we will be using 2 ViewHolders here.
    public class ReceiverViewHolder extends RecyclerView.ViewHolder {

        TextView tvReceiverMsg, tvReceiverTime;
        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            tvReceiverMsg = itemView.findViewById(R.id.tvReceiverMsg);
            tvReceiverTime = itemView.findViewById(R.id.tvSenderTime);

        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder {

        TextView tvSenderMsg, tvSenderTime;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSenderMsg = itemView.findViewById(R.id.tvSenderMsg);
            tvSenderTime = itemView.findViewById(R.id.tvSenderTime);

        }
    }

}
