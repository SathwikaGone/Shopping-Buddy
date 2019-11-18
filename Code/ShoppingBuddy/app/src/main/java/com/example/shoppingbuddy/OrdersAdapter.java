package com.example.shoppingbuddy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ItemsViewHolder> {

    private ArrayList<Container> itemListArray;

    public OrdersAdapter(ArrayList<Container> itemListArray, Context context) {
        this.itemListArray = itemListArray;
        this.context = context;
    }

    private Context context;


    public static class ItemsViewHolder extends RecyclerView.ViewHolder {
        public ImageView productImage;
        public TextView productName;
        public TextView cost;
        public Button cancel,retur;

        public LinearLayout linearLayout2;

        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.imageIV);
            productName = itemView.findViewById(R.id.iNameTV);
            cost = itemView.findViewById(R.id.priceTV);
            cancel=itemView.findViewById(R.id.button24);
            retur=itemView.findViewById(R.id.button25);

            linearLayout2 = itemView.findViewById(R.id.linearLayout2);
        }
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_orders_container, viewGroup, false);
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
        itemsViewHolder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to cancel the product").setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        Orders_Product_List ol=new Orders_Product_List();
                        ol.cancelOrder(currentItem.getDocumentId());
                        Toast.makeText(context, "Product cancelled successfully", Toast.LENGTH_LONG).show();
                        Intent j = new Intent(context, Orders_Product_List.class);
                        j.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(j);

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent j = new Intent(context, Orders_Product_List.class);
                        j.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(j);
                    }
                }).show();

            }
        });
        itemsViewHolder.retur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to return the product").setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        Orders_Product_List ol=new Orders_Product_List();
                        ol.cancelOrder(currentItem.getDocumentId());
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Your return has been proccessed successfully, Please return item in USPS within 7 days").setPositiveButton("OK", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                Intent j = new Intent(context, Orders_Product_List.class);
                                j.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(j);

                            }
                        }).show();

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent j = new Intent(context, Orders_Product_List.class);
                        j.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(j);
                    }
                }).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return itemListArray.size();
    }


}

