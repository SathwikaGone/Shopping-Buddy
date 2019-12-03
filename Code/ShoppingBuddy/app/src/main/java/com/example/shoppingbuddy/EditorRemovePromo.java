package com.example.shoppingbuddy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class EditorRemovePromo extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
        TextView promoid;
        EditText promocode, cost, description;
        private FirebaseFirestore db;
        String pid, pcode, pcost, docid,pdes,deletePromo;
    private Button saveChangesBTN, cancelBTN, removeBTN;
    private DocumentReference itemDoc;
    private CollectionReference deletedPromo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_remove_promo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        promoid=findViewById(R.id.textView58);
        promocode=findViewById(R.id.editText26);
        cost=findViewById(R.id.editText30);
        description=findViewById(R.id.editText27);

        db = FirebaseFirestore.getInstance();
        deletedPromo=db.collection("deletedPromo");
        saveChangesBTN=findViewById(R.id.button12);
        removeBTN=findViewById(R.id.button15);
        cancelBTN=findViewById(R.id.button14);

//      Get data from the Intent
        Intent i = getIntent();
        pid = i.getStringExtra("promoId");
        pcode = i.getStringExtra("promocode");
        pcost = i.getStringExtra("price");
        docid=i.getStringExtra("documentId");
        pdes=i.getStringExtra("des");
        deletePromo=i.getStringExtra("promocode");
        promoid.setText(pid);
        promocode.setText(pcode);
        cost.setText(pcost);
        description.setText(pdes);
        itemDoc = db.collection("promocode").document(docid);
        saveChangesBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pcode = promocode.getText().toString();
                pcost = cost.getText().toString();
                pdes=description.getText().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(EditorRemovePromo.this);
                builder.setMessage("Are you sure you want to save the changes").setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        itemDoc.update("PromoCode", pcode, "amount to dedcut", pcost,"description",pdes);
                        Toast.makeText(EditorRemovePromo.this, "Promocode updated successfully", Toast.LENGTH_LONG).show();
                        Intent j = new Intent(EditorRemovePromo.this, AdminHomeActivity.class);
                        j.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(j);
                        EditorRemovePromo.this.finish();

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
                Intent i = new Intent(EditorRemovePromo.this, SelectPromocodeActivity.class);
                startActivity(i);
            }
        });
        removeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditorRemovePromo.this);
                builder.setMessage("Are you sure you want to delete the promocode").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Map<String, Object> deletepromo = new HashMap<>();
                        deletepromo.put("itemId",pid);
                        deletepromo.put("itemName", deletePromo);
                        deletepromo.put("cost", pcost);
                        deletepromo.put("description",pdes);
                        deletedPromo.document().set(deletepromo);
                        itemDoc.delete();
                        finish();
                        Toast.makeText(EditorRemovePromo.this, "Promocode deleted successfully", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(EditorRemovePromo.this, AdminHomeActivity.class);
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
        if(id==R.id.History){
            Intent in=new Intent(this, UserHistoryActivity.class);
            startActivity(in);
        }
        else if (id == R.id.Logout) {
            // Handle the accessories action
            Intent in=new Intent(this, MainActivity.class);
            startActivity(in);
        }
        else if(id==R.id.addedprod){
            Intent in=new Intent(this, AdminHistoryAprodActivity.class);
            startActivity(in);
        }
        else if(id==R.id.deletedprod){
            Intent in=new Intent(this, AdminHistoryDProdActivity.class);
            startActivity(in);
        }
        else if (id==R.id.addedpromo){
            Intent in=new Intent(this, AdminHistoryAPromoActivity.class);
            startActivity(in);
        }
        else if (id==R.id.deletedpromo){
            Intent in=new Intent(this, AdminHistoryDPromoActivity.class);
            startActivity(in);
        }
        else if (id==R.id.Inventory){
            Intent in=new Intent(this, AdminInventory.class);
            startActivity(in);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
