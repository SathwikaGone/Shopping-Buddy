package com.example.shoppingbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    public Button btn;
    private FirebaseAuth firebaseAuth;
    public EditText email;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Intent ini=getIntent();
        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        email=(EditText)findViewById(R.id.editText8);
        btn=(Button)findViewById(R.id.button4);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAcc();

            }
        });
    }
    private void loginAcc(){
        String em=email.getText().toString().trim();
        if(TextUtils.isEmpty(em)){
            Toast.makeText(this,"Email should not be empty",Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage(" Sending Email ...");
        progressDialog.show();
        firebaseAuth.sendPasswordResetEmail(em).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ForgotPassword.this, "Email sent sucessful", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ForgotPassword.this, SigninActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(ForgotPassword.this, "Login failed", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
       
    }
}


