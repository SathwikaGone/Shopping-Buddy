package com.example.shoppingbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PaymentActivity extends AppCompatActivity {

    EditText cardno, CVV,date;
    String card,cvv,d;
    Button pay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Intent in=getIntent();
        double totcost=in.getDoubleExtra("total cost",0.0);
        TextView tv=findViewById(R.id.textView49);
        tv.setText("$"+totcost);
        cardno=findViewById(R.id.editText20);
        CVV=findViewById(R.id.editText21);
        date=findViewById(R.id.editText22);
        pay=findViewById(R.id.button19);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card=cardno.getText().toString();
                cvv=CVV.getText().toString();
                d=date.getText().toString();
                if(card.isEmpty()&& cvv.isEmpty()&&d.isEmpty()){

                    cardno.setError("All fields must be filled");
                    CVV.setError("All fields must be filled");
                    date.setError("All fields must be filled");
                }
                else if(card.isEmpty()){
                    cardno.setError("Card number must be filled");
                }
                else if(cvv.isEmpty()){
                    CVV.setError("CVV must be filled");
                }
                else if(d.isEmpty()){
                    date.setError("Date must be filled");
                }
                else if(!(card.length()==16)){
                    cardno.setError("Incorrect card number");
                }
                else if(!(cvv.length()==3)){
                    CVV.setError("Incorrect CVV number");
                }

            }
        });

    }
}
