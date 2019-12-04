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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.Vector;

public class AddTwitter extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference wlRef= database.getReference("watchList");
    CharSequence toastMessage = "";
    Context context;
    final int duration = Toast.LENGTH_LONG;
    String stockToFind = "";
    String currentUser = "";
    Vector<String> dbStocks = new Vector<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_twitter);

        stockToFind = (String) getIntent().getCharSequenceExtra("stkName");
        System.out.print(stockToFind);

        context = getApplicationContext();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getUid();
    }

    public void toAddTwitter(View view) {
        //go to addTwitter page
        Intent intent = new Intent(this, AddTwitter.class);
        startActivity(intent);
    }

    public void backScreen(View view){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void addTwitterTerm(View view){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        TextView userEntry = findViewById(R.id.followStock2);
        String twitterTerm = userEntry.getText().toString();

        wlRef.child(currentUser).child("Watchlist").child(stockToFind).child(twitterTerm).setValue("Twitter Term Added");
        toastMessage = twitterTerm + " added successfully!";
        Toast toast = Toast.makeText(context, toastMessage, duration);
        toast.show();

    }
}
