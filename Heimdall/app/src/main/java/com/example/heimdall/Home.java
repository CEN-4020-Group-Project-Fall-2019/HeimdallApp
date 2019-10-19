package com.example.heimdall;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class Home extends AppCompatActivity {
    private LinearLayout parentLayout;
    private int rowCount = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        parentLayout = findViewById(R.id.VerticalLayout);
        for(int i = 1; i < getIntent().getIntExtra("numRows", 1); ++i){
            onAddField(this.getCurrentFocus());
        }
    }

    public void backScreen(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public int addScreen(View view){


        return 1;
        //Intent intent = new Intent(this, AddCard.class);
        //startActivity(intent);
    }

    public void onAddField(View v) {
        int selection = addScreen(this.getCurrentFocus());
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View row = inflater.inflate(R.layout.field, null);
        TextView t = findViewById(R.id.textView10);

        if(findViewById(R.id.textView10).getTag().equals("none")){
            t.setText("STK" + selection +"(x.x%)");
            t.setTag("");
        }else {
            row.setId(rowCount);
            row.setLayoutParams(t.getLayoutParams());
            TextView tmp = (TextView) row;
            tmp.setText("NSTK" + selection +"(x.x%)");
            parentLayout.addView(row, parentLayout.getChildCount());
            ++rowCount;
        }
    }

    public void infoScreen(View view){
        Intent intent = new Intent(this, CardInfo.class);
        //TODO: Need to pass rowCount to all functions
        intent.putExtra("numRows", rowCount);
        startActivity(intent);
    }

    public void settingsScreen(View view){
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    public void recommendationsScreen(View view){
        Intent intent = new Intent(this, Recommendation.class);
        startActivity(intent);
    }


}
