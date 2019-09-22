package com.example.shoppingbuddy;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemDetails extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ImageView imageIV;
    private TextView itemNameTV, priceTV, detailsTV;
    private FirebaseFirestore db;
    private String imageURL,docId;
    private DocumentReference itemRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageIV = findViewById(R.id.imageIV);
        itemNameTV = findViewById(R.id.itemNameTV);
        priceTV = findViewById(R.id.priceTV);
        detailsTV = findViewById(R.id.detailsTV);
        db = FirebaseFirestore.getInstance();
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
        getMenuInflater().inflate(R.menu.item_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
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
            startActivity(in);
        }
        else if (id == R.id.accessories) {
            Intent in=new Intent(this,AccessoriesActivity.class);
            startActivity(in);

        } else if (id == R.id.electronics) {
            Intent in=new Intent(this,ElectronicsActivity.class);
            startActivity(in);
        }
        else if (id == R.id.clothing) {
            Intent in=new Intent(this,ClothingActivity.class);
            startActivity(in);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
