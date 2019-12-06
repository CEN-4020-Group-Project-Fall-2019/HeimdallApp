package com.example.heimdall;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
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
    CharSequence toastMessage = "";
    private FirebaseAuth mAuth;
    private String CHANNEL_ID = "HEIMDALL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        createNotificationChannel(CHANNEL_ID);
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
        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_LONG;

        //Main login functionality, just checks for user
        //entries and checks with db if they're right.
        //Also provides some error checking popups for user
        if(email.length() > 0 && password.length() > 0) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                toastMessage = "Sign in successful!";
                                Toast toast = Toast.makeText(context, toastMessage, duration);
                                toast.show();

                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                toastMessage = "Sign in unsuccessful!";
                                Toast toast = Toast.makeText(context, toastMessage, duration);
                                toast.show();
                            }

                            // ...
                        }
                    });
        }else{
            toastMessage = "Sign in unsuccessful! Provide email & password.";
            Toast toast = Toast.makeText(context, toastMessage, duration);
            toast.show();

        }

    }

    public void register(View view){
        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);
    }

    //must create channel before send notifications, so creating as soon as app opens
    //not 100% on what CHANNEL_ID is supposed to be...but constructor on documentation showed as a string. So made a string with random name
    private void createNotificationChannel(String ch_id) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(ch_id, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
