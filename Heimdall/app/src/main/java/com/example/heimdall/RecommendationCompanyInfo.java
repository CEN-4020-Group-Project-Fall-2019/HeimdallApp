package com.example.heimdall;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RecommendationCompanyInfo extends AppCompatActivity {
    private FirebaseDatabase db;
    private FirebaseAuth mAuth;
    private DatabaseReference wlRef;
    private String currentUser;
    private String stockName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation_company_info);
        stockName = (String) getIntent().getCharSequenceExtra("stkName");
        TextView title = findViewById(R.id.RecStockName);
        title.setText(stockName);
        final TextView stockPrice = findViewById(R.id.InfoRecStock);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
        db = FirebaseDatabase.getInstance();
        DatabaseReference stockRef = db.getReference("stocks");
        wlRef = db.getReference("watchList");
        stockRef.child(stockName).child("meta").child("regularMarketPrice").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //stockPrice.setText("Current: $" + dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void toRecScreen(View view){
        Intent intent = new Intent(this, Recommendation.class);
        startActivity(intent);
    }

    public void onFollow(View view){

    }
}
