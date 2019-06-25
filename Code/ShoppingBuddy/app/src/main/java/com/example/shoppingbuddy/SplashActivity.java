package com.example.shoppingbuddy;

import android.content.Intent;
import android.os.SystemClock;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SystemClock.sleep(1500);
        Intent login = new Intent(SplashActivity.this,MainActivity.class);
        startActivity(login);
        finish();
    }
}
