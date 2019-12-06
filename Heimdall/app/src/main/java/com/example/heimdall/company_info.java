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
import java.util.Collections;

import org.w3c.dom.Text;

import java.util.Iterator;
import java.util.Vector;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

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

        //Setting variables for later use
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

        //Event listener for getting the market price of a stock
        //and setting the top panel to reflect it
        stockRef.child(stockName).child("meta").child("regularMarketPrice").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                stockPrice.setText("Current: $" + dataSnapshot.getValue().toString());

                //TODO: These probably shouldn't be here but I was afraid of deleting them
                //Iterable<DataSnapshot> times = dataSnapshot.child("stocks").child(stockName).child("1m").child("t").getChildren();
                ///Iterable<DataSnapshot> prices = dataSnapshot.child("stocks").child(stockName).child("1m").child("p").getChildren();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        //Event listener for getting prices for the graph
        //TODO: Let Sarah and Darbi document this
        stockRef.child(stockName).child("1m").child("p").orderByValue().limitToLast(100).addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


//                Iterable<DataSnapshot> times = dataSnapshot.child(stockName).child("1m").child("t").getChildren();
//                Iterable<DataSnapshot> prices = dataSnapshot.child(stockName).child("1m").child("p").getChildren();

                com.jjoe64.graphview.series.LineGraphSeries<com.jjoe64.graphview.series.DataPoint> series;
                com.jjoe64.graphview.GraphView graph = findViewById(R.id.graph);
                series = new LineGraphSeries<>();

//                double[] t = new double[400];
//                double[] p = new double[400];
                Vector<Double> t = new Vector<Double>();
                Vector<Double> p = new Vector<Double>();

                Double q = 0.;
                Double w = 0.;

                Iterable<DataSnapshot> prices = dataSnapshot.getChildren();

                Log.d("CHILD COUNT", Long.toString(dataSnapshot.child(stockName).child("1m").child("p").getChildrenCount()));

//                for (Iterator<DataSnapshot> itr = times.iterator(); itr.hasNext(); ++q) {
//                    t.add( Double.valueOf(itr.next().getKey()));
//                }

                w = 0.;
                DataSnapshot tmpSnap;
                for (Iterator<DataSnapshot> itr = prices.iterator(); itr.hasNext(); ++w) {
                    tmpSnap = itr.next();
                    p.add((Double) tmpSnap.getValue());
                }

//                int k = 0;
//                for ( Iterator<DataSnapshot> d : prices.iterator()) {
//                    double price = d.getValue(double.class);
//                    k++;
//                }


                for(Double track = 0.; track < 50; track++){
                    t.add(track);
                }
//                for (Object data : dataSnapshot.child("stocks").child(stockName).child("1m").child("t").getChildren()) {
//                    t[l++] = (Double) (data);
//                }


//                for ( DataSnapshot ds : times) {
//                    Double time = (Double) ds.getValue();
//                    Log.d("TIME", time.toString());
//                    t[l] = time;
//                    System.out.print(time + "\n");
//                    l++;
//                    k = 100;
//                }
//                for ( DataSnapshot d : prices) {
//                    Double price = (Double) d.child("stocks").child(stockName).child("1m").child("p").getValue();
//                    Log.d("PRICE", price.toString());
//                    p[k] = price;
//                    System.out.print(price + "\n");
//                    k++;
//                }

//                for (i = 0; i < 20; i++){
//                    t[i] = 50;
//                    p[i] = 50;
//                }
                Collections.sort(t);

                int temp = 0;
                for(double num : p){
                    series.appendData(new com.jjoe64.graphview.series.DataPoint(temp++, num), true, 100);
                }

                graph.getGridLabelRenderer().setLabelFormatter(new com.jjoe64.graphview.DefaultLabelFormatter() {
                    @Override
                    public String formatLabel(double value, boolean isValueX) {
                        if (isValueX) {
                            // show normal x values
                            return super.formatLabel(value, isValueX);
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
                graph.getViewport().setMinY(0);
                graph.getViewport().setMaxY(60);


                graph.addSeries(series);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        //Variable for dynamically adding rows
        final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Event listener for getting the twitter terms
        wlRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                View row = inflater.inflate(R.layout.field2, parentLayout, false);
                Iterable<DataSnapshot> datas = dataSnapshot.child(currentUser).child("Watchlist").child(stockName).getChildren();
                String term = "";
                int i = 547;

                //Goes through all children of the selected stock
                //Adds a row to the scrollView and sets the text to
                //reflect the term
                for (Iterator<DataSnapshot> itr = datas.iterator(); itr.hasNext(); ++i) {
                    term = itr.next().getKey();
                    TextView tmp = (TextView) row;
                    tmp.setText(term);
                    tmp.setId(i);

                    parentLayout.addView(tmp);
                    row = inflater.inflate(R.layout.field2, parentLayout, false);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }


    public void deleteTerm(View view){
        //The code is confusing. Sequence of events is this:
        //1: User clicks twitter term row -> row text and
        //1 cont.: onClick func changed; cancel button is set to visible
        //If user clicks row again: Twitter term is deleted and row is
        //removed
        //If user cancels: Row is set to the original term, cancel button
        //disappears
        final TextView callingObject = findViewById(view.getId());
        final String original = callingObject.getText().toString();
        callingObject.setText("Click again to delete or cancel using the button.");
        final Button cancelButton = findViewById(R.id.cancelDelete);
        cancelButton.setVisibility(View.VISIBLE);
        callingObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callingObject.setVisibility(View.GONE);
                wlRef.child(currentUser).child("Watchlist").child(stockName).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.d("HEIMDALL", "num " + dataSnapshot.getChildrenCount());
                        if(dataSnapshot.getChildrenCount() == 1){
                            wlRef.child(currentUser).child("Watchlist").child(stockName).setValue("Stock added");
                        }else{
                            wlRef.child(currentUser).child("Watchlist").child(stockName).child(original).removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
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
        //Navigate to proper db entry and remove it
        wlRef.child(currentUser).child("Watchlist").child(stockName).removeValue();
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void toAddTwitter(View view) {
        String stockToFind = (String) getIntent().getCharSequenceExtra("stkName");
        Intent intent = new Intent(this, AddTwitter.class);
        intent.putExtra("stkName", stockToFind);
        startActivity(intent);
    }

    public void toHome(View view){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}