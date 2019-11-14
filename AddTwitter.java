package com.example.add_twiter_follow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AddTwitter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_twitter);
    }

    public void toAddTwitter(View view) {
        //go to addTwitter page
        Intent intent = new Intent(this, AddTwitter.class);
        startActivity(intent);
    }

    //Added to keep some of Jacob's functionality
    public void toHome(View view){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void toRecomend(View view) {
        //go to Recomendations page
        Intent intent = new Intent(this, Recommendation.class);
        startActivity(intent);
    }

    public void toSettings(View view) {
        //go to Settings page
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }
}
