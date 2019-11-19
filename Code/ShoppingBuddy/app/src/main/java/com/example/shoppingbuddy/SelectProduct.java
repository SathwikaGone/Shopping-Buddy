package com.example.shoppingbuddy;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;

import java.util.ArrayList;

public class SelectProduct extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    private static final String[] paths = {"Men's clothing", "Women's clothing", "Kid's clothing",
            "Men's Accessories", "Women's Accessories", "Cell Phones and Accessories",
            "Tv and Computers", "Men's Footwear", "Women's Footwear","Books","Sports Equipment","Hair","Skin","Makeup"};
    Spinner productcate;
    private Spinner spinner;
    String category;
    SearchView search;
    private FirebaseFirestore db;
    private StorageReference StorageRef;
    private CollectionReference productCollection;
    private RecyclerView productLV;
    private RecyclerView.Adapter productsAdapter;
    private RecyclerView.LayoutManager productLayoutManager;
    private String q;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        db = FirebaseFirestore.getInstance();
        StorageRef = FirebaseStorage.getInstance().getReference().child("productimages");
        productCollection = db.collection("products");
        search=findViewById(R.id.search);
        search.setIconifiedByDefault(false);
        search.setQueryHint("Enter the product to be edited");
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                q=query;
                final ArrayList<Container> itemListArray = new ArrayList<>();

                productCollection.orderBy("itemId", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int i = 0;
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                Log.d("search method", "inside for loop");

                                if (doc.getString("category").toLowerCase().contains(q.toLowerCase()) || doc.getString("itemName").toLowerCase().contains(q.toLowerCase()) || doc.getString("itemDetails").toLowerCase().contains(q.toLowerCase()) || doc.getString("itemId").toLowerCase().contains(q.toLowerCase())) {
                                    itemListArray.add(new Container(doc.getString("itemId"), doc.getString("itemName"), doc.getDouble("cost"), doc.getString("itemDetails"),
                                            doc.getString("category"), doc.getId(), doc.getString("imageURL")));
                                    i++;
                                }
                            }

                            productLV = findViewById(R.id.productsLV);
                            productLV.setHasFixedSize(true);
                            productLayoutManager = new LinearLayoutManager(SelectProduct.this);
                            productsAdapter = new AdminProductsAdapter(itemListArray, SelectProduct.this);
                            productLV.setLayoutManager(productLayoutManager);
                            productLV.setAdapter(productsAdapter);
                        }
                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
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
        if(id==R.id.History){
            Intent in=new Intent(this,UserHistoryActivity.class);
            startActivity(in);
        }
        else if (id == R.id.Logout) {
            // Handle the accessories action
            Intent in=new Intent(this,MainActivity.class);
            startActivity(in);
        }
        else if(id==R.id.addedprod){
            Intent in=new Intent(this,AdminHistoryAprodActivity.class);
            startActivity(in);
        }
        else if(id==R.id.deletedprod){
            Intent in=new Intent(this,AdminHistoryDProdActivity.class);
            startActivity(in);
        }
        else if (id==R.id.addedpromo){
            Intent in=new Intent(this,AdminHistoryAPromoActivity.class);
            startActivity(in);
        }
        else if (id==R.id.deletedpromo){
            Intent in=new Intent(this,AdminHistoryDPromoActivity.class);
            startActivity(in);
        }
        else if (id==R.id.Inventory){
            Intent in=new Intent(this,AdminInventory.class);
            startActivity(in);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
