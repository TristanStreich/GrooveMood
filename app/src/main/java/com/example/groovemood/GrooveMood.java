package com.example.groovemood;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.media.MediaPlayer;

public class GrooveMood extends Application {

    private static Context context;
    public static MediaPlayer mp;
    public static MediaPlayer lengthChecker;

    public static Activity currActivity;

    public void onCreate() {
        super.onCreate();
        GrooveMood.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return GrooveMood.context;
    }

    public static float getLength(int id){
        lengthChecker = MediaPlayer.create(context,id);
        float len = lengthChecker.getDuration()*0.001f;
        lengthChecker.stop();
        return len;
    }
}
