package com.example.heimdall;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.support.v4.view.PagerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
//import com.example.add_twiter_follow.ui.main.SectionsPagerAdapter;

public class Settings extends AppCompatActivity {
    private FirebaseAuth mAuth;
    CharSequence toastMessage = "";
    Context context;
    int duration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mAuth = FirebaseAuth.getInstance();
        TextView newText = findViewById(R.id.textView6);
        newText.setText(mAuth.getCurrentUser().getEmail());
    }

    public void signOut(View view){
        mAuth.signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void toRecPage(View view){
        Intent intent = new Intent(this, Recommendation.class);
        startActivity(intent);
    }

    //Added to keep some of Jacob's functionality
    public void toHome(View view){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void resetPassword(View view){
        context = getApplicationContext();
        duration = Toast.LENGTH_LONG;
        TextView getEntry = findViewById(R.id.editText2);

        mAuth.getCurrentUser().updatePassword(getEntry.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    toastMessage = "Password changed successfully!";
                    Toast toast = Toast.makeText(context, toastMessage, duration);
                    toast.show();
                } else {
                    toastMessage = "Password unable to be changed";
                    Toast toast = Toast.makeText(context, toastMessage, duration);
                    toast.show();
                }
            }
        });


    }


}