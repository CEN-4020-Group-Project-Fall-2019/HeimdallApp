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
    boolean foundStock = false;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference wlRef= database.getReference("watchList");

    String stockToFind = "";
    String currentUser = "";
    Vector<String> dbStocks = new Vector<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getUid();
        loadValues();

    }

    public void loadValues(){
        final DatabaseReference stocksRef = database.getReference("stocks");
        stocksRef.keepSynced(true);
        stocksRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String stk = "";
                for (Iterator<DataSnapshot> itr = dataSnapshot.getChildren().iterator(); itr.hasNext();) {
                    stk = itr.next().getKey();
                    if (!dbStocks.contains(stk)) {
                        dbStocks.add(stk);
                    }
                }
                Log.d("HEIMDALL", "values" + dbStocks.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void backScreen(View view){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    //add function that calls info from Heimdallcen4020 and puts stock into UserDB: watchlist
    public void addStockCard(View view){
        final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        TextView userEntry = findViewById(R.id.followStock);
        stockToFind = userEntry.getText().toString();

        Log.d("HEIMDALL", "valuesPT2" + dbStocks.toString());

        if(dbStocks.contains(stockToFind)){
            wlRef.child(currentUser).child("Watchlist").child(stockToFind).setValue("stck added");
            foundStock = true;
        }else{
            Log.d("HEIMDALL", "fuuuuuuck");
        }

        if(foundStock) {
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        }
    /*    myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                EditText stk = findViewById(R.id.followStock);
                String stock = stk.getText().toString();
                ArrayList<String> twitL = new ArrayList<>();
                Object twit = twitL;
                Map<String, Object> map = new HashMap<>();
                map.put(stock, twit);
                myRef.updateChildren(map);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("HEIMDALL", "Failed to read value.", error.toException());
            }
        }); */



        /*myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                View row = inflater.inflate(R.layout.field, null);
                Iterable<DataSnapshot> datas = dataSnapshot.getChildren();
                String stk = "";

                int i = 197;
                Log.d("HEIMDALL", "num children " + dataSnapshot.getChildrenCount());
                Log.d("HEIMDALL", "IM HERE!!");

                for (Iterator<DataSnapshot> itr = datas.iterator(); itr.hasNext(); ) {
                    stk = itr.next().getKey();
                    TextView tmp = (TextView) row;
                    Log.d("HEIMDALL", "itr is at " + stk);
                    tmp.setId(i);
                    tmp.setText(stk + "(x.x%)");
                    parentLayout.addView(row);
                    row = inflater.inflate(R.layout.field, null);
                }

            }

            @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("HEIMDALL", "Failed to read value.", error.toException());
                }
        });*/
        }
    }
