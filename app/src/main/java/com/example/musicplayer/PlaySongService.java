package com.example.musicplayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class PlaySongService extends Service {
    public static MediaPlayer mediaPlayer;
    private static ArrayList<Song> songs = Song.initSong();
    public static int position, repeat;
    public static boolean shuffle;
    private final IBinder songBinder = new SongBinder();

    public PlaySongService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        position = intent.getExtras().getInt("Position");
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mediaPlayer = MediaPlayer.create(getApplicationContext(), songs.get(position).getFile());
        start();
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

    public void playPause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            PlaySongActivity.btnPlayPause.setImageResource(R.drawable.btn_play);
        } else {
            mediaPlayer.start();
            PlaySongActivity.btnPlayPause.setImageResource(R.drawable.btn_pause);
        }
    }

    public void prev() {
        if (shuffle) {
            Random random = new Random();
            int n;
            do {
                n = random.nextInt(songs.size());
            } while (n == position);
            position = n;
        } else {
            position--;
            if (position < 0) {
                position = songs.size() - 1;
            }
        }
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer = MediaPlayer.create(getApplicationContext(), songs.get(position).getFile());
        start();
        mediaPlayer.start();
        PlaySongActivity.btnPlayPause.setImageResource(R.drawable.btn_pause);
    }

    public void next() {
        if (shuffle) {
            Random random = new Random();
            int n;
            do {
                n = random.nextInt(songs.size());
            } while (n == position);
            position = n;
        } else {
            position++;
            if (position > songs.size() - 1) {
                position = 0;
            }
        }
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer = MediaPlayer.create(getApplicationContext(), songs.get(position).getFile());
        start();
        mediaPlayer.start();
        PlaySongActivity.btnPlayPause.setImageResource(R.drawable.btn_pause);
    }

    public void repeat() {
        if (repeat == 0) {
            repeat = 2;
            PlaySongActivity.btnRepeat.setImageResource(R.drawable.btn_repeat_choose);
        } else if (repeat == 2) {
            repeat = 1;
            PlaySongActivity.btnRepeat.setImageResource(R.drawable.btn_repeat_one);
        } else {
            repeat = 0;
            PlaySongActivity.btnRepeat.setImageResource(R.drawable.btn_repeat);
        }
    }

    public void shuffle() {
        if (shuffle) {
            shuffle = false;
            PlaySongActivity.btnShuffle.setImageResource(R.drawable.btn_shuffle);
        } else {
            shuffle = true;
            PlaySongActivity.btnShuffle.setImageResource(R.drawable.btn_shuffle_choose);
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

    public void start() {
        createSong();
        setTimeTotal();
        updateTimeNow();
    }

    private void createSong() {
        PlaySongActivity.txtTitle.setText(songs.get(position).getTitle());
        PlaySongActivity.txtArtist.setText(songs.get(position).getArtist());
        PlaySongActivity.ivPhoto.setImageResource(songs.get(position).getPhoto());
        if (repeat == 0) {
            PlaySongActivity.btnRepeat.setImageResource(R.drawable.btn_repeat);
        } else if (repeat == 1) {
            PlaySongActivity.btnRepeat.setImageResource(R.drawable.btn_repeat_one);
        } else {
            PlaySongActivity.btnRepeat.setImageResource(R.drawable.btn_repeat_choose);
        }
        if (shuffle) {
            PlaySongActivity.btnShuffle.setImageResource(R.drawable.btn_shuffle_choose);
        } else PlaySongActivity.btnShuffle.setImageResource(R.drawable.btn_shuffle);
    }

    private void setTimeTotal() {
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        PlaySongActivity.txtTimeTotal.setText(format.format(mediaPlayer.getDuration()));
        PlaySongActivity.sbSong.setMax(mediaPlayer.getDuration());
    }

    private void updateTimeNow() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat format = new SimpleDateFormat("mm:ss");
                PlaySongActivity.txtTimeNow.setText(format.format(mediaPlayer.getCurrentPosition()));
                PlaySongActivity.sbSong.setProgress(mediaPlayer.getCurrentPosition());

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if (repeat != 1) {
                            if (shuffle) {
                                Random random = new Random();
                                int n;
                                do {
                                    n = random.nextInt(songs.size());
                                } while (n == position);
                                position = n;
                            } else {
                                position++;
                                if (position > songs.size() - 1) {
                                    position = 0;
                                }
                            }
                        }

                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();
                        }
                        mediaPlayer = MediaPlayer.create(getApplicationContext(), songs.get(position).getFile());
                        start();
                        if (!shuffle && position == 0 && repeat == 0) {
                            PlaySongActivity.btnPlayPause.setImageResource(R.drawable.btn_play);
                        } else {
                            mediaPlayer.start();
                            PlaySongActivity.btnPlayPause.setImageResource(R.drawable.btn_pause);
                        }
                    }
                });
                handler.postDelayed(this, 500);
            }
        }, 100);
    }

    public void seekTo(int progress) {
        mediaPlayer.seekTo(progress);
    }
}