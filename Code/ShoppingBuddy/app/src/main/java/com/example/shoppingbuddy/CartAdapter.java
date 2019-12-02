package com.example.shoppingbuddy;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ItemsViewHolder> {
private ArrayList<Container> itemListArray;
private Context context;

public CartAdapter(ArrayList<Container> itemListArray, Context context) {
        this.itemListArray = itemListArray;
        this.context = context;
        }
public static class ItemsViewHolder extends RecyclerView.ViewHolder {
    public ImageView productImage, delete;
    public TextView productName;
    public TextView cost,quantity,size;


    public LinearLayout linearLayout2;

    public ItemsViewHolder(@NonNull View itemView) {
        super(itemView);

        productImage = itemView.findViewById(R.id.imageIV);
        productName = itemView.findViewById(R.id.iNameTV);
        cost = itemView.findViewById(R.id.priceTV);
        quantity = itemView.findViewById(R.id.quantityTV);
        delete=itemView.findViewById(R.id.imageView10);
        size = itemView.findViewById(R.id.sizeTV);
        linearLayout2 = itemView.findViewById(R.id.linearLayout2);


    }
}
    @NonNull
    @Override
    public CartAdapter.ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_cart_container, viewGroup, false);
        CartAdapter.ItemsViewHolder itemsVH = new CartAdapter.ItemsViewHolder(v);
        return itemsVH;
    }

    @Override
    public void onBindViewHolder(@NonNull final CartAdapter.ItemsViewHolder itemsViewHolder, final int i) {

        final Container currentItem = itemListArray.get(i);

        //itemsViewHolder.itemImage.setImageResource(currentItem.getImage());
        Picasso.get().load(currentItem.getImageURL()).into(itemsViewHolder.productImage);
        itemsViewHolder.productName.setText(currentItem.getItemName());
        itemsViewHolder.cost.setText("price :$" + currentItem.getCost());
        itemsViewHolder.quantity.setText("Quantity: "+currentItem.getQuantity());
        itemsViewHolder.size.setText("Size: "+currentItem.getSize());

        itemsViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartActivity ct=new CartActivity();
                ct.deleteprod(currentItem.getDocumentId());
                Intent in=new Intent(context,CartActivity.class);
                context.startActivity(in);
            }
        });

    }



    @Override
    public int getItemCount() {
        return itemListArray.size();
    }


}

