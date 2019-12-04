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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//import com.example.add_twiter_follow.ui.main.SectionsPagerAdapter;

public class Recommendation extends AppCompatActivity {
    private LinearLayout parentLayout;
    private FirebaseDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        db = FirebaseDatabase.getInstance();
        DatabaseReference recRef = db.getReference("calls");
        parentLayout = findViewById(R.id.RecLayout);
        final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.field3, parentLayout, false);
        String stk = "Stock";
        for(int i = 287; i < 297; ++i){
            TextView tmp = (TextView) row;
            tmp.setId(i);
            tmp.setText(stk + i);
            parentLayout.addView(row);
            row = inflater.inflate(R.layout.field3, parentLayout, false);

        }
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