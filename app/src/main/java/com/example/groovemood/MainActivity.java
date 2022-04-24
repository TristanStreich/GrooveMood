package com.example.groovemood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //This is called when navigating away from this screen.
    //Use it to save the instance of the screen in static variables
    //so that it can be restored in the onCreate Function
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void gotoGeneratePlaylist(View button){
        Intent genPlay = new Intent(this, GeneratePlaylist.class);
        startActivity(genPlay);
        finish();
    }

    //navigates to the ViewPlaylist Screen for the given playlist
    public void viewPlaylist(View playlistBar){

    }

    //opens the song options popup menu at the location of BUTTON
    public void openSongOptions(View button){
        openPopUp(button,R.menu.song_options_popup, new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.queue:
                        Toast.makeText(button.getContext(),
                                "TODO: Queue Song", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.removeSong:
                        Toast.makeText(button.getContext(),
                                "TODO: Remove Song", Toast.LENGTH_SHORT).show();
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