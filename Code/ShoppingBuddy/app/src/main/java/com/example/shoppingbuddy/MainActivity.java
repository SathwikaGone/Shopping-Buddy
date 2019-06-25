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
    public EditText phno;
    public EditText password;
    public EditText repwd;

private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

firebaseAuth= FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        username=findViewById(R.id.editText);
        email=findViewById(R.id.editText2);
        phno=findViewById(R.id.editText6);
        password=findViewById(R.id.editText4);
        repwd=findViewById(R.id.editText5);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        btn=(Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               CreateAct();
                Intent i=new Intent(MainActivity.this,SigninActivity.class);
                Log.d("click","button clicked");
                startActivity(i);
            }
        });
        tv=(TextView) findViewById(R.id.textView9);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,SigninActivity.class);
                Log.d("click","button clicked");
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

private void CreateAct(){
        String uname=username.getText().toString().trim();
        String em=email.getText().toString().trim();
        String pw=password.getText().toString().trim();
        String repw= repwd.getText().toString().trim();
        String phn=phno.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        int phone=Integer.parseInt(phno.getText().toString().trim());
        if(TextUtils.isEmpty(uname)){
            Toast.makeText(this,"User Name should not be empty",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(em)){
            Toast.makeText(this,"Email should not be empty",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(phn)){
            Toast.makeText(this,"Phone number should not be empty",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(pw)){
            Toast.makeText(this,"Password should not be empty",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(repw)){
            Toast.makeText(this,"Re-Password should not be empty",Toast.LENGTH_LONG).show();
            return;
        }
        //final EditText emailValidate = (EditText)findViewById(R.id.textMessage);

        //final TextView textView = (TextView)findViewById(R.id.text);

        //String email = emailValidate.getText().toString().trim();

        if(!pw.equals(repw)) {
            Toast.makeText(this, "Password and Confirm Password does not match", Toast.LENGTH_LONG).show();
            return;
        }
        if(pw.length() < 8){
            Toast.makeText(this,"Passwod should have a minimum length of 8 characters", Toast.LENGTH_LONG).show();
            return;
        }
        // onClick of button perform this simplest code.
        if (em.matches(emailPattern))
        {
            Toast.makeText(this,"valid email address",Toast.LENGTH_SHORT).show();
            return;
        }
        if (!em.matches(emailPattern)){
            Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Registering user...");
        progressDialog.show();
    firebaseAuth.createUserWithEmailAndPassword(em,pw).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(MainActivity.this,"Registered successfully",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(MainActivity.this,"Could not register please try again",Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );
        
    }
}
