package com.example.shoppingbuddy;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class Footwear_Item_Details extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ImageView imageIV;
    private TextView itemNameTV, priceTV, detailsTV,quantityTV,sizeTV;
    private FirebaseFirestore db;
    private String imageURL,docId;
    private DocumentReference itemRef;
    private Button cart,six,seven,eight,nine,ten;
    private Spinner spinner;
    private static final String[] paths = {"1","2","3","4","5","6","7","8","9","10"};
    Spinner quantity;

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
//        six=findViewById(R.id.button17);
//        seven=findViewById(R.id.button18);
//        eight=findViewById(R.id.button19);
//        nine=findViewById(R.id.button20);
//        ten=findViewById(R.id.button21);
        db = FirebaseFirestore.getInstance();
        quantity=findViewById(R.id.spinner);
        quantityTV=findViewById(R.id.textView32);
        sizeTV=findViewById(R.id.textView31);
//      Get data from the Intent
        Intent i = getIntent();
        docId = i.getStringExtra("documentId");
        imageURL = i.getStringExtra("imageURL");
        //imageIV.setImageResource(i.getIntExtra("image",0));
        Picasso.get().load(imageURL).into(imageIV);
        itemNameTV.setText(i.getStringExtra("itemName"));
        priceTV.setText("Buying price :$" + i.getDoubleExtra("unitPrice",0));

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



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
