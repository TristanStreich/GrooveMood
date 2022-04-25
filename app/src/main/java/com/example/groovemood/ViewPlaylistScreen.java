package com.example.groovemood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ViewPlaylistScreen extends AppCompatActivity {

    public static Playlist thisPlaylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_playlist_screen);

        populate(thisPlaylist);
    }

    public void populate(Playlist playlist) {
        ((TextView) findViewById(R.id.playlistName)).setText(playlist.getName());
        ((TextView) findViewById(R.id.playlistLength)).setText(playlist.getReadableLength());
    }


    public void closePlaylistScreen(View button){
        finish();
    }
}