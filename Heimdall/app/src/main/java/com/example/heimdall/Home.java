package com.example.heimdall;

import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
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

        //Function to load in the current user's watchlist
        loadInCalls(this.getCurrentFocus());
    }

    public void loadInCalls(View v){
        //Create variables for the current user id, dynamic view adder, and database refs
        final String currentUser = mAuth.getUid();
        final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("watchList/");

        //Event listeners are ways to access database
        //Functions defined inside are run when the event
        //is triggered
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                 View row = inflater.inflate(R.layout.field, parentLayout, false);
                 Iterable<DataSnapshot> datas = dataSnapshot.child(currentUser).child("Watchlist").getChildren();
                 String stk = "";
                 int i = 176;

                 //Going through the data snapshot and adding a row for each stock
                //in user's watchlist
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

    public void backScreen(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        mAuth.signOut();
    }

    public void onAddField(View v) {
        Intent intent = new Intent(this, AddCard.class);
        startActivity(intent);
    }

    public void infoScreen(View view){
        TextView callingObject = findViewById(view.getId());
        Intent intent = new Intent(this, company_info.class);
        intent.putExtra("stkName", callingObject.getText());
        startActivity(intent);
    }

    public void settingsScreen(View view){
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    public void notificationSystem(View view) {
        //Intent intent = new Intent(this, Home.class);
        //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        //Building notifications
        //NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getString(R.string.CHANNEL_ID))
        //.setSmallIcon(/*not sure what to put here or if we can ignore it*/)
        //        .setContentTitle("TitleToBe")
        //        .setContentText("MSG to user here")
        //        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        //        //.setContentIntent(pendingIntent) //intent to open upon user click...probably don't need this...
        //        .setAutoCancel(true);
    }

}
