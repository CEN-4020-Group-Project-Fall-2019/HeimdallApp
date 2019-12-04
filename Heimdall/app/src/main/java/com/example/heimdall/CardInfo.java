package com.example.heimdall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CardInfo extends AppCompatActivity {
    private int rowCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_info);
        String stockName = (String) getIntent().getCharSequenceExtra("stkName");
        TextView title = findViewById(R.id.textView7);
        title.setText(stockName);
    }

    public void backScreen(View view){
        Intent intent = new Intent(this, Home.class);
        intent.putExtra("numRows", rowCount);
        startActivity(intent);
    }

    public void addTwitter(View view){
        String stockName = (String) getIntent().getCharSequenceExtra("stkName");
        Intent intent = new Intent(this, AddTwitter.class);
        intent.putExtra("stkName", stockName);
        startActivity(intent);
    }
}
