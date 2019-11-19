package com.example.shoppingbuddy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class AdminPromoActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseFirestore db;
    private StorageReference storg;
    private CollectionReference itemCollection;
    private DocumentReference itemDoc;
    String id,promo,deductamount,des;
    EditText promoid,promocode,deductedamount,description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_promo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        promoid=findViewById(R.id.editText10);
        promocode=findViewById(R.id.editText11);
        deductedamount=findViewById(R.id.editText19);
        description=findViewById(R.id.editText28);
        Button addpromo=findViewById(R.id.button17);


        storg = FirebaseStorage.getInstance().getReference();
        db = FirebaseFirestore.getInstance();
        itemCollection = db.collection("promocode");


        addpromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id=promoid.getText().toString();
                promo=promocode.getText().toString();
                deductamount=deductedamount.getText().toString();
                des=description.getText().toString();

                if(id.equals("")||promo.equals("")||deductamount.equals("")||des.equals("")){
                    Toast.makeText(AdminPromoActivity.this, "All the field needs to be filled ", Toast.LENGTH_SHORT).show();
                }
                else {
                    Map<String, Object> addpromo = new HashMap<>();
                    addpromo.put("PromoId", id);
                    addpromo.put("PromoCode", promo);
                    addpromo.put("amount to dedcut",deductamount);
                    addpromo.put("description",des);

                    itemCollection.document().set(addpromo);
                    Toast.makeText(AdminPromoActivity.this, "Promo code added to the list", Toast.LENGTH_SHORT).show();

                    finish();


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
