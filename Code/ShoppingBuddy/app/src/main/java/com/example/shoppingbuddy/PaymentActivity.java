package com.example.shoppingbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Intent in=getIntent();
        double totcost=in.getDoubleExtra("total cost",0.0);
        TextView tv=findViewById(R.id.textView49);
        tv.setText("$"+totcost);
    }
}
