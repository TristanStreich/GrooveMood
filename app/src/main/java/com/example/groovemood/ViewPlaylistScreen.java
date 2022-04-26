package com.example.groovemood;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class ViewPlaylistScreen extends AppCompatActivity {

    public static Playlist thisPlaylist;

    public LinearLayout songContainer;
    public TextView songName;
    public TextView songLength;

    Activity thisContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_playlist_screen);
        songContainer = findViewById(R.id.songContainer);

        songName = (TextView) findViewById(R.id.playlistName);
        songLength = (TextView) findViewById(R.id.playlistLength);

        populate();

        thisContext = this;
        setUpNavBar();
    }

    public void populate() {
        //TODO: handle empty playlist
        songName.setText(thisPlaylist.getName());
        songLength.setText(thisPlaylist.getReadableLength());

        for (Song song : thisPlaylist.getSongs()){
            makeSongBar(song);
        }

        navUtils.redrawMusicBar(this);
    }

    public void reDrawUI(){
        songContainer.removeAllViewsInLayout();
        populate();
    }

    public void setUpNavBar(){
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

        View next = findViewById(R.id.musicBarNext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.currPlaylist == null) return;
                MainActivity.currPlaylist.playNext();
                reDrawUI();
            }
        });
        View prev = findViewById(R.id.musicBarPrev);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.currPlaylist == null) return;
                MainActivity.currPlaylist.playPrev();
                reDrawUI();
            }
        });
        View playPause = findViewById(R.id.musicBarPlayPause);
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
                reDrawUI();
            }
        });

        //options song option pop up when clicked
        View songOptions = songBar.findViewById(R.id.songOptions);
        songOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSongOptions(songOptions,song);
            }
        });
    }

    public void openRenameDialog(View button){
        EditText editText = new EditText(this);
        AlertDialog.Builder renameDialog = new AlertDialog.Builder(this);
        renameDialog.setTitle("Enter New Name");
        renameDialog.setView(editText);
        editText.setText(thisPlaylist.getName());
        renameDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String newName = editText.getText().toString();
                if (newName != null && newName.length() >= 1){
                    thisPlaylist.setName(newName);
                    reDrawUI();
                }
            }
        });
        renameDialog.setNegativeButton("Cancel", null);

        renameDialog.show();
    }

    public void playThisPlaylist(View button){
        thisPlaylist.playFirst();
        reDrawUI();
    }

    public void openDeleteDialog(Song song){
        AlertDialog.Builder renameDialog = new AlertDialog.Builder(this);
//        new MaterialAlertDialogBuilder(this,
//                R.style.alertDialogStyle);
        renameDialog.setTitle("Are you sure you want to delete " +
                                song.getName() + " from " + thisPlaylist.getName() + "?");
        renameDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                thisPlaylist.playNext();
                thisPlaylist.removeSong(song);
                reDrawUI();
            }
        });
        renameDialog.setNeutralButton("No", null);

        renameDialog.show();
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
                        openDeleteDialog(song);
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

}