package com.example.shoppingbuddy;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UserChatActivity extends AppCompatActivity
         {

    private EditText message;
    private ImageButton sendmsg;
    private FirebaseFirestore db;
    private CollectionReference chatCollection;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private String email, msg;
    private RecyclerView productLV;
    private RecyclerView.Adapter productsAdapter;
    private RecyclerView.LayoutManager productLayoutManager;

     Timestamp d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        d= new Timestamp(new Date());
        message= findViewById(R.id.message);
        sendmsg=findViewById(R.id.send);
//

        db = FirebaseFirestore.getInstance();
        chatCollection = db.collection("chat");
        auth= FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        email=user.getEmail();



        sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg=message.getText().toString();
                    Map<String, Object> messages = new HashMap<>();
                    messages.put("From", email);
                    messages.put("To", "shoppingbuddyseven@gmail.com");
                    messages.put("Message", msg);
                    messages.put("Date",new Timestamp(new Date()) );

                    chatCollection.document().set(messages);
                    Toast.makeText(UserChatActivity.this, "Message sent", Toast.LENGTH_SHORT).show();
                    message.setText("");


            }
        });

        final ArrayList<Container> itemListArray = new ArrayList<>();

        chatCollection.orderBy("Date",Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                final ArrayList<Container> itemListArray = new ArrayList<>();
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {

                    if (doc.getString("From").equals(email) || doc.getString("To").equals(email)) {
                        //  if(doc.getDate("Date").after(d.toDate())){
                        itemListArray.add(new Container(doc.getId(),doc.getString("Message"), doc.getString("From"),doc.getString("To")));
                        //  }


                    }
                }
                productLV = findViewById(R.id.recyclerview);
                productLV.setHasFixedSize(true);
                productLayoutManager = new LinearLayoutManager(UserChatActivity.this);
                productsAdapter = new ChatAdapter(itemListArray, UserChatActivity.this);
                productLV.setLayoutManager(productLayoutManager);
                productLV.setAdapter(productsAdapter);
                productLV.scrollToPosition(itemListArray.size()-1);
            }
        });


//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//FirebaseAuth auth = FirebaseAuth.getInstance();
//        FirebaseUser user = auth.getCurrentUser();
//        View headerView = navigationView.getHeaderView(0);
//        TextView navUsername = (TextView) headerView.findViewById(R.id.textView);
//        navUsername.setText(user.getEmail());
//        navigationView.setNavigationItemSelectedListener(this);
    }

//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }

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
            Intent i = new Intent(this, CartActivity.class);
            startActivity(i);
        }
        if (id == R.id.action_chat) {
            Intent i = new Intent(this,UserChatActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

   // @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.home) {
//            // Handle the home action
//            Intent in=new Intent(this,Home.class);
//            startActivity(in);}
//        else if (id == R.id.electronics) {
//            Intent in=new Intent(this,ElectronicsActivity.class);
//            startActivity(in);}
//        else if (id == R.id.clothing) {
//            // Handle the accessories action
//            Intent in=new Intent(this,ClothingActivity.class);
//            startActivity(in);
//        }
//        else if (id == R.id.accessories) {
//            // Handle the accessories action
//            Intent in=new Intent(this,AccessoriesActivity.class);
//            startActivity(in);
//        }
//        else if (id == R.id.footwear) {
//            // Handle the accessories action
//            Intent in=new Intent(this,Footwear.class);
//            startActivity(in);
//        }
//        else if (id == R.id.logout) {
//            // Handle the accessories action
//            Intent in=new Intent(this,MainActivity.class);
//            startActivity(in);
//        }
//        else if(id==R.id.orders){
//            Intent in=new Intent(this,Orders_Product_List.class);
//            startActivity(in);
//        }
//        else if (id == R.id.settings) {
//            // Handle the accessories action
//            Intent in = new Intent(this, Settings.class);
//            startActivity(in);
//        }
//
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
}
