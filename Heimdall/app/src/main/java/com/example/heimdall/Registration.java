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

public class Registration extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();

    }

    public void createUser(View view){

        TextView em = findViewById(R.id.email);
        TextView ps = findViewById(R.id.password);


        if(em.getText().length() > 0 && ps.getText().length() > 0) {
            CharSequence email = em.getText();
            CharSequence password = ps.getText();

            Log.d("HEIMDALL", email.toString() + "\t" + password.toString());

            mAuth.createUserWithEmailAndPassword(email.toString(), password.toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("HEIMDALL", "createUserWithEmail:success");
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
