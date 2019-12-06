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
import com.jjoe64.graphview.series.LineGraphSeries;

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

                Iterable<DataSnapshot> times = dataSnapshot.child("stocks").child(stockName).child("1m").child("t").getChildren();
                Iterable<DataSnapshot> prices = dataSnapshot.child("stocks").child(stockName).child("1m").child("p").getChildren();

                com.jjoe64.graphview.series.LineGraphSeries<com.jjoe64.graphview.series.DataPoint> series;
                com.jjoe64.graphview.GraphView graph = findViewById(R.id.graph);
                series = new LineGraphSeries<>();

                double[] t = new double[400];
                double[] p = new double[400];
                int l = 0 , k = 0;
                //java.util.ArrayList<Double> t= new java.util.ArrayList<>();
                //java.util.ArrayList<Double> p= new java.util.ArrayList<>();
                for ( DataSnapshot ds : times) {
                    double time = ds.child("stocks").child(stockName).child("1m").child("t").getValue(double.class);
                    t[l] = time;
                    System.out.print(time + "\n");
                    l++;
                }
                for ( DataSnapshot d : prices) {
                    double price = d.child("stocks").child(stockName).child("1m").child("p").getValue(double.class);
                    p[k] = price;
                    System.out.print(price + "\n");
                    k++;
                }
                for(int j = 0; j < k; j++) {
                    double x = t[j];
                    double y = p[j];
                    series.appendData(new com.jjoe64.graphview.series.DataPoint(x,y), true, 100);
                }
                graph.getGridLabelRenderer().setLabelFormatter(new com.jjoe64.graphview.DefaultLabelFormatter() {
                    @Override
                    public String formatLabel(double value, boolean isValueX) {
                        if (isValueX) {
                            // show normal x values
                            return super.formatLabel(value, isValueX) + "m";
                        }else {
                            // show currency for y values
                            return "$" + super.formatLabel(value, isValueX);
                        }
                    }
                });
                // set manual X bounds
                graph.getViewport().setXAxisBoundsManual(true);
                graph.getViewport().setMinX(0);
                graph.getViewport().setMaxX(100);

// set manual Y bounds
                graph.getViewport().setYAxisBoundsManual(true);
                graph.getViewport().setMinY(5);
                graph.getViewport().setMaxY(100);


                graph.addSeries(series);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        /*com.jjoe64.graphview.series.LineGraphSeries<com.jjoe64.graphview.series.DataPoint> series;
        DatabaseReference myRef = db.getReference(String.format("stocks/%s/meta/regularMarketPrice", stockName));
        DatabaseReference myRef2 = db.getReference(String.format("stocks/%s/meta/regularMarketTime", stockName));
        double value, time;
        com.jjoe64.graphview.GraphView graph = (com.jjoe64.graphview.GraphView) findViewById(R.id.graph);
        series = new LineGraphSeries<>();

        for(int i = 0; i < 500; i++){
            time = 0;//get from db here;
            value = 0;//get from db here;
            series.appendData(new com.jjoe64.graphview.series.DataPoint(time,value), true, 500);
        }*/

        //graph.addSeries(series);
        /*DataSnapshot dataSnapshot;
        Iterable<DataSnapshot> times = dataSnapshot.child("stocks").child(stockName).child("1m/t/").getChildren();
                Iterable<DataSnapshot> prices = dataSnapshot.child("stocks").child(stockName).child("1m/p").getChildren();

                com.jjoe64.graphview.series.LineGraphSeries<com.jjoe64.graphview.series.DataPoint> series;
                com.jjoe64.graphview.GraphView graph = (com.jjoe64.graphview.GraphView) findViewById(R.id.graph);
                series = new LineGraphSeries<>();

                java.util.ArrayList<Double> t= new java.util.ArrayList<>();
                java.util.ArrayList<Double> p= new java.util.ArrayList<>();
                for (DataSnapshot ds : times) {
                    Double time = ds.child("stocks").child(stockName).child("1m/t/").getValue(Double.class);
                    t.add(time);
                }
                for (DataSnapshot d : prices) {
                    Double price = d.child("stocks").child(stockName).child("1m/p/").getValue(Double.class);
                    p.add(price);
                }
                for(int j = 0; j < 400; j++) {

                    series.appendData(new com.jjoe64.graphview.series.DataPoint(t.get(i), p.get(i)), true, 400);
                }
                graph.addSeries(series);*/
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