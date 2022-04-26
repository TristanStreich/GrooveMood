package com.example.groovemood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GeneratePlaylist extends AppCompatActivity {

    public static ImageView moodSelection;
    public static EditText playlistNameField;

    //floats representing the position of the ball on the graph
    //must be between -1 and 1
    public static float happySad;
    public static float energy;

    Activity thisContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_playlist);
        moodSelection = (ImageView) findViewById(R.id.mood_selector);
        moodSelection.setBackgroundResource(R.drawable.mood_selector);
        moodSelection.setOnTouchListener(handleTouch);

        int numPlaylists = MainActivity.playlists.size();
        playlistNameField = (EditText) findViewById(R.id.playlist_name_textfield);
        playlistNameField.setText(String.format("Playlist #%d", numPlaylists+1));
        playlistNameField.selectAll();
        thisContext = this;
        setUpNavBar();
    }

    //This is called when navigating away from this screen.
    //Use it to save the instance of the screen in static variables
    //so that it can be restored in the onCreate Function
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void setUpNavBar(){
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
    }


    public void generatePlaylist(View button){
        //TODO: make a new playlist object based on the happy/Sad and energy and add
        //      it to the playlists list in MainActivity

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

        String playlistName = playlistNameField.getText().toString();

        Playlist newPlaylist = new Playlist(playlistName, happySad, energy);

        newPlaylist.addSongs(testSongs);

        MainActivity.playlists.add(newPlaylist);

        Intent home = new Intent(this, MainActivity.class);
        startActivity(home);
        finish();

    }

    //navigates to the home screen.
    public void goHome(View button){
        Intent home = new Intent(this, MainActivity.class);
        startActivity(home);
        finish();
    }

    private final View.OnTouchListener handleTouch = new View.OnTouchListener() {
        // Stored to draw multiple circle.
        // If you want to draw only one circle then you can make it a local variable


        @SuppressLint("ResourceAsColor")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            energy = (x-v.getWidth())/v.getWidth()/2;
            happySad = (y-v.getHeight())/v.getHeight()/2;
            //Toast.makeText(GeneratePlaylist.this, "x = " + x + ", y = " + y, Toast.LENGTH_LONG).show();

            Paint paint = new Paint();
            int radius;
            radius = 15;
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(R.color.alt_blue);
            // Create a bitmap object of your view size
            Bitmap bmp = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
            // Create the canvas with the bitmap
            Canvas canvas = new Canvas(bmp);
            canvas.drawCircle(x, y, radius, paint);
            // Set the bitmap to the imageView
            ImageView iv = (ImageView) v;
            iv.setImageBitmap(bmp);
            return true;
        }
    };
}

