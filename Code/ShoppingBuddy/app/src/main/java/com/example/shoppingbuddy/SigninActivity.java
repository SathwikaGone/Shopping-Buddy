package com.example.shoppingbuddy;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class SigninActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public Button btn;
    public Button btn1;
    public EditText email;
    public EditText password;
    private CheckBox saveLoginCheckBox;
    private SharedPreferences rememberMe;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    public TextView forgotpassword;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        rememberMe = this.getPreferences(MODE_PRIVATE);

        Intent i=getIntent();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        email=findViewById(R.id.editText3);
        password=findViewById(R.id.editText7);
        saveLoginCheckBox = (CheckBox)findViewById(R.id.saveLoginCheckBox);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        btn=(Button)findViewById(R.id.button3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SigninActivity.this,MainActivity.class);
                Log.d("click","button clicked");
                startActivity(i);
            }
        });
        btn1=(Button)findViewById(R.id.button2);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAcc();

            }
        });
        forgotpassword=(TextView)findViewById(R.id.textView23);
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SigninActivity.this,ForgotPassword.class);
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
        getMenuInflater().inflate(R.menu.signin, menu);
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
//
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void loginAcc(){
        String em=email.getText().toString().trim();
        String pwd=password.getText().toString().trim();
        if(TextUtils.isEmpty(em)){
            email.setError("Email should not be empty");
            //Toast.makeText(this,"Email should not be empty",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(pwd)){
            password.setError("password should not be empty");
           // Toast.makeText(this,"password should not be empty",Toast.LENGTH_LONG).show();
            return;
        }

        if(em.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
            if(pwd.length()>=8) {
                firebaseAuth.signInWithEmailAndPassword(em, pwd)
                        .addOnCompleteListener(SigninActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    user = firebaseAuth.getCurrentUser();
                                    if(saveLoginCheckBox.isChecked()){

                                        SharedPreferences.Editor  editor = rememberMe.edit();
                                        editor.putString("userName", String.valueOf(email));
                                        editor.putString("password", String.valueOf(password));
                                        editor.commit();
                                    }

                                    if (user.isEmailVerified()) {
                                        progressDialog.setMessage("Logging in...");
                                        progressDialog.show();
                                        Toast.makeText(SigninActivity.this, "Login sucessful", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(SigninActivity.this, Home.class);
                                        startActivity(i);
                                    }
                                    else {
                                        //Toast.makeText(SigninActivity.this, "User email is not verified", Toast.LENGTH_SHORT).show();
                                        AlertDialog.Builder builder=new AlertDialog.Builder(SigninActivity.this);
                                        builder.setMessage("User Email not verified").setCancelable(false);
                                        builder.setPositiveButton("send email verification again", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(final DialogInterface dialog, int which) {
                                                user.sendEmailVerification()
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Toast.makeText(SigninActivity.this,"Email sent",Toast.LENGTH_LONG).show();
                                                                }
                                                                else{
                                                                    Toast.makeText(SigninActivity.this,"Problem in sending email verification please try again",Toast.LENGTH_LONG).show();
                                                                }
                                                               //dialog.cancel();

                                                             }
                                                });

                                    }
                                        });
                                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                        AlertDialog alert=builder.create();
                                        alert.setTitle("Email verification");
                                        alert.show();
                                    }
                                } else {
                                    Toast.makeText(SigninActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        });
            }
            else{
                Toast.makeText(this,"Password limit not reached",Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(this,"Email pattern not matched",Toast.LENGTH_LONG).show();
        }
    }
}
