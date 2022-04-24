package com.example.groovemood;

public final class Song {

    private final float length;  //length of the song in seconds.
    private final String name;
    private Playlist playlist;

    //TODO: add somethings for the audio of the song
    //TODO: add something for the image of the song

    public Song(float len, String name){
        this.length = len;
        this.name = name;
    }

    //starts playing the song.
    //should update MainActivity.CurrSong
    //must do whatever else is needed. Like update the music playing bar and/or
    //music playing screen
    public void play() {
        MainActivity.currSong = this;
        //TODO
    }

    //pauses the song
    public void pause() {
        //TODO
    }

    public String getName(){
        return name;
    }

    //returns the length of the song in seconds
    public float getLength(){
        return length;
    }

    //returns the length of the song as a string in the format
    // "mm:ss"
    public String getReadableLength(){
        return convertToReadableLength(getLength());
    }



    //converts the len in seconds to a human readable format
    // "mm:ss"
    public static String convertToReadableLength(float len){
        int minutes = (int) Math.floor(len/60f);
        int seconds = (int) Math.round(len - minutes*60);

        return Integer.toString(minutes)+":"+Integer.toString(seconds);
    }
}
