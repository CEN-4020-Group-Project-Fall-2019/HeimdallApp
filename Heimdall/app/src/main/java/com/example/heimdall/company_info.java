package com.example.heimdall;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.view.PagerAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Iterator;

//import com.example.add_twiter_follow.ui.main.SectionsPagerAdapter;

public class company_info extends AppCompatActivity {
    private FirebaseDatabase db;
    private FirebaseAuth mAuth;
    private LinearLayout parentLayout;
    private DatabaseReference wlRef;
    private String currentUser;
    private String stockName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_info);
        stockName = (String) getIntent().getCharSequenceExtra("stkName");
        TextView title = findViewById(R.id.textView9);
        title.setText(stockName);
        final TextView stockPrice = findViewById(R.id.Info);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
        parentLayout = findViewById(R.id.TwitterTerms);
        db = FirebaseDatabase.getInstance();
        DatabaseReference stockRef = db.getReference("stocks");
        wlRef = db.getReference("watchList");
        stockRef.child(stockName).child("meta").child("regularMarketPrice").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                stockPrice.setText("Current: $" + dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        wlRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                View row = inflater.inflate(R.layout.field2, parentLayout, false);
                Iterable<DataSnapshot> datas = dataSnapshot.child(currentUser).child("Watchlist").child(stockName).getChildren();

                String term = "";
                int i = 547;

                for (Iterator<DataSnapshot> itr = datas.iterator(); itr.hasNext(); ++i) {
                    term = itr.next().getKey();
                    TextView tmp = (TextView) row;
                    tmp.setText(term);
                    tmp.setId(i);

                    parentLayout.addView(tmp);
                    row = inflater.inflate(R.layout.field2, parentLayout, false);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Added to keep some of Jacob's functionality
    public void toHome(View view){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void deleteTerm(View view){
        final TextView callingObject = findViewById(view.getId());
        final String original = callingObject.getText().toString();
        callingObject.setText("Click again to delete or cancel using the button.");
        final Button cancelButton = findViewById(R.id.cancelDelete);
        cancelButton.setVisibility(View.VISIBLE);
        callingObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callingObject.setVisibility(View.GONE);
                wlRef.child(currentUser).child("Watchlist").child(stockName).child(original).removeValue();
                cancelButton.setVisibility(View.GONE);
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callingObject.setText(original);
                callingObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteTerm(v);
                    }
                });
                cancelButton.setVisibility(View.GONE);
            }
        });
    }

    public void removeStock(View view){

        wlRef.child(currentUser).child("Watchlist").child(stockName).removeValue();
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void toAddTwitter(View view) {
        //go to addTwitter page
        String stockToFind = (String) getIntent().getCharSequenceExtra("stkName");
        Intent intent = new Intent(this, AddTwitter.class);
        intent.putExtra("stkName", stockToFind);
        startActivity(intent);
    }
}