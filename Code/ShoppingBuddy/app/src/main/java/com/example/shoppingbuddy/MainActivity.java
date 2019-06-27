package com.example.shoppingbuddy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public Button btn;
    public TextView tv;
    public EditText username;
    public EditText email;
    public EditText password;
    public EditText repwd;
    public EditText phonenumber;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        username = findViewById(R.id.editText);
        email = findViewById(R.id.editText2);
        phonenumber = findViewById(R.id.editText8);
        password = findViewById(R.id.editText4);
        repwd = findViewById(R.id.editText5);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAct();

            }
        });
        tv = (TextView) findViewById(R.id.textView9);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SigninActivity.class);
                Log.d("click", "button clicked");
                startActivity(i);
            }
        });
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
        getMenuInflater().inflate(R.menu.main, menu);
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
//        int id = item.getItemId();
//
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {

//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void CreateAct() {
        String uname = username.getText().toString();
        String em = email.getText().toString();
        String pw = password.getText().toString();
        String repw = repwd.getText().toString();
        String phone=phonenumber.getText().toString();
        //String MobilePattern = "[0-9]{10}";
        //int phone = Integer.parseInt(phno.getText().toString());
        if ((TextUtils.isEmpty(uname)) && (TextUtils.isEmpty(em)) && (TextUtils.isEmpty(phone)) && (TextUtils.isEmpty(pw)) && (TextUtils.isEmpty(em))) {
           // Toast.makeText(this, "All fields must be filled", Toast.LENGTH_LONG).show();
            username.setError("user Name should not be empty");
            email.setError("Email should not be empty");
            phonenumber.setError("Phone number should not be empty");
            password.setError("Password should not be empty");
            repwd.setError("Re-Password should not be empty");
        } else {
         if (TextUtils.isEmpty(uname)) {
                username.setError("user Name should not be empty");
                //Toast.makeText(this, "User Name should not be empty", Toast.LENGTH_LONG).show();

            } else if (TextUtils.isEmpty(em)) {
                email.setError("Email should not be empty");
                //Toast.makeText(this, "Email should not be empty", Toast.LENGTH_LONG).show();

            } else if (!em.contains("@") || !em.contains(".")) {
                email.setError("Enter a valid email address");
                //Toast.makeText(this, "Enter a valid email address", Toast.LENGTH_LONG).show();
            } else if (TextUtils.isEmpty(phone)) {
                phonenumber.setError("Phone number should not be empty");
                // Toast.makeText(this, "Phone number should not be empty", Toast.LENGTH_LONG).show();

            } else if (TextUtils.isEmpty(pw)) {
                password.setError("Password should not be empty");
                // Toast.makeText(this, "Password should not be empty", Toast.LENGTH_LONG).show();

            } else if (TextUtils.isEmpty(repw)) {
                repwd.setError("Re-Password should not be empty");
                // Toast.makeText(this, "Re-Password should not be empty", Toast.LENGTH_LONG).show();

            } else if (pw.length() < 8) {
                password.setError("Password should have a minimum length of 8 characters");
                // Toast.makeText(this, "Password should have a minimum length of 8 characters", Toast.LENGTH_LONG).show();

            } else if (!(pw.equals(repw))) {
                repwd.setError("Password and Confirm Passwords does not match");
                //Toast.makeText(this,"Password and Confirm Passwords does not match",Toast.LENGTH_LONG).show();

            } else if (!(phone.length() == 10)) {
                phonenumber.setError("Please enter valid 10 digit phone number");
                //Toast.makeText(this,"Please enter valid 10 digit phone number", Toast.LENGTH_LONG).show();

            } else {
                progressDialog.setMessage("Registering user...");
                progressDialog.show();
                firebaseAuth.createUserWithEmailAndPassword(em, pw).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(MainActivity.this, Home.class);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(MainActivity.this, "Could not register please try again", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                );

            }
        }
    }
}