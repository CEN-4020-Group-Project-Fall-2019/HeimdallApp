package com.example.heimdall;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

public class AddCard extends AppCompatActivity {
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
        context = getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        //Setting up variables for later use
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getUid();
        findViewById(R.id.addToWatchlist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastMessage = "Stock list not loaded yet! Please wait.";
                Toast toast = Toast.makeText(context, toastMessage, duration);
                toast.show();
            }
        });

        //Load in all the entries in the stocks database
        //whenever the page loads
        loadValues();
    }

    public void loadValues(){
        //Get the database reference and access through
        //event listeners
        final DatabaseReference stocksRef = database.getReference("stocks");
        stocksRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> datas = dataSnapshot.getChildren();
                String stk = "";

                //Iterate through each child and get the stock name
                //If the stock name has not been added yet, then add
                //the stock
                for (Iterator<DataSnapshot> itr = datas.iterator(); itr.hasNext(); ) {
                    stk = itr.next().getKey();
                    if (!dbStocks.contains(stk)) {
                        dbStocks.add(stk);
                    }
                }

                //This code controls the add to watchlist button
                //You aren't allowed to add a stock until the db
                //completely loads, which this enforces
                findViewById(R.id.addToWatchlist).setAlpha(1);

                //Now the button will call the add function whenever
                //it is clicked
                findViewById(R.id.addToWatchlist).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addStockCard(getCurrentFocus());
                    }
                });

                //Tell the user that they can add stocks to watchlist now
                toastMessage = "Database finished loading. Feel free to enter values now.";
                Toast toast = Toast.makeText(context, toastMessage, duration);
                toast.show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });


    }

    //add function that calls info from Heimdallcen4020 and puts stock into UserDB: watchlist
    public void addStockCard(View view){
        //Setting up variables
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        TextView userEntry = findViewById(R.id.followStock);
        stockToFind = userEntry.getText().toString().toUpperCase();

        //This checks whether or not the user entered stock symbol
        //is in the database. If it is, then it is added to the
        //user's watchlist, if not, then the user is told
        if(dbStocks.contains(stockToFind)){
            wlRef.child(currentUser).child("Watchlist").child(stockToFind).setValue("stck added");
            toastMessage = stockToFind + " added successfully!";
            Toast toast = Toast.makeText(context, toastMessage, duration);
            toast.show();
        }else{
            toastMessage = stockToFind + " not found!";
            Toast toast = Toast.makeText(context, toastMessage, duration);
            toast.show();
        }

    }

    public void backScreen(View view){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}
