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

public class AdminPromoAdapter extends RecyclerView.Adapter<AdminPromoAdapter.ItemsViewHolder> {
    private ArrayList<Container> itemListArray;
    private Context context;

    public AdminPromoAdapter(ArrayList<Container> itemListArray, Context context) {
        this.itemListArray = itemListArray;
        this.context = context;
    }

    public static class ItemsViewHolder extends RecyclerView.ViewHolder {
        public TextView price, promocode;

        public LinearLayout linearLayout2;
        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            promocode = itemView.findViewById(R.id.iNameTV);
            price = itemView.findViewById(R.id.priceTV);
            linearLayout2 = itemView.findViewById(R.id.linearLayout2);

        }
    }
    @NonNull
    @Override
    public AdminPromoAdapter.ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_promo_container, viewGroup, false);
        AdminPromoAdapter.ItemsViewHolder itemsVH = new AdminPromoAdapter.ItemsViewHolder(v);
        return itemsVH;
    }



    @Override
    public void onBindViewHolder(@NonNull final AdminPromoAdapter.ItemsViewHolder itemsViewHolder, final int i) {

        final Container currentItem = itemListArray.get(i);
        itemsViewHolder.promocode.setText(currentItem.getPromocode());
        itemsViewHolder.price.setText("$ " + currentItem.getPrice());
    }
    @Override
    public int getItemCount() {
        return itemListArray.size();
    }

}
