package com.example.groovemood;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class ViewPlaylistScreen extends AppCompatActivity {

    public static Playlist thisPlaylist;

    public LinearLayout songContainer;
    public TextView songName;
    public TextView songLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_playlist_screen);
        songContainer = findViewById(R.id.songContainer);

        songName = (TextView) findViewById(R.id.playlistName);
        songLength = (TextView) findViewById(R.id.playlistLength);

        populate();
    }

    public void populate() {
        //TODO: handle empty playlist
        songName.setText(thisPlaylist.getName());
        songLength.setText(thisPlaylist.getReadableLength());

        for (Song song : thisPlaylist.getSongs()){
            makeSongBar(song);
        }
    }

    public void reDrawUI(){
        songContainer.removeAllViewsInLayout();
        populate();
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

    public void openDeleteDialog(Song song){
        AlertDialog.Builder renameDialog = new AlertDialog.Builder(this);
//        new MaterialAlertDialogBuilder(this,
//                R.style.alertDialogStyle);
        renameDialog.setTitle("Are you sure you want to delete " +
                                song.getName() + " from " + thisPlaylist.getName() + "?");
        renameDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
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

    public void closePlaylistScreen(View button){
        finish();
    }
}