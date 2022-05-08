package com.example.groovemood;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.widget.Toast;

import androidx.core.graphics.ColorUtils;

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
    public Playlist(String name, float happySad, float energy) {
        this.name = name;

        assert (happySad >= -1f && happySad <= 1f);
        assert (energy >= -1f && energy <= 1f);
        this.happySad = happySad;
        this.energy = energy;

        songs = new ArrayList<Song>();
    }

    public static int convertToColor(float happySad, float energy){
        float saturation = 1f;//0.6f + energy*0.4f;
        float light = .70f - energy*.20f;//.5f;
        //make happy color
        float hue = 50f;
        float []HSL = new float []{hue,saturation,light};
        int happyColor = ColorUtils.HSLToColor(HSL);
        //make sad color
        HSL[0] = 200f;
        int sadColor = ColorUtils.HSLToColor(HSL);
        return ColorUtils.blendARGB(sadColor, happyColor, (happySad+1)/2);
    }

    public int getColor(){
        return convertToColor(getHappySad(),getEnergy());
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
            MainActivity.playing = true;

            if (GrooveMood.mp != null){
                GrooveMood.mp.stop();
            }
            GrooveMood.mp = MediaPlayer.create(GrooveMood.getAppContext(), song.getAudioID());
            GrooveMood.mp.start();
        }
    }

    //plays the song in the playlist that comes after MainActivity.currSong
    //if there is no next song. Then change currSong to the first song and pause
    public void playNext() {
        int currIndex = getSongs().indexOf(MainActivity.currSong);
        if (currIndex == -1){
            return;
        }

        if (currIndex < getSongs().size() - 1){
            //play next
            play(getSongs().get(currIndex+1));
        } else {
            //go to beginning and pause
            playFirst();
            pause();
        }
    }

    //plays the song in the playlist that comes before MainActivity.currSong
    //if there is no prev song then restart the song.
    public void playPrev() {
        int currIndex = getSongs().indexOf(MainActivity.currSong);
        if (currIndex <= 0){
            return;
        }

        play(getSongs().get(currIndex-1));
    }

    //pauses the song
    public void pause() {
        MainActivity.playing = false;
        if (GrooveMood.mp == null){
            return;
        }
        GrooveMood.mp.pause();
    }

    //resumes the song
    public void resume(){
        MainActivity.playing = true;
        GrooveMood.mp.start();
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
