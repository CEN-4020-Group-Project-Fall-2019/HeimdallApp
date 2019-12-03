package com.example.heimdall;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
    }

    public void resetPassword(View view){
        Intent intent = new Intent(this, ForgotPassword.class);
        startActivity(intent);
    }

    public void signIn(View view){
        final Intent intent = new Intent(this, Home.class);
        TextView em = findViewById(R.id.FPuname);
        TextView pw = findViewById(R.id.FPpw);
        String email = em.getText().toString();
        String password = pw.getText().toString();

        if(email.length() > 0 && password.length() > 0) {
            Log.d("HEIMDALL", email + "\t\t" + password);
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("HEIMDALL", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                //TODO: create entry in users DB with currentUser's UID
                                final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("users");
                                myRef.setValue(user.getUid());
                                ArrayList<Map<String, ArrayList<String>>> watchList = new ArrayList<>();
                                myRef.child(user.getUid()).setValue(watchList);

                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("HEIMDALL", "signInWithEmail:failure", task.getException());
                            }

                            // ...
                        }
                    });
        }else{
            Log.d("HEIMDALL", "email or password not supplied");

        }

    }

    public void register(View view){
        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);
    }
}
