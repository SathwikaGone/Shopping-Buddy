package com.example.shoppingbuddy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ItemsViewHolder> {
    private ArrayList<Container> itemListArray;
    private Context context;

    public UsersAdapter(ArrayList<Container> itemListArray, Context context) {
        this.itemListArray = itemListArray;
        this.context = context;
    }

    public static class ItemsViewHolder extends RecyclerView.ViewHolder {
        public TextView userEmail;

        public LinearLayout linearLayout2;
        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            userEmail = itemView.findViewById(R.id.iNameTV);
            linearLayout2 = itemView.findViewById(R.id.linearLayout2);

        }
    }
    @NonNull
    @Override
    public UsersAdapter.ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_userhistory_container, viewGroup, false);
        UsersAdapter.ItemsViewHolder itemsVH = new UsersAdapter.ItemsViewHolder(v);
        return itemsVH;
    }



    @Override
    public void onBindViewHolder(@NonNull final UsersAdapter.ItemsViewHolder itemsViewHolder, final int i) {

        final Container currentItem = itemListArray.get(i);
        itemsViewHolder.userEmail.setText(currentItem.getSender());
        itemsViewHolder.linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserOrdersActivity.class);
                intent.putExtra("email",currentItem.getSender());
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return itemListArray.size();
    }


}
