package com.example.shoppingbuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ItemsViewHolder> {

    private ArrayList<Container> itemListArray;
    public static final int MSG_TYPE_LEFT=0;
    public static final int MSG_TYPE_RIGHT=1;

    FirebaseUser user;
    public String email;
    public ChatAdapter(ArrayList<Container> itemListArray, Context context) {
        this.itemListArray = itemListArray;
        this.context = context;
    }

    public ChatAdapter() {
    }

    private Context context;


    public static class ItemsViewHolder extends RecyclerView.ViewHolder {

        public TextView message;
        public LinearLayout linearLayout2;

        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);

            message = itemView.findViewById(R.id.showmsg);
            linearLayout2 = itemView.findViewById(R.id.linearLayout2);
        }
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(i==MSG_TYPE_LEFT) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_chat_itemleft, viewGroup, false);
            ItemsViewHolder itemsVH = new ItemsViewHolder(v);
            return itemsVH;
        }
        else{
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_chat_itemright, viewGroup, false);
            ItemsViewHolder itemsVH = new ItemsViewHolder(v);
            return itemsVH;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemsViewHolder itemsViewHolder, final int i) {

        final Container currentItem = itemListArray.get(i);
        itemsViewHolder.message.setText(currentItem.getMessage());
    }

    @Override
    public int getItemCount() {
        return itemListArray.size();
    }

    @Override
    public int getItemViewType(int position) {
        user=FirebaseAuth.getInstance().getCurrentUser();
        //email=user.getEmail();
        if(itemListArray.get(position).getSender().equals(user.getEmail())){
            return MSG_TYPE_RIGHT;
        }
        else{
            return MSG_TYPE_LEFT;
        }
    }
}

