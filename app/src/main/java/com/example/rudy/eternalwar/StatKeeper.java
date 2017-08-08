package com.example.rudy.eternalwar;

/**
 * Created by Rudy on 8/5/2017.
 */

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class StatKeeper {

    private static final String TAG = "";
    private long hits;
    private long misses;
    private boolean won;

    private long totalHits;
    private long totalMisses;
    private long totalGames;
    private long totalWins;

    private final List<Long> globalStats = new ArrayList<Long>();



    public StatKeeper()
    {
        this.hits = 0;
        this.misses = 0;
        this.won = false;
    }

    public void wasHit()
    {
        hits = hits + 1;
    }

    public void wasMiss()
    {
        misses = misses +1;
    }

    public void wasWin()
    {
        won = true;
    }

    public void gameFinished()
    {
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
                        totalGames = globalStats.get(0);
                        myRef.child("GamesPlayed").setValue(totalGames+1);
                    }
                    else if (globalStats.size() == 2)
                    {
                        totalHits = globalStats.get(1);
                        myRef.child("Hits").setValue(totalHits + hits);
                    }
                    else if(globalStats.size() == 3)
                    {
                        totalMisses = globalStats.get(2);
                        myRef.child("Misses").setValue(totalMisses + misses);
                    }
                    else if(globalStats.size() == 4)
                    {
                        totalWins = globalStats.get(3);
                        if(won == true)
                            myRef.child("Wins").setValue(totalWins + 1);
                    }
                    System.out.println(globalStats.size() + " " + globalStats.get(globalStats.size()-1));
                }

            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


    }
}
