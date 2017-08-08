package com.example.rudy.eternalwar;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

//import com.google.firebase.auth.FirebaseAuth;

//import static com.example.rudy.eternalwar.R.layout.activity_main;

public class MainActivity extends Activity
{

   // private static final String TAG = "myMessage";
    //private FirebaseAuth mAuth;
    //private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        final BattleGrid myBattleGrid = new BattleGrid(this);
        myBattleGrid.setNumColumns(11);
        myBattleGrid.setNumRows(11);
        /*mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };*/
        setContentView(R.layout.activity_main);

        Button playButton = (Button) findViewById(R.id.playButton);
        Button statsButton = (Button) findViewById(R.id.statsButton);
        Button quitButton = (Button) findViewById(R.id.quitButton);

        playButton.setOnClickListener(
                new Button.OnClickListener()
                {
                    public void onClick(View V)
                    {
                        setContentView(myBattleGrid);
                    }
                }
        );

        statsButton.setOnClickListener(
                new Button.OnClickListener()
                {
                    public void onClick(View V)
                    {
                        Intent i = new Intent(MainActivity.this, StatsPage.class);
                        startActivity(i);
                    }
                }
        );

        quitButton.setOnClickListener(
                new Button.OnClickListener()
                {
                    public void onClick(View V)
                    {
                        onDestroy();
                    }
                }
        );

        //setContentView(myBattleGrid);
        //Log.i(TAG, "onCreate");
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        int id= android.os.Process.myPid();
        android.os.Process.killProcess(id);
    }
   /* @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }*/
    /*
    @Override
    protected void onStart()
    {
        super.onStart();
        Log.i(TAG, "onStart");
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        Log.i(TAG, "onResume");
    }


    @Override
    protected void onPause()
    {
        super.onPause();
        Log.i(TAG, "onPause");
    }


    @Override
    protected void onStop()
    {
        super.onStop();
        Log.i(TAG, "onStop");
    }


    @Override
    protected void onRestart()
    {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }


    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    */
}
