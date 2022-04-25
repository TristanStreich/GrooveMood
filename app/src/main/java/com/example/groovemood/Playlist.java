package com.example.groovemood;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Playlist {

    private ArrayList<Song> songs;
    private String name;
    private final float happySad;
    private final float energy;

    //TODO: add something for the image of the playlist

    //constructor only takes in name and happy/Sad and energy
    //Happy/Sad and energy both are floats that must be between -1 and 1
    //fill playlist with addSong or addSongs
    public Playlist(String name, float happySad, float energy){
        this.name = name;

        assert(happySad >= -1f && happySad <= 1f);
        assert(energy >= -1f && energy <= 1f);
        this.happySad = happySad;
        this.energy = energy;

        songs = new ArrayList<Song>();
    }

    //starts playing the first song in the playlist
    public void playFirst(){
        if (getSongs().size() > 0) {
            play(getSongs().get(0));
        }
    }
    //plays the given song from the playlist
    //updates MainActivity.currPlaylist
    //Does whatever else needs to be done for this
    public void play(Song song) {
        if (getSongs().contains(song)) {
            MainActivity.currPlaylist = this;
            MainActivity.currSong = song;
            //TODO
        }
    }

    //plays the song in the playlist that comes after MainActivity.currSong
    //if there is no next song. Then change currSong to the first song and pause
    public void playNext() {
        //TODO
    }

    //plays the song in the playlist that comes before MainActivity.currSong
    //if there is no prev song then restart the song.
    public void playPrev() {
        //TODO
    }

    public void addSong(Song song){
        songs.add(song);
    }

    public void addSongs(Collection<Song> songList){
        for (Song song : songList){
            addSong(song);
        }
    }

    public void addSongs(Song[] songArray){
        for (Song song : songArray){
            addSong(song);
        }
    }

    //removes song from the playlist
    //returns true if the song was removed and
    //false if nothing was removed
    public boolean removeSong(Song song){
        return songs.remove(song);
    }

    //returns a shallow copy of the songs list
    //changes to the returned playlist will not persist but
    //changes to the songs it contains will persist
    public List<Song> getSongs(){
        return songs;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public float getHappySad() {
        return this.happySad;
    }

    public float getEnergy(){
        return this.energy;
    }

    //returns the length of the playlist in seconds
    public float getLength(){
        float sum = 0;
        for (Song song : getSongs()){
            sum += song.getLength();
        }
        return sum;
    }

    //returns the length of the playlist as a string in the format
    // "mm:ss"
    public String getReadableLength(){
        return readableTime(getLength()/3600);
    }

    //Returns time as a string in a readable format so it can be printed
    //Takes in time in hours
    //1.5 ----> "1h 30m"
    //2.0 ----> "2h "
    //0.75 ---> "45m"
    //0.0 ----> "No Time"
    public String readableTime(float time){
        int hours = (int) (time);
        int minutes = (int) ((time - hours)*60);
        String hourString = hours != 0 ? String.valueOf(hours) + "h " : "";
        String minString = minutes != 0 ? String.valueOf(minutes) + "m" : "";
        String totalString = hourString + minString;
        return !totalString.isEmpty() ? totalString : "No Time";
    }
}
