package com.example.shoppingbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity {

    EditText cardno, CVV,date;
    String card,cvv,d,orderid;
    Button pay;
    private CollectionReference orderCollection, cartCollection;
    private FirebaseFirestore db;
    private FirebaseUser user;
    double totcost;
    Intent in;
    DocumentReference itemDoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        in=getIntent();
        totcost=in.getDoubleExtra("total cost",0.0);
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
                else{
                    db = FirebaseFirestore.getInstance();
                    orderCollection=db.collection("orders");
                    cartCollection=db.collection("cart");
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    user = auth.getCurrentUser();
                    //Intent i=getIntent();
                    orderid=in.getStringExtra("orderid");
                    cartCollection.orderBy("itemId", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (final QueryDocumentSnapshot doc : task.getResult()) {
                                    if(user.getEmail().equals(doc.getString("user"))){
                                        Log.d("click","inside users verfication");
                                        Map<String, Object> addproduct = new HashMap<>();
                                        addproduct.put("OrderId",orderid);
                                        addproduct.put("user",user.getEmail());
                                        addproduct.put("productId",doc.getString("itemId"));
                                        addproduct.put("quantity",doc.getLong("quantity"));
                                        addproduct.put("size",doc.getString("size"));
                                        addproduct.put("total cost",totcost);
                                        orderCollection.document().set(addproduct);
                                        Toast.makeText(PaymentActivity.this, "check your orders", Toast.LENGTH_SHORT).show();
                                        //finish();
                                        itemDoc = db.collection("cart").document(doc.getId());
                                        itemDoc.delete();
                                        Log.d("itemclick",""+doc.getId());
                                        Intent in=new Intent(PaymentActivity.this,Home.class);
                                        startActivity(in);

                                    }
                                }


                            }
                        }
                    });



                }

            }
        });

    }
}
