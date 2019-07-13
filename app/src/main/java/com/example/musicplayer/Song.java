package com.example.musicplayer;

import java.util.ArrayList;

public class Song {
    private String name;
    private String artist;
    private String album;
    private int photo;

    Song(String name, String artist, String album, int photo) {
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.photo = photo;
    }

    Song(String name, String artist, int photo) {
        this.name = name;
        this.artist = artist;
        this.photo = photo;
    }

    private static ArrayList<Song> songs;
    public static ArrayList<Song> initData(){
        songs = new ArrayList<>();
        songs.add(new Song("Anh Đang Ở Đâu Đấy Anh", "Hương Giang", R.drawable.anhdangodaudayanh));
        songs.add(new Song("Đúng Người Đúng Thời Điểm", "Thanh Hưng", R.drawable.dungnguoidungthoidiem));
        songs.add(new Song("Hãy Trao Cho Anh", "Sơn Tùng MTP", R.drawable.haytraochoanh));
        songs.add(new Song("Mamacita", "Super Junior", R.drawable.mamacita));
        songs.add(new Song("Nếu Anh Đi", "Mỹ Tâm", R.drawable.neuanhdi));
        songs.add(new Song("On My Way", "Alan Walker", R.drawable.onmyway));
        songs.add(new Song("The Chance Of Love", "DBSK", R.drawable.thechanceoflove));
        songs.add(new Song("Thương Em Hơn Chính Anh", "Jun Phạm", R.drawable.thuongemhonchinhanh));
        songs.add(new Song("Yêu Em Dại Khờ", "Lou Hoàng", R.drawable.yeuemdaikho));
        return songs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}