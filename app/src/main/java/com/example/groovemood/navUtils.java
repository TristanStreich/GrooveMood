package com.example.groovemood;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

public class navUtils {

    public static void gotoGeneratePlaylist(Activity currActivity){
        Intent genPlay = new Intent(currActivity, GeneratePlaylist.class);
        currActivity.startActivity(genPlay);
        currActivity.finish();
    }

    public static void openSongOverlay(Activity currActivity){
        Intent songOverlay = new Intent(currActivity, MusicScreen.class);
        currActivity.startActivity(songOverlay);
        //does not finish so we can navigate back with the finish() function
    }


    //navigates to the home screen.
    public static void goHome(Activity currActivity){
        Intent home = new Intent(currActivity, MainActivity.class);
        currActivity.startActivity(home);
        currActivity.finish();
    }

    //navigates to the ViewPlaylist Screen for the given playlist
    public static void viewPlaylist(Activity currActivity, Playlist playlist){
        Intent playlistScreenIntent = new Intent(currActivity, ViewPlaylistScreen.class);
        currActivity.startActivity(playlistScreenIntent);

        ViewPlaylistScreen.thisPlaylist = playlist;
        currActivity.finish();
    }


    //If currently on a page with a music bar will redraw its name and pause button
    public static void redrawMusicBar(Activity currActivity){

        TextView songName = currActivity.findViewById(R.id.musicBarSongName);
        if (songName == null || MainActivity.currSong == null){
            return;
        }
        songName.setText(MainActivity.currSong.getName());

        ImageView playPause = currActivity.findViewById(R.id.musicBarPlayPause);
        if (!MainActivity.playing){
            playPause.setImageResource(R.drawable.ic_play);
        } else {
            playPause.setImageResource(R.drawable.ic_pause_button);
        }
    }

}
