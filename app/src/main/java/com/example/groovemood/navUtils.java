package com.example.groovemood;

import android.app.Activity;
import android.content.Intent;

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

}
