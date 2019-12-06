package com.example.heimdall;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.view.PagerAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

//import com.example.add_twiter_follow.ui.main.SectionsPagerAdapter;

public class Recommendation extends AppCompatActivity {
    private LinearLayout parentLayout;
    private FirebaseDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        db = FirebaseDatabase.getInstance();
        DatabaseReference recRef = db.getReference("recommended");

        //Event listener for the recommended database
        recRef.orderByChild("diff").limitToLast(10).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                displayNotification();

                Iterable<DataSnapshot> datas = dataSnapshot.getChildren();
                parentLayout = findViewById(R.id.RecLayout);
                final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View row = inflater.inflate(R.layout.field3, parentLayout, false);
                String stk = "";
                int i = 297;
                DataSnapshot tmpSnap;

                //Goes through recommendations and adds a row for each
                //one; switches text to show stock names
                for(Iterator<DataSnapshot> itr = datas.iterator(); i < 307; ++i){
                    tmpSnap = itr.next();
                    stk = tmpSnap.getKey();
                    TextView tmp = (TextView) row;
                    tmp.setId(i);
                    tmp.setText(stk);
                    parentLayout.addView(row);
                    row = inflater.inflate(R.layout.field3, parentLayout, false);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void displayNotification(){
        //Code to create an intent that launches into the recommendations
        //page
        Intent resultIntent = new Intent(this, Recommendation.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        //Setting up notification content and then displaying
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "HEIMDALL")
                .setSmallIcon(R.drawable.googleg_disabled_color_18)
                .setContentIntent(resultPendingIntent)
                .setContentTitle("Heimdall")
                .setContentText("Recommendations have been updated!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat nManager = NotificationManagerCompat.from(this);
        nManager.notify(975, builder.build());
    }

    //Added to keep some of Jacob's functionality
    public void toHome(View view){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void toRecPage(View view) {
        //go to Company Info page
        TextView callingObject = findViewById(view.getId());
        Intent intent = new Intent(this, RecommendationCompanyInfo.class);
        intent.putExtra("stkName", callingObject.getText());
        startActivity(intent);
    }
}