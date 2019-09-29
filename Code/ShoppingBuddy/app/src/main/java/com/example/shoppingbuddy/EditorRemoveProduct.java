package com.example.shoppingbuddy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class EditorRemoveProduct extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView prodimage;
    private EditText  productid, productname, productdes, productcost, qntityNeededET, requiredByET;
    private Button saveChangesBTN, cancelBTN, removeBTN;
    private TextView id,name, cate ;
    private String imageURL, prodcat, documentId, prodid, prodname,  proddes;
    private Double prodcost;
    private Spinner productcate;

    private FirebaseFirestore db;
    private DocumentReference itemDoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_remove_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        productid=findViewById(R.id.editText6);
        productname=findViewById(R.id.editText9);
        productdes=findViewById(R.id.editText12);
        productcate=findViewById(R.id.spinner);
        productcost=findViewById(R.id.editText13);
        prodimage=findViewById(R.id.imageView3);
        saveChangesBTN=findViewById(R.id.button12);
        removeBTN=findViewById(R.id.button15);
        cancelBTN=findViewById(R.id.button14);
        name=findViewById(R.id.textView37);
        cate=findViewById(R.id.textView39);
        id=findViewById(R.id.textView38);


        db = FirebaseFirestore.getInstance();

//      Get data from the Intent
        Intent i = getIntent();

        prodid = i.getStringExtra("itemId");
        prodcost = i.getDoubleExtra("unitPrice",0);
        documentId = i.getStringExtra("documentId");
        imageURL = i.getStringExtra("imageURL");
        Picasso.get().load(imageURL).into(prodimage);
        name.setText(i.getStringExtra("itemName"));
        id.setText(prodid);
        cate.setText(i.getStringExtra("category"));
        productcost.setText(prodcost.toString());
        productdes.setText(i.getStringExtra("description"));

        itemDoc = db.collection("products").document(documentId);


        saveChangesBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proddes = productdes.getText().toString();
                prodcost = Double.parseDouble(productcost.getText().toString());
                AlertDialog.Builder builder = new AlertDialog.Builder(EditorRemoveProduct.this);
                builder.setMessage("Are you sure you want to save the changes").setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        itemDoc.update("itemDetails", proddes, "cost", prodcost);
                        Toast.makeText(EditorRemoveProduct.this, "Product updated successfully", Toast.LENGTH_LONG).show();
                        Intent j = new Intent(EditorRemoveProduct.this, AdminHomeActivity.class);
                        j.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(j);
                        EditorRemoveProduct.this.finish();

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();
            }
        });


        cancelBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditorRemoveProduct.this, SelectProduct.class);
                startActivity(i);
            }
        });
        removeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditorRemoveProduct.this);
                builder.setMessage("Are you sure you want to delete the product").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        itemDoc.delete();
                        finish();
                        Toast.makeText(EditorRemoveProduct.this, "Product deleted successfully", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(EditorRemoveProduct.this, AdminHomeActivity.class);
                        startActivity(i);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();
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
        getMenuInflater().inflate(R.menu.admin_home, menu);
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
