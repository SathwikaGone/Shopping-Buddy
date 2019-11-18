package com.example.shoppingbuddy;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.common.util.Strings;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ShippingAddressActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView productLV;
    private RecyclerView.Adapter productsAdapter;
    private RecyclerView.LayoutManager productLayoutManager;
    private FirebaseFirestore db;
    private StorageReference StorageRef;
    private CollectionReference shippingCollection, cartCollection,promoCollection;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    double totalcost;

    private String fname,lname,address,city,state,zipcode,promocode, orderId="";

    EditText fname1,lname1,address1,city1,state1,zipcode1,promocode1;
    TextView totcost;
    Button paybutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_address);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fname1=findViewById(R.id.editText10);
        lname1=findViewById(R.id.editText11);
        address1=findViewById(R.id.editText14);
        city1=findViewById(R.id.editText16);
        state1=findViewById(R.id.editText17);
        zipcode1=findViewById(R.id.editText15);
        db = FirebaseFirestore.getInstance();
        promoCollection=db.collection("promocode");
        totcost=findViewById(R.id.textView40);

        db = FirebaseFirestore.getInstance();
        Intent i=getIntent();
        totalcost=i.getDoubleExtra("total cost",0.0);
        
        shippingCollection=db.collection("shippingAddress");
        FirebaseAuth auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        Button shipadd= findViewById(R.id.button17);
        shipadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname=fname1.getText().toString();
                lname=lname1.getText().toString();
                address=address1.getText().toString();
                city=city1.getText().toString();
                state=state1.getText().toString();
                zipcode=zipcode1.getText().toString();
                orderId = lname + Math.random();
                shippingCollection.orderBy("orderId", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (final QueryDocumentSnapshot doc : task.getResult()) {

                                if (doc.getString("orderId").equals("")) {
                                    orderId = lname + Math.random();
                                } else if (doc.getString("orderId").equals(orderId)) {
                                    orderId = lname + Math.random();
                                } else {
                                    orderId = lname + Math.random();
                                }
                            }
                        }
                    }
                });

                if(fname.equals("")||lname.equals("")||address.equals("") || city.equals("") || state.equals("") || zipcode.equals("")){
                    Toast.makeText(ShippingAddressActivity.this, "All the field needs to be filled ", Toast.LENGTH_SHORT).show();
                }
                else {
                    Map<String, Object> addproduct = new HashMap<>();
                    addproduct.put("User",user);
                    addproduct.put("OrderId", orderId );
                    addproduct.put("First Name", fname);
                    addproduct.put("Last Name", lname);
                    addproduct.put("Address", address);
                    addproduct.put("City", city);
                    addproduct.put("State", state);
                    addproduct.put("Zipcode", zipcode);
                    shippingCollection.document().set(addproduct);
                    Toast.makeText(ShippingAddressActivity.this, "Order", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent i = new Intent(ShippingAddressActivity.this, PaymentActivity.class);
                    i.putExtra("total cost",totalcost);
                    i.putExtra("orderid",orderId);
                    startActivity(i);


                }

            }
        });




        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
//        FirebaseAuth auth1 = FirebaseAuth.getInstance();
//        FirebaseUser user = auth1.getCurrentUser();
//        View headerView = navigationView.getHeaderView(0);
//        TextView navUsername = (TextView) headerView.findViewById(R.id.textView);
//        navUsername.setText(user.getEmail());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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

        if (id == R.id.logout) {
            // Handle the accessories action
            Intent in=new Intent(this,MainActivity.class);
            startActivity(in);
        }
        else if (id == R.id.home) {
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
        else if(id==R.id.orders){
            Intent in=new Intent(this,Orders_Product_List.class);
            startActivity(in);
        }

//        if (id == R.id.nav_home) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_tools) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
