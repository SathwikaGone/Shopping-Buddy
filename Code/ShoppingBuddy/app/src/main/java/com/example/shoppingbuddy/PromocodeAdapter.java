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

public class PromocodeAdapter extends RecyclerView.Adapter<PromocodeAdapter.ItemsViewHolder> {

    private ArrayList<Container> itemListArray;

    public PromocodeAdapter(ArrayList<Container> itemListArray, Context context) {
        this.itemListArray = itemListArray;
        this.context = context;
    }

    private Context context;


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
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_promo_container, viewGroup, false);
        ItemsViewHolder itemsVH = new ItemsViewHolder(v);
        return itemsVH;
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemsViewHolder itemsViewHolder, final int i) {

        final Container currentItem = itemListArray.get(i);

        //itemsViewHolder.itemImage.setImageResource(currentItem.getImage());
        //Picasso.get().load(currentItem.getImageURL()).into(itemsViewHolder.productImage);
        itemsViewHolder.promocode.setText(currentItem.getPromocode());
        itemsViewHolder.price.setText("$ " + currentItem.getPrice());


        itemsViewHolder.linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditorRemovePromo.class);
                //intent.putExtra("imageURL", currentItem.getImageURL());
                intent.putExtra("documentId", currentItem.getDocumentId());
                //intent.putExtra("description", currentItem.getDescription());
                intent.putExtra("promoId", currentItem.getPromoid());
                intent.putExtra("promocode", currentItem.getPromocode());
                intent.putExtra("price", currentItem.getPrice());
                intent.putExtra("des",currentItem.getPdes());
                //intent.putExtra("unitPrice", currentItem.getCost());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemListArray.size();
    }


}

