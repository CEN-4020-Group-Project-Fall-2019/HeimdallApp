package com.example.heimdall;

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

}
