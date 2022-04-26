package com.example.groovemood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GeneratePlaylist extends AppCompatActivity {

    public static String counterVal;

    //floats representing the position of the ball on the graph
    //must be between -1 and 1
    public static float happySad;
    public static float energy;

    Activity thisContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_playlist);
        if (counterVal != null){
            TextView counter = (TextView) findViewById(R.id.counter);
            counter.setText(counterVal);
        }

        thisContext = this;
        setUpNavBar();
    }

    //This is called when navigating away from this screen.
    //Use it to save the instance of the screen in static variables
    //so that it can be restored in the onCreate Function
    @Override
    protected void onDestroy() {
        super.onDestroy();
        TextView counter = (TextView) findViewById(R.id.counter);
        counterVal = (String) counter.getText();
    }

    @Override
    protected void onResume() {
        super.onResume();
        navUtils.redrawMusicBar(this);
    }

    public void setUpNavBar(){
        RelativeLayout HomeButton = findViewById(R.id.HomeButton);
        HomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navUtils.goHome(thisContext);
            }
        });

        ConstraintLayout musicOverlayButton = findViewById(R.id.MusicBar);
        musicOverlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navUtils.openSongOverlay(thisContext);
            }
        });
    }


    public void generatePlaylist(){
        //TODO: make a new playlist object based on the happy/Sad and energy and add
        //      it to the playlists list in MainActivity
    }

    public void increment(View v) {
        TextView counter = (TextView) v;
        int oldNum = Integer.parseInt( (String) counter.getText());
        counter.setText(Integer.toString(oldNum+1));
    }
}