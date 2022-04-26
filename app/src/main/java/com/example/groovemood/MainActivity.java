package com.example.groovemood;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

    //Currently Playing song
    public static Song currSong;
    //TODO: add other fields that are neccessary for the currently playing song if needed

    //Currently playing playlist
    public static Playlist currPlaylist;
    //TODO: add other fields that are neccessary for the currently playing playlist if needed

    LinearLayout playlistsContainer;
    LinearLayout navBar;
    ScrollView scroller;

    public static int scrollX;
    public static int scrollY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (playlists == null){
            playlists = new ArrayList<Playlist>();
            generateTestPlaylists();
        }



        playlistsContainer = findViewById(R.id.playlistsContainer);
        populateScreen();

        navBar = findViewById(R.id.navBar);
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


        makeTestPlaylist(1,testSongs);
        makeTestPlaylist(2,testSongs);
        makeTestPlaylist(3,testSongs);
        makeTestPlaylist(4,testSongs);
        makeTestPlaylist(5,testSongs);
        makeTestPlaylist(6,testSongs);
        makeTestPlaylist(7,testSongs);
        makeTestPlaylist(8,testSongs);
        makeTestPlaylist(9,testSongs);
        makeTestPlaylist(10,testSongs);
        makeTestPlaylist(123456789,testSongs);

        Playlist testPlaylist = new Playlist("short", 0.4f, .75f);
        testPlaylist.addSongs(testSongs);
        playlists.add(testPlaylist);

    }

    private void makeTestPlaylist(int num, ArrayList<Song> songs){
        Playlist testPlaylist = new Playlist("Test Playlist " + Integer.toString(num), 0.4f, .75f);
        testPlaylist.addSongs(songs);
        playlists.add(testPlaylist);}


    private void populateScreen(){
        //TODO: handle no Playlists
        for (Playlist playlist : playlists){
            displayPlaylist(playlist);
        }
    }

    public void setUpNavBar() {
        RelativeLayout generatePlaylistButton = findViewById(R.id.GeneratePLaylistButton);
        generatePlaylistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoGeneratePlaylist(null);
            }
        });

        RelativeLayout HomeButton = findViewById(R.id.HomeButton);
        HomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scroller.smoothScrollTo(0,0);
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


        playlistBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPlaylist(playlist);
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

    public void gotoGeneratePlaylist(View button){
        Intent genPlay = new Intent(this, GeneratePlaylist.class);
        startActivity(genPlay);
        finish();
    }

    public void openSongOverlay(View button){
        Intent songOverlay = new Intent(this, MusicScreen.class);
        startActivity(songOverlay);
        //does not finish so we can navigate back with the pop() function
    }

    //navigates to the ViewPlaylist Screen for the given playlist
    public void viewPlaylist(Playlist playlist){
        Intent playlistScreenIntent = new Intent(this, ViewPlaylistScreen.class);
        startActivity(playlistScreenIntent);

        ViewPlaylistScreen.thisPlaylist = playlist;
    }

}