package com.example.shoppingbuddy;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
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

import java.util.ArrayList;

public class AdminHistoryAPromoActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView productLV;
    private RecyclerView.Adapter promoAdapter;
    private RecyclerView.LayoutManager promoLayoutManager;

    private FirebaseFirestore db;
    private StorageReference StorageRef;
    private CollectionReference promoCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_history_apromo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = FirebaseFirestore.getInstance();
        promoCollection = db.collection("promocode");
        final ArrayList<Container> itemListArray = new ArrayList<>();
        promoCollection.orderBy("PromoId", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int i = 0;
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        itemListArray.add(new Container(doc.getString("PromoId"), doc.getString("PromoCode"), doc.getString("amount to dedcut"), doc.getId(),doc.getString("description")));
                        i++;
                    }

                    productLV = findViewById(R.id.productsLV);
                    productLV.setHasFixedSize(true);
                    promoLayoutManager = new LinearLayoutManager(AdminHistoryAPromoActivity.this);
                    promoAdapter = new AdminPromoAdapter(itemListArray, AdminHistoryAPromoActivity.this);
                    productLV.setLayoutManager(promoLayoutManager);
                    productLV.setAdapter(promoAdapter);
                }
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
            Intent in=new Intent(this,AdminSettingActivity.class);
            startActivity(in);
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
        }else if (id==R.id.Home){
            Intent in=new Intent(this,AdminHomeActivity.class);
            startActivity(in);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
