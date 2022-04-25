package com.example.groovemood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class ViewPlaylistScreen extends AppCompatActivity {

    public static Playlist thisPlaylist;

    public LinearLayout songContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_playlist_screen);
        songContainer = findViewById(R.id.songContainer);

        populate(thisPlaylist);
    }

    public void populate(Playlist playlist) {
        ((TextView) findViewById(R.id.playlistName)).setText(playlist.getName());
        ((TextView) findViewById(R.id.playlistLength)).setText(playlist.getReadableLength());

        for (Song song : playlist.getSongs()){
            makeSongBar(song);
        }
    }

    public void makeSongBar(Song song){
        View songBar = LayoutInflater.from(this).inflate(R.layout.song_bar,null);
        songContainer.addView(songBar);

        TextView nameText = songBar.findViewById(R.id.songName);
        nameText.setText(song.getName());

        TextView lengthText = songBar.findViewById(R.id.songLength);
        lengthText.setText(song.getReadableLength());

        //plays the song when clicked
        songBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thisPlaylist.play(song);
                Toast.makeText(songBar.getContext(),
                        "TODO: Play " + song.getName() + " from " + thisPlaylist.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        //options song option pop up when clicked
        Button songOptions = (Button) songBar.findViewById(R.id.songOptions);
        songOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSongOptions(songOptions,song);
            }
        });
    }


    //opens the song options popup menu at the location of BUTTON
    public void openSongOptions(View button, Song song){
        openPopUp(button,R.menu.song_options_popup, new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.queue:
                        Toast.makeText(button.getContext(),
                                "TODO: Queue " + song.getName(), Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.removeSong:
                        Toast.makeText(button.getContext(),
                                "TODO: Remove " + song.getName(), Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    //Opens a pupup menu at the location of BUTTON.
    //The popup menu is specified by ID
    //The actions of the menu are specified by clickListener
    public void openPopUp(View button, int ID, PopupMenu.OnMenuItemClickListener clickListener){
        PopupMenu menu = new PopupMenu(this, button);
        menu.inflate(ID);
        menu.setOnMenuItemClickListener(clickListener);
        menu.show();
    }

    public void closePlaylistScreen(View button){
        finish();
    }
}