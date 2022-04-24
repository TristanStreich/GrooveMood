package com.example.groovemood;

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
}
