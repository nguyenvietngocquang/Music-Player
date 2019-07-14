package com.example.musicplayer;

import java.util.ArrayList;

public class Song {
    private String title;
    private String artist;
    private String album;
    private int photo;
    private int file;

    Song(String title, String artist, String album, int photo) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.photo = photo;
    }

    Song(String title, String artist, int photo) {
        this.title = title;
        this.artist = artist;
        this.photo = photo;
    }

    Song(String title, String artist, int photo, int file) {
        this.title = title;
        this.artist = artist;
        this.photo = photo;
        this.file = file;
    }

    private static ArrayList<Song> songs;

    public static ArrayList<Song> initSong() {
        songs = new ArrayList<>();
        songs.add(new Song("Anh Đang Ở Đâu Đấy Anh", "Hương Giang", R.drawable.anh_dang_o_dau_day_anh_huong_giang));
        songs.add(new Song("Anh Nhà Ở Đâu Thế", "AMEE", R.drawable.anh_nha_o_dau_the_amee));
        songs.add(new Song("Đúng Người Đúng Thời Điểm", "Thanh Hưng", R.drawable.dung_nguoi_dung_thoi_diem_thanh_hung));
        songs.add(new Song("Hãy Trao Cho Anh", "Sơn Tùng MTP", R.drawable.hay_trao_cho_anh_son_tung_mtp));
        songs.add(new Song("Mamacita", "Super Junior", R.drawable.mamacita_super_junior));
        songs.add(new Song("Nếu Anh Đi", "Mỹ Tâm", R.drawable.neu_anh_di_my_tam));
        songs.add(new Song("On My Way", "Alan Walker", R.drawable.on_my_way_alan_walker));
        songs.add(new Song("The Chance Of Love", "DBSK", R.drawable.the_chance_of_love_dbsk));
        songs.add(new Song("Thương Em Hơn Chính Anh", "Jun Phạm", R.drawable.thuong_em_hon_chinh_anh_jun_pham));
        songs.add(new Song("Yêu Em Dại Khờ", "Lou Hoàng", R.drawable.yeu_em_dai_kho_lou_hoang));
        return songs;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getFile() {
        return file;
    }

    public void setFile(int file) {
        this.file = file;
    }
}