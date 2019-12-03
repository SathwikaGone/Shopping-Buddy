package com.example.shoppingbuddy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class Footwear_Item_Details extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ImageView imageIV;
    private TextView itemNameTV, priceTV, detailsTV,quantityTV,sizeTV, averageRating;
    private FirebaseFirestore db;
    private String imageURL,docId,size1,curruser, itemid;
    private DocumentReference itemRef;
    private Button cart,six,seven,eight,nine,ten;
    private Spinner spinner;

    private static final String[] paths = {"1","2","3","4","5","6","7","8","9","10"};
    Spinner quantity;
    private Long quant;
    private RadioGroup size;
    private RadioButton selectedRadioButton;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private CollectionReference itemCollection,ratingCollection, ordersCollection, productCollection;
    private double r;
    private RatingBar rating1;
    private int check=0,  count=0;
    private double avg=0.0, sum=0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footwear__item__details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageIV = findViewById(R.id.imageIV);
        itemNameTV = findViewById(R.id.itemNameTV);
        priceTV = findViewById(R.id.priceTV);
        detailsTV = findViewById(R.id.detailsTV);

        cart=findViewById(R.id.button16);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        rating1=findViewById(R.id.ratingBar);

        db = FirebaseFirestore.getInstance();
        itemCollection = db.collection("cart");
        ratingCollection=db.collection("rating");
        ordersCollection=db.collection("orders");
        productCollection=db.collection("products");
        quantity=findViewById(R.id.spinner);
        quantityTV=findViewById(R.id.textView32);
        sizeTV=findViewById(R.id.textView31);
        size=findViewById(R.id.size);
        averageRating=findViewById(R.id.textView69);
//      Get data from the Intent
        Intent i = getIntent();
        docId = i.getStringExtra("documentId");
        imageURL = i.getStringExtra("imageURL");
        itemid=i.getStringExtra("itemId");
        //imageIV.setImageResource(i.getIntExtra("image",0));
        Picasso.get().load(imageURL).into(imageIV);
        itemNameTV.setText(i.getStringExtra("itemName"));
        averageRating.setText("Average Rating: 3.0");
        priceTV.setText("$" + i.getDoubleExtra("unitPrice",0));

//        Get an instance of the items
        itemRef = db.collection("products").document(docId);
        itemRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                detailsTV.setText(documentSnapshot.get("itemDetails").toString());
            }
        });
        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Footwear_Item_Details.this,
                android.R.layout.simple_spinner_item,paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("quantity", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//
        rating1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                r=rating;
                ratingCollection.orderBy("user",Query.Direction.ASCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            if (doc.getString("user").equals(user.getEmail()) && doc.getString("item").equals(itemid)) {
                                check=1;
                            }
                        }
                        if(check==1){
                            AlertDialog.Builder builder = new AlertDialog.Builder(Footwear_Item_Details.this);
                            builder.setMessage("You have already rated this item").setPositiveButton("OK", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which){
                                    Intent j = new Intent(Footwear_Item_Details.this, Home.class);
                                    j.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(j);

                                }
                            }).show();
                        }
                        else{
                            Log.d("rate","inside rating onclick");
                            Map<String, Object> addproduct = new HashMap<>();
                            addproduct.put("item", itemid);
                            addproduct.put("rating", r);
                            addproduct.put("user", user.getEmail());
                            ratingCollection.document().set(addproduct);
                            Log.d("rate","inside rating posted");
                            Toast.makeText(Footwear_Item_Details.this, "Rating added to the list", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        ratingCollection.orderBy("user",Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    if (doc.getString("item").equals(itemid)) {
                        sum=sum+doc.getDouble("rating");
                        count=count+1;
                    }
                }
                avg=sum/count;
                averageRating.setText("Average rating: "+String.format("%.2f",avg));
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quant=Long.parseLong(quantity.getSelectedItem().toString());

                int selectedId = size.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                selectedRadioButton = (RadioButton)findViewById(selectedId);
                size1=selectedRadioButton.getText().toString();
                curruser=user.getEmail();
                Map<String, Object> addcart = new HashMap<>();
                addcart.put("user", curruser);
                addcart.put("itemId", docId);
                addcart.put("quantity", quant);
                addcart.put("size", size1);
                itemCollection.document().set(addcart);
                Toast.makeText(Footwear_Item_Details.this, "Item added to the cart", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        FirebaseAuth auth1 = FirebaseAuth.getInstance();
        FirebaseUser user = auth1.getCurrentUser();
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.textView);
        navUsername.setText(user.getEmail());
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cart) {
            Intent i = new Intent(Footwear_Item_Details.this,CartActivity.class);
            startActivity(i);
        }
        if (id == R.id.action_chat) {
            Intent i = new Intent(this,UserChatActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            // Handle the home action
            Intent in=new Intent(this,Home.class);
            startActivity(in);}
        else if (id == R.id.electronics) {
            Intent in=new Intent(this,ElectronicsActivity.class);
            startActivity(in);}
        else if (id == R.id.clothing) {
            // Handle the accessories action
            Intent in=new Intent(this,ClothingActivity.class);
            startActivity(in);
        }
        else if (id == R.id.accessories) {
            // Handle the accessories action
            Intent in=new Intent(this,AccessoriesActivity.class);
            startActivity(in);
        }
        else if (id == R.id.footwear) {
            // Handle the accessories action
            Intent in=new Intent(this,Footwear.class);
            startActivity(in);
        }
        else if (id == R.id.logout) {
            // Handle the accessories action
            Intent in=new Intent(this,MainActivity.class);
            startActivity(in);
        }
        else if(id==R.id.orders){
            Intent in=new Intent(this,Orders_Product_List.class);
            startActivity(in);
        }
        else if (id == R.id.settings) {
            // Handle the accessories action
            Intent in = new Intent(this, Settings.class);
            startActivity(in);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
