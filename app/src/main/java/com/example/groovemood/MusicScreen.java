package com.example.groovemood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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

    public LinearLayout musicContainer;
    Activity thisContext;

    public static Playlist thisPlaylist;

    public TextView songName;
    public TextView songLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_screen);
        musicContainer = findViewById(R.id.musicContainer);

        TextView songName = this.findViewById(R.id.textView2);
        if (songName == null || MainActivity.currSong == null){
            return;
        }
        songName.setText(MainActivity.currSong.getName());

        setUpControls();
        thisContext = this;
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
    public void populate() {
        TextView songName = this.findViewById(R.id.textView2);
        if (songName == null || MainActivity.currSong == null){
            return;
        }
        songName.setText(MainActivity.currSong.getName());

        ImageView playPause = this.findViewById(R.id.imageButton4);
        if (!MainActivity.playing){
            playPause.setImageResource(R.drawable.ic_play_button_large);
        } else {
            playPause.setImageResource(R.drawable.large_pause);
        }
    }

    public void reDrawUI(){
        musicContainer.removeAllViewsInLayout();
        populate();
    }

    public void closeSongOverlay(View button){
        finish();
    }
}