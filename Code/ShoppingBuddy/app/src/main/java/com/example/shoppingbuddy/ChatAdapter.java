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

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ItemsViewHolder> {

    private ArrayList<Container> itemListArray;

    public ChatAdapter(ArrayList<Container> itemListArray, Context context) {
        this.itemListArray = itemListArray;
        this.context = context;
    }

    public ChatAdapter() {
    }

    private Context context;


    public static class ItemsViewHolder extends RecyclerView.ViewHolder {

        public TextView email;
        public TextView subject;

        public LinearLayout linearLayout2;

        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);

            email = itemView.findViewById(R.id.Email);
            subject = itemView.findViewById(R.id.Subject);


            linearLayout2 = itemView.findViewById(R.id.linearLayout2);
        }
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_adminchat_container, viewGroup, false);
        ItemsViewHolder itemsVH = new ItemsViewHolder(v);
        return itemsVH;
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemsViewHolder itemsViewHolder, final int i) {

        final Container currentItem = itemListArray.get(i);

        //itemsViewHolder.itemImage.setImageResource(currentItem.getImage());

        itemsViewHolder.email.setText(currentItem.getEmail());
        itemsViewHolder.subject.setText("Subject: " + currentItem.getSubject());

//        itemsViewHolder.linearLayout2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, ItemDetails.class);
//                intent.putExtra("imageURL", currentItem.getImageURL());
//                intent.putExtra("documentId", currentItem.getDocumentId());
//                intent.putExtra("description", currentItem.getDescription());
//                intent.putExtra("itemId", currentItem.getItemID());
//                intent.putExtra("category", currentItem.getCategory());
//                intent.putExtra("itemName", currentItem.getItemName());
//                intent.putExtra("unitPrice", currentItem.getCost());
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return itemListArray.size();
    }


}

