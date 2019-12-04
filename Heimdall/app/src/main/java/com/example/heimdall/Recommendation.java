package com.example.heimdall;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.view.PagerAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

//import com.example.add_twiter_follow.ui.main.SectionsPagerAdapter;

public class Recommendation extends AppCompatActivity {
    private LinearLayout parentLayout;
    private FirebaseDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        db = FirebaseDatabase.getInstance();
        DatabaseReference recRef = db.getReference("recommended");
        recRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> datas = dataSnapshot.getChildren();
                parentLayout = findViewById(R.id.RecLayout);
                final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View row = inflater.inflate(R.layout.field3, parentLayout, false);
                String stk = "";
                int i = 297;
                for(Iterator<DataSnapshot> itr = datas.iterator(); i < 307; ++i){
                    stk = itr.next().getKey();
                    TextView tmp = (TextView) row;
                    tmp.setId(i);
                    tmp.setText(stk);
                    parentLayout.addView(row);
                    row = inflater.inflate(R.layout.field3, parentLayout, false);

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

    public void toRecPage(View view) {
        //go to Company Info page
        Intent intent = new Intent(this, Recommendation.class);
        startActivity(intent);
    }
}