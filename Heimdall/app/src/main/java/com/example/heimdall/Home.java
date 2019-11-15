package com.example.heimdall;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.Map;

public class Home extends AppCompatActivity {
    private LinearLayout parentLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        parentLayout = findViewById(R.id.VerticalLayout);
        loadInCalls(this.getCurrentFocus());
    }

    public void backScreen(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void addScreen(View view){
        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("New dialog");
        builder.setTitle("Test");

        AlertDialog dialog = builder.create();

        dialog.show();

        return 1;*/
        //Intent intent = new Intent(this, AddCard.class);
        //startActivity(intent);
    }

    public void onAddField(View v) {

        //View row = inflater.inflate(R.layout.field, null);
       /* TextView t = findViewById(R.id.textView10);

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
        }*/
    }

    public void loadInCalls(View v){
        final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("puts");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                View row = inflater.inflate(R.layout.field, null);
                Iterable<DataSnapshot> datas = dataSnapshot.getChildren();
                String stk = "";

                int i = 197;
                Log.d("HEIMDALL", "num children " + dataSnapshot.getChildrenCount());
                Log.d("HEIMDALL", "IM HERE!!");

                for(Iterator<DataSnapshot> itr = datas.iterator(); itr.hasNext(); ){
                    stk = itr.next().getKey();
                    TextView tmp = (TextView) row;
                    Log.d("HEIMDALL", "itr is at " + stk);
                    tmp.setId(i);
                    tmp.setText(stk +"(x.x%)");
                    parentLayout.addView(row);
                    row = inflater.inflate(R.layout.field, null);
                }


                //Log.d("HEIMDALL", "Number of values is: " + datas.toString());

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("HEIMDALL", "Failed to read value.", error.toException());
            }
        });
    }

    public void infoScreen(View view){
        Intent intent = new Intent(this, company_info.class);
        //TODO: Need to pass rowCount to all functions
        //intent.putExtra("numRows", rowCount);
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
