package com.example.add_twiter_follow;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.add_twiter_follow.ui.main.SectionsPagerAdapter;

public class recommendations extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    //Added to keep some of Jacob's functionality
    public void backScreen(View view){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void toAddTwitter(View view) {
        //go to addTwitter page
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void toRecomend(View view) {
        //go to Recomendations page
        Intent intent = new Intent(this, recommendations.class);
        startActivity(intent);
    }

    public void toSettings(View view) {
        //go to Settings page
        Intent intent = new Intent(this, settings.class);
        startActivity(intent);
    }
}