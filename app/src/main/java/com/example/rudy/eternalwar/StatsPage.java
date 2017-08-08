package com.example.rudy.eternalwar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StatsPage extends AppCompatActivity {

    private final List<Long> globalStats = new ArrayList<Long>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_page);

        Button okButton = (Button) findViewById(R.id.statsButton);

        okButton.setOnClickListener(
                new Button.OnClickListener()
                {
                    public void onClick(View V)
                    {
                        Intent i = new Intent(StatsPage.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
        );

        final TextView gpText = (TextView)findViewById(R.id.gpStat);
        final TextView gwText = (TextView)findViewById(R.id.gwStat);
        final TextView wpText = (TextView)findViewById(R.id.wpStat);
        final TextView hitText = (TextView)findViewById(R.id.hitStat);
        final TextView missText = (TextView)findViewById(R.id.missStat);
        final TextView hitperText = (TextView)findViewById(R.id.hpStat);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child : children)
                {

                    long values = (long)child.getValue();
                    globalStats.add(values);
                    if(globalStats.size()==1)
                    {
                        gpText.setText(Long.toString(globalStats.get(0)));
                    }
                    else if (globalStats.size() == 2)
                    {
                        hitText.setText(Long.toString(globalStats.get(1)));
                    }
                    else if(globalStats.size() == 3)
                    {
                        missText.setText(Long.toString(globalStats.get(2)));
                        double temp = globalStats.get(1)/((1.0)*(globalStats.get(1))+(1.0)*(globalStats.get(2)));
                        temp = temp*100;
                        String formattedString = String.format("%.02f", temp);
                        formattedString = formattedString+"%";
                        hitperText.setText(formattedString);
                    }
                    else if(globalStats.size() == 4)
                    {
                        gwText.setText(Long.toString(globalStats.get(3)));
                        double temp = ((1.0)*(globalStats.get(3)))/((1.0)*(globalStats.get(0)));
                        temp = temp*100;
                        String formattedString = String.format("%.02f", temp);
                        formattedString = formattedString+"%";
                        wpText.setText(formattedString);
                    }
                    System.out.println(globalStats.size() + " " + globalStats.get(globalStats.size()-1));
                }

            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


    }
}
