package com.example.heimdall;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Graph extends AppCompatActivity {
    private FirebaseAuth mAuth;
    com.jjoe64.graphview.series.LineGraphSeries<com.jjoe64.graphview.series.DataPoint> series;
    String currentStock;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference(String.format("stocks/%s/meta/regularMarketPrice", currentStock));
    DatabaseReference myRef2 = database.getReference(String.format("stocks/%s/meta/regularMarketTime", currentStock));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        double value, time;

        mAuth = FirebaseAuth.getInstance();

        com.jjoe64.graphview.GraphView graph = (com.jjoe64.graphview.GraphView) findViewById(R.id.graph);
        series = new LineGraphSeries<>();

        for(int i = 0; i < 500; i++){
            time = 0;//get from db here;
            value = 0;//get from db here;
            series.appendData(new com.jjoe64.graphview.series.DataPoint(time,value), true, 500);
        }

        graph.addSeries(series);
    }
}

