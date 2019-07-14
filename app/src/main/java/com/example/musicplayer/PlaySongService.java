package com.example.musicplayer;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;

import java.util.ArrayList;

public class PlaySongService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {
    private MediaPlayer mediaPlayer;
    private ArrayList<Song> songs = Song.initSong();
    private int position;
    private final IBinder songBind = new SongBinder();

    public PlaySongService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return songBind;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mediaPlayer.stop();
        mediaPlayer.release();
        return false;
    }

    public void playSong() {
        mediaPlayer.reset();
        mediaPlayer = MediaPlayer.create(getApplicationContext(), songs.get(position).getFile());
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
        mediaPlayer.prepareAsync();
    }

    public void pauseSong() {
        mediaPlayer.pause();
    }

    public void stopSong() {
        mediaPlayer.stop();
    }

    public boolean isPlaying() {
        if (mediaPlayer.isPlaying()) return true;
        else return false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        position = intent.getIntExtra("Position", -1);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        mediaPlayer.release();
        super.onDestroy();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }

    public class SongBinder extends Binder {
        PlaySongService getService() {
            return PlaySongService.this;
        }
    }
}