package com.example.heimdall;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    CharSequence toastMessage = "";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        mAuth = FirebaseAuth.getInstance();
    }

    public void backScreen(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void resetPassword(View view){
        TextView em = findViewById(R.id.PWRemail);
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;

        if(em.getText().length() > 0) {
            String userEmail = em.getText().toString();
            toastMessage = "Email sent successfully (if user exists).";
            Toast toast = Toast.makeText(context, toastMessage, duration);
            toast.show();
            mAuth.sendPasswordResetEmail(userEmail);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else{
            toastMessage = "Provide an email.";
            Toast toast = Toast.makeText(context, toastMessage, duration);
            toast.show();
        }
    }
}
