package com.example.groovemood;

import android.app.Application;
import android.content.Context;

public class GrooveMood extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        GrooveMood.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return GrooveMood.context;
    }
}
