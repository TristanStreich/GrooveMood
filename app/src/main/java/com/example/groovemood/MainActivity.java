package com.example.groovemood;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    //All the previously created playlists
    public static ArrayList<Playlist> playlists;

    //if current song is paused
    public static boolean playing;

    //Currently Playing song
    public static Song currSong;
    //TODO: add other fields that are neccessary for the currently playing song if needed

    //Currently playing playlist
    public static Playlist currPlaylist;
    //TODO: add other fields that are neccessary for the currently playing playlist if needed

    LinearLayout playlistsContainer;

    //TODO: maintain scroll when returning to home screen.
    ScrollView scroller;
    public static int scrollX;
    public static int scrollY;

    Activity thisContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GrooveMood.currActivity = this;
        setTheme(R.style.Theme_GrooveMood);
        setContentView(R.layout.activity_main);
        if (playlists == null){
            playlists = new ArrayList<Playlist>();
            generateTestPlaylists();
        }

        thisContext = this;



        playlistsContainer = findViewById(R.id.playlistsContainer);
        populateScreen();

        scroller = findViewById(R.id.scrollView);
        resetScroll();
        setUpNavBar();

    }

    @Override
    protected void onResume() {
        super.onResume();
        reDrawUI();
    }


    private void generateTestPlaylists() {
        ArrayList<Song> testSongs = new ArrayList<Song>();
        testSongs.add(new Song(500f,"Test Song 1"));
        testSongs.add(new Song(185f,"Test Song 2"));
        testSongs.add(new Song(245f,"Test Song 3"));
        testSongs.add(new Song(113f,"Test Song 4"));
        testSongs.add(new Song(267f,"Test Song 5"));
        testSongs.add(new Song(10000f,"Test Song 6"));
        testSongs.add(new Song(565f,"Test Song 7"));
        testSongs.add(new Song(145f,"Test Song 8"));
        testSongs.add(new Song(207f,"Test Song 9"));
        testSongs.add(new Song(56f,"Test Song 10"));

        Playlist testPlaylist = new Playlist("Party Time", 1f, 1f);
        testPlaylist.addSongs(testSongs);
        playlists.add(testPlaylist);

        testPlaylist = new Playlist("Sunday Morning", 1f, -1f);
        testPlaylist.addSongs(testSongs);
        playlists.add(testPlaylist);

        testPlaylist = new Playlist("Down Bad", -1f, -1f);
        testPlaylist.addSongs(testSongs);
        playlists.add(testPlaylist);


        testPlaylist = new Playlist("Falling Apart", -1f, 1f);
        testPlaylist.addSongs(testSongs);
        playlists.add(testPlaylist);

    }


    private void populateScreen(){
        //TODO: handle no Playlists
        for (int i = playlists.size() - 1; i >= 0; i --){
            displayPlaylist(playlists.get(i));
        }
        navUtils.redrawMusicBar(this);
    }

    public void setUpNavBar() {
        RelativeLayout generatePlaylistButton = findViewById(R.id.GeneratePLaylistButton);
        generatePlaylistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navUtils.gotoGeneratePlaylist(thisContext);
            }
        });

        RelativeLayout HomeButton = findViewById(R.id.HomeButton);
        HomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scroller.smoothScrollTo(0,0);
            }
        });

        ConstraintLayout musicOverlayButton = findViewById(R.id.MusicBar);
        musicOverlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navUtils.openSongOverlay(thisContext);
            }
        });

        View next = findViewById(R.id.musicBarNext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.currPlaylist == null) return;
                currPlaylist.playNext();
                reDrawUI();
            }
        });
        View prev = findViewById(R.id.musicBarPrev);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.currPlaylist == null) return;
                currPlaylist.playPrev();
                reDrawUI();
            }
        });
        View playPause = findViewById(R.id.musicBarPlayPause);
        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.currPlaylist == null) return;
                if (MainActivity.playing){
                    currPlaylist.pause();
                } else {
                    currPlaylist.resume();
                }
                reDrawUI();
            }
        });
    }

    public void reDrawUI(){
        playlistsContainer.removeAllViewsInLayout();
        populateScreen();
//        resetScroll();
    }

    public void saveScroll(){
        scrollX = scroller.getScrollX();
        scrollY = scroller.getScrollY();
    }

    public void resetScroll(){
        scroller.scrollTo(scrollX,scrollY);
    }

    private void displayPlaylist(Playlist playlist){
        View playlistBar = LayoutInflater.from(this).inflate(R.layout.playlist_bar,null);
        playlistsContainer.addView(playlistBar);

        TextView nameText = playlistBar.findViewById(R.id.playlistName);
        nameText.setText(playlist.getName());

        TextView lengthText = playlistBar.findViewById(R.id.playlistLength);
        lengthText.setText(playlist.getReadableLength());

        View image = playlistBar.findViewById(R.id.playlistImage);
        image.setBackgroundColor(playlist.getColor());


        playlistBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navUtils.viewPlaylist(thisContext,playlist);
            }
        });
    }


    //This is called when navigating away from this screen.
    //Use it to save the instance of the screen in static variables
    //so that it can be restored in the onCreate Function
    @Override
    protected void onStop() {
        super.onStop();
        saveScroll();
    }



}