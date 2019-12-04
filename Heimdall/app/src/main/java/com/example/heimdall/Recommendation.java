package com.example.heimdall;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.support.v4.view.PagerAdapter;

//import com.example.add_twiter_follow.ui.main.SectionsPagerAdapter;

public class Recommendation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
    }

    //Added to keep some of Jacob's functionality
    public void toHome(View view){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void toCompanyInfo(View view) {
        //go to Company Info page
        Intent intent = new Intent(this, company_info.class);
        startActivity(intent);
    }
}