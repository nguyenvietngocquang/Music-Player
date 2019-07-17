package com.example.musicplayer;

import java.util.ArrayList;
import java.util.Collections;

public class Artist implements Comparable<Artist> {
    private String artist;
    private int photo;
    private String info;
    private static ArrayList<Song> songs = Song.initSong();

    Artist(String artist, int photo, String info) {
        this.artist = artist;
        this.photo = photo;
        this.info = info;
    }

    public static ArrayList<Artist> initArtist() {
        ArrayList<Artist> artists = new ArrayList<>();
        for (Song song : songs) {
            Artist temp = new Artist(song.getArtist(), song.getArtistPhoto(), song.getInfo());
            if (!artists.contains(temp)) {
                artists.add(temp);
            }
        }
        Collections.sort(artists);
        return artists;
    }

    public String getArtist() {
        return artist;
    }

    public int getPhoto() {
        return photo;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public int compareTo(Artist artist) {
        return this.artist.compareTo(artist.artist);
    }
}