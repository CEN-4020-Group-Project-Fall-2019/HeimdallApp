package com.example.heimdall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

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

        Log.d("HEIMDALL", "email: " + em.getText().toString());
        if(em.getText().length() > 0) {
            String userEmail = em.getText().toString();
            //TODO tell the user if email wasn't sent
            mAuth.sendPasswordResetEmail(userEmail);
        }else{
            Log.d("HEIMDALL", "no email provided");
        }
    }
}
