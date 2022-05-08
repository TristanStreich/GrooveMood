package com.example.groovemood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MusicScreen extends AppCompatActivity {

    Activity thisContext;

    public static Playlist thisPlaylist;
    private static Thread thread;

    public TextView songName;
    public TextView songLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GrooveMood.currActivity = this;
        setContentView(R.layout.activity_music_screen);

        TextView songName = this.findViewById(R.id.textView2);
        if (songName == null || MainActivity.currSong == null){
            return;
        }
        songName.setText(MainActivity.currSong.getName());

        setUpControls();
        thisContext = this;
        thread = null;
        reDrawUI();
    }

    public void setUpControls(){

        View next = findViewById(R.id.imageButton);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.currPlaylist == null) return;
                MainActivity.currPlaylist.playNext();
                reDrawUI();
            }
        });
        View prev = findViewById(R.id.imageButton3);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.currPlaylist == null) return;
                MainActivity.currPlaylist.playPrev();
                reDrawUI();
            }
        });
        View playPause = findViewById(R.id.imageButton4);
        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.currPlaylist == null) return;
                if (MainActivity.playing){
                    MainActivity.currPlaylist.pause();
                } else {
                    MainActivity.currPlaylist.resume();
                }
                reDrawUI();
            }
        });
    }
//

    public void reDrawUI(){
        TextView songName = this.findViewById(R.id.textView2);
        if (songName == null || MainActivity.currSong == null){
            return;
        }
        songName.setText(MainActivity.currSong.getName());

        TextView songLength = this.findViewById(R.id.songLength);
        songLength.setText(MainActivity.currSong.getReadableLength());

        updateProgressTime();

        if (thread == null) {
            thread = new Thread() {

                @Override
                public void run() {
                    try {
                        while (!this.isInterrupted()) {
                            Thread.sleep(250);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateProgressTime();
                                }
                            });
                        }
                    } catch (InterruptedException e) {
                    }
                }
            };
            thread.start();
        }


        ImageView playPause = this.findViewById(R.id.imageButton4);
        if (!MainActivity.playing){
            playPause.setImageResource(R.drawable.ic_play_button_large);
        } else {
            playPause.setImageResource(R.drawable.ic_larger_pause_button);
        }
    }

    private void updateProgressTime() {
        float currProg = GrooveMood.mp.getCurrentPosition()*0.001f;
        float len = MainActivity.currSong.getLength();
        TextView prog = findViewById(R.id.progressTime);
        prog.setText(Song.convertToReadableLength(currProg));

        //moves the progress bar
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.setHorizontalBias(R.id.progress_circle, currProg/len);
        constraintSet.applyTo(constraintLayout);
    }

    public void closeSongOverlay(View button){
        finish();
    }
}