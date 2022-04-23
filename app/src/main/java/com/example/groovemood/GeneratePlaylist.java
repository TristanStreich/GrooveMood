package com.example.groovemood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;

public class GeneratePlaylist extends AppCompatActivity {

    public static String counterVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_playlist);
        if (counterVal != null){
            TextView counter = (TextView) findViewById(R.id.counter);
            counter.setText(counterVal);
        }
    }

    //This is called when navigating away from this screen.
    //Use it to save the instance of the screen in static variables
    //so that it can be restored in the onCreate Function
    @Override
    protected void onDestroy() {
        super.onDestroy();
        TextView counter = (TextView) findViewById(R.id.counter);
        counterVal = (String) counter.getText();
    }

    //navigates to the home screen.
    public void goHome(View button){
        Intent home = new Intent(this, MainActivity.class);
        startActivity(home);
        finish();
    }

    public void increment(View v) {
        TextView counter = (TextView) v;
        int oldNum = Integer.parseInt( (String) counter.getText());
        counter.setText(Integer.toString(oldNum+1));
    }
}