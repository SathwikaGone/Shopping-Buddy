package com.example.shoppingbuddy;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import android.util.Log;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
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

public class AdminSettingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

 private EditText password;
        private TextView email;
        private String pass;
        private Button update;
        FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        user = FirebaseAuth.getInstance().getCurrentUser();

        email =findViewById(R.id.textView65);
        email.setText(user.getEmail());
        password =findViewById(R.id.editText29);
        update = findViewById(R.id.button23);

  update.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          pass = password.getText().toString();
                                          user.updatePassword(pass).addOnCompleteListener(new OnCompleteListener<Void>() {

                                              @Override
                                              public void onComplete(@NonNull Task<Void> task) {
                                                  if (task.isSuccessful()) {
                                                      Toast.makeText(AdminSettingActivity.this, "Password updated", Toast.LENGTH_SHORT).show();
                                                      Log.d("success", "password updated");
                                                      Intent i = new Intent(AdminSettingActivity.this, SigninActivity.class);
                                                      startActivity(i);

                                                  } else {
                                                      Toast.makeText(AdminSettingActivity.this, "Password not updated", Toast.LENGTH_SHORT).show();
                                                  }
                                              }

                                          });
                                      }
                                  });



 //       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

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
            Intent in=new Intent(AdminSettingActivity.this,AdminSettingActivity.class);
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
        }else if (id==R.id.Home){
            Intent in=new Intent(this, AdminHomeActivity.class);
            startActivity(in);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
