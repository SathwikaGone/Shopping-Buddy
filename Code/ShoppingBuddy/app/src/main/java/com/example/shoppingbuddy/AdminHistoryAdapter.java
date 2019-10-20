package com.example.shoppingbuddy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdminHistoryAdapter extends RecyclerView.Adapter<AdminHistoryAdapter.ItemsViewHolder> {

    private ArrayList<Container> itemListArray;

    public AdminHistoryAdapter(ArrayList<Container> itemListArray, Context context) {
        this.itemListArray = itemListArray;
        this.context = context;
    }

    private Context context;


    public static class ItemsViewHolder extends RecyclerView.ViewHolder {
        public ImageView productImage;
        public TextView productName;
        public TextView cost;

        public LinearLayout linearLayout2;

        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.imageIV);
            productName = itemView.findViewById(R.id.iNameTV);
            cost = itemView.findViewById(R.id.priceTV);

            linearLayout2 = itemView.findViewById(R.id.linearLayout2);
        }
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_container, viewGroup, false);
        ItemsViewHolder itemsVH = new ItemsViewHolder(v);
        return itemsVH;
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemsViewHolder itemsViewHolder, final int i) {

        final Container currentItem = itemListArray.get(i);

        //itemsViewHolder.itemImage.setImageResource(currentItem.getImage());
        Picasso.get().load(currentItem.getImageURL()).into(itemsViewHolder.productImage);
        itemsViewHolder.productName.setText(currentItem.getItemName());
        itemsViewHolder.cost.setText("Buying price :$" + currentItem.getCost());
    }

    @Override
    public int getItemCount() {
        return itemListArray.size();
    }


}
