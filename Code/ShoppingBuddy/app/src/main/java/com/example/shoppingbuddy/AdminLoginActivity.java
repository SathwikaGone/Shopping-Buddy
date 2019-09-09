package com.example.shoppingbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
    }
    public void signin(View v){
        EditText et=findViewById(R.id.editText3);
        EditText et1=findViewById(R.id.editText7);
        String un=et.getText().toString().trim();
        String pw=et1.getText().toString().trim();
        if(un=="admin" && pw=="admin"){
            Intent ini=new Intent(this,Home.class);
            startActivity(ini);
        }
        else{
            Toast.makeText(this,"login failed",Toast.LENGTH_LONG).show();
        }
    }
}
