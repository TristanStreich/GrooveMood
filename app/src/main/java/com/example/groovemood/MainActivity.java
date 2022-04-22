package com.example.groovemood;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
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

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openSongOptions(View button){
        openPopUp(button,R.menu.song_options_popup);
    }

    public void openPopUp(View button, int ID){
        PopupMenu menu = new PopupMenu(this, button);
        menu.inflate(ID);
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
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

        menu.show();
    }
}