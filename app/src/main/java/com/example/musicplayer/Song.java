package com.example.musicplayer;

import java.util.ArrayList;

public class Song {
    private String title;
    private String artist;
    private int photo;
    private int file;
    private int artistPhoto;
    private String info;
    private static ArrayList<Song> songs;

    Song(String title, String artist, int photo, int file, int artistPhoto, String info) {
        this.title = title;
        this.artist = artist;
        this.photo = photo;
        this.file = file;
        this.artistPhoto = artistPhoto;
        this.info = info;
    }

    public static ArrayList<Song> initSong() {
        songs = new ArrayList<>();
        songs.add(new Song("Anh Đang Ở Đâu Đấy Anh", "Hương Giang", R.drawable.anh_dang_o_dau_day_anh_huong_giang, R.raw.anh_dang_o_dau_day_anh_huong_giang, R.drawable.huong_giang, "29/12/1991"));
        songs.add(new Song("Anh Nhà Ở Đâu Thế", "AMEE", R.drawable.anh_nha_o_dau_the_amee, R.raw.anh_nha_o_dau_the_amee, R.drawable.amee, "23/3/2000"));
        songs.add(new Song("Đúng Người Đúng Thời Điểm", "Thanh Hưng", R.drawable.dung_nguoi_dung_thoi_diem_thanh_hung, R.raw.dung_nguoi_dung_thoi_diem_thanh_hung, R.drawable.thanh_hung, "30/10/1991"));
        songs.add(new Song("Hãy Trao Cho Anh", "Sơn Tùng MTP", R.drawable.hay_trao_cho_anh_son_tung_mtp, R.raw.hay_trao_cho_anh_son_tung_mtp, R.drawable.son_tung, "5/7/1994"));
        songs.add(new Song("Mamacita", "Super Junior", R.drawable.mamacita_super_junior, R.raw.mamacita_super_junior, R.drawable.super_junior, "6/11/2005"));
        songs.add(new Song("Nếu Anh Đi", "Mỹ Tâm", R.drawable.neu_anh_di_my_tam, R.raw.neu_anh_di_my_tam, R.drawable.my_tam, "16/1/1981"));
        songs.add(new Song("On My Way", "Alan Walker", R.drawable.on_my_way_alan_walker, R.raw.on_my_way_alan_walker, R.drawable.alan_walker, "24/8/1997"));
        songs.add(new Song("The Chance Of Love", "DBSK", R.drawable.the_chance_of_love_dbsk, R.raw.the_chance_of_love_dbsk, R.drawable.dbsk, "26/12/2003"));
        songs.add(new Song("Thương Em Hơn Chính Anh", "Jun Phạm", R.drawable.thuong_em_hon_chinh_anh_jun_pham, R.raw.thuong_em_hon_chinh_anh_jun_pham, R.drawable.jun_pham, "24/7/1989"));
        songs.add(new Song("Yêu Em Dại Khờ", "Lou Hoàng", R.drawable.yeu_em_dai_kho_lou_hoang, R.raw.yeu_em_dai_kho_lou_hoang, R.drawable.lou_hoang, "6/3/1994"));
        return songs;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public int getPhoto() {
        return photo;
    }

    public int getFile() {
        return file;
    }

    public int getArtistPhoto() {
        return artistPhoto;
    }

    public String getInfo() {
        return info;
    }
}