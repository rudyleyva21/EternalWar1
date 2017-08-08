package com.example.rudy.eternalwar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EndGameScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game_screen);
        Button okButton = (Button) findViewById(R.id.button2);

        okButton.setOnClickListener(
                new Button.OnClickListener()
                {
                    public void onClick(View V)
                    {
                        Intent i = new Intent(EndGameScreen.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
        );
    }
}
