package com.example.heimdall;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();
        dbConnection = FirebaseDatabase.getInstance();
        dbRef = dbConnection.getReference("users");

    }

    public void createUser(View view){

        final TextView em = findViewById(R.id.email);
        TextView ps = findViewById(R.id.password);


        if(em.getText().length() > 0 && ps.getText().length() > 0) {
            final CharSequence email = em.getText();
            CharSequence password = ps.getText();

            mAuth.createUserWithEmailAndPassword(email.toString(), password.toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("HEIMDALL", "createUserWithEmail:success");
                                dbRef.child(mAuth.getUid()).setValue("Watchlist");
                                dbRef.child(mAuth.getUid()).child("Watchlist").child("Stock1").setValue("Twitter");

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("HEIMDALL", "createUserWithEmail:failure", task.getException());

                            }
                        }
                    });
        }else{
            Log.d("HEIMDALL", "email or password not supplied");

        }


    }

    public void backScreen(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
