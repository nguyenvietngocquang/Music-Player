package com.example.musicplayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import java.util.ArrayList;

public class PlaySongService extends Service {
    public static MediaPlayer mediaPlayer;
    private ArrayList<Song> songs = Song.initSong();
    public static int position;
    public static boolean replay = false, shuffle = false;
    private final IBinder songBinder = new SongBinder();

    public PlaySongService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        position = intent.getExtras().getInt("Position");
        mediaPlayer = MediaPlayer.create(getApplicationContext(), songs.get(position).getFile());
        mediaPlayer.start();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.release();
        super.onDestroy();
    }

    public static void playPause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        } else mediaPlayer.start();
    }

    public static void stop() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return songBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return true;
    }

    public class SongBinder extends Binder {
        PlaySongService getService() {
            return PlaySongService.this;
        }
    }

    public static int getDuration() {
        return mediaPlayer.getDuration();
    }

    public static int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    public static void seekTo(int progress) {
        mediaPlayer.seekTo(progress);
    }
}