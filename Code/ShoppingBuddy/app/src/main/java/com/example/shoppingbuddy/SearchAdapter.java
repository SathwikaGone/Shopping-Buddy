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

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ItemsViewHolder> {

    private ArrayList<Container> itemListArray;

    public SearchAdapter(ArrayList<Container> itemListArray, Context context) {
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

        itemsViewHolder.linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentItem.getCategory().equals("Men's clothing") || currentItem.getCategory().equals("Women's clothing") ||currentItem.getCategory().equals("Kid's clothing")){
                    Intent intent = new Intent(context, Clothing_Item_Details.class);
                    intent.putExtra("imageURL", currentItem.getImageURL());
                    intent.putExtra("documentId", currentItem.getDocumentId());
                    intent.putExtra("description", currentItem.getDescription());
                    intent.putExtra("itemId", currentItem.getItemID());
                    intent.putExtra("category", currentItem.getCategory());
                    intent.putExtra("itemName", currentItem.getItemName());
                    intent.putExtra("unitPrice", currentItem.getCost());
                    intent.putExtra("size",currentItem.getSize());
                    context.startActivity(intent);
                }
                if(currentItem.getCategory().equals("Men's Accessories") || currentItem.getCategory().equals("Women's Accessories") ||currentItem.getCategory().equals("Cell Phones and Accessories") || currentItem.getCategory().equals("Tv and Computers")){
                    Intent intent = new Intent(context, ItemDetails.class);
                    intent.putExtra("imageURL", currentItem.getImageURL());
                    intent.putExtra("documentId", currentItem.getDocumentId());
                    intent.putExtra("description", currentItem.getDescription());
                    intent.putExtra("itemId", currentItem.getItemID());
                    intent.putExtra("category", currentItem.getCategory());
                    intent.putExtra("itemName", currentItem.getItemName());
                    intent.putExtra("unitPrice", currentItem.getCost());
                    context.startActivity(intent);
                }
                if(currentItem.getCategory().equals("Men's Footwear") || currentItem.getCategory().equals("Women's Footwear")){
                    Intent intent = new Intent(context, ItemDetails.class);
                    intent.putExtra("imageURL", currentItem.getImageURL());
                    intent.putExtra("documentId", currentItem.getDocumentId());
                    intent.putExtra("description", currentItem.getDescription());
                    intent.putExtra("itemId", currentItem.getItemID());
                    intent.putExtra("category", currentItem.getCategory());
                    intent.putExtra("itemName", currentItem.getItemName());
                    intent.putExtra("unitPrice", currentItem.getCost());
                    intent.putExtra("size",currentItem.getSize());
                    context.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return itemListArray.size();
    }


}
