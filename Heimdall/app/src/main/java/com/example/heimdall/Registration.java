package com.example.heimdall;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase dbConnection;
    private DatabaseReference dbRef;
    CharSequence toastMessage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();
        dbConnection = FirebaseDatabase.getInstance();
        dbRef = dbConnection.getReference("watchList");

    }

    public void createUser(View view){
        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_LONG;
        final TextView em = findViewById(R.id.email);
        TextView ps = findViewById(R.id.password);
        final Intent intent = new Intent(this, Home.class);

        //Make sure the user entered values into email and password
        if(em.getText().length() > 0 && ps.getText().length() > 0) {
            final CharSequence email = em.getText();
            final CharSequence password = ps.getText();

            //Main register functionality, just uses fireAuth's functions
            mAuth.createUserWithEmailAndPassword(email.toString(), password.toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                toastMessage = "Account creation successful!";
                                Toast toast = Toast.makeText(context, toastMessage, duration);
                                toast.show();

                                //Creating user's watchlist
                                dbRef.child(mAuth.getUid()).setValue("Watchlist");
                                //  This line is how you add a hierarchy. access a child through parent and set the value
                                //dbRef.child(mAuth.getUid()).child("Watchlist").child("AAON").setValue("Twitter");

                                //Sign the user in and bring them to homescreen
                                mAuth.signInWithEmailAndPassword(email.toString(), password.toString());
                                startActivity(intent);

                            } else {
                                // If sign in fails, display a message to the user.
                                toastMessage = "Account not created. Email may already be in use or password is too short.";
                                Toast toast = Toast.makeText(context, toastMessage, duration);
                                toast.show();
                            }
                        }
                    });
        }else{
            toastMessage = "Provide email & password. Password must be > 5 characters.";
            Toast toast = Toast.makeText(context, toastMessage, duration);
            toast.show();

        }


    }

    public void backScreen(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
