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

public class Home extends AppCompatActivity {
    private LinearLayout parentLayout;
    private FirebaseAuth mAuth;
    Vector<String> wList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        parentLayout = findViewById(R.id.VerticalLayout);
        mAuth = FirebaseAuth.getInstance();
        wList = new Vector<String>();
        Log.d("HEIMDALL", "size: " + wList.size());
        loadInCalls(this.getCurrentFocus());
    }

    public void backScreen(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        //TODO: add logout functionality
        mAuth.signOut();
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

        Intent intent = new Intent(this, AddCard.class);
        startActivity(intent);

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

        final String currentUser = mAuth.getUid();
        final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("watchList/");

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                 View row = inflater.inflate(R.layout.field, parentLayout, false);
                 Iterable<DataSnapshot> datas = dataSnapshot.child(currentUser).child("Watchlist").getChildren();
                 String stk = "";
                 int i = 176;

                for (Iterator<DataSnapshot> itr = datas.iterator(); itr.hasNext(); ++i) {
                    stk = itr.next().getKey();
                    if (!wList.contains(stk)) {
                        wList.add(stk);
                        TextView tmp = (TextView) row;
                        tmp.setId(i);
                        tmp.setText(stk);
                        parentLayout.addView(row);
                        row = inflater.inflate(R.layout.field, parentLayout, false);
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("HEIMDALL", "Failed to read value.", error.toException());
            }
        });
    }

    public void infoScreen(View view){
        TextView callingObject = findViewById(view.getId());
        Intent intent = new Intent(this, CardInfo.class);
        intent.putExtra("stkName", callingObject.getText());
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
