package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class PlaySongActivity extends AppCompatActivity {

    private ArrayList<Song> songs;
    TextView txtTitle, txtArtist, txtTimeNow, txtTimeTotal;
    SeekBar sbSong;
    ImageView ivPhoto;
    ImageButton btnBack, btnReplay, btnPrev, btnPlayPause, btnNext, btnShuffle;
    int position;
    boolean replay = false, shuffle = false;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);

        Intent intent = this.getIntent();
        position = intent.getIntExtra("Position", -1);

        mapping();
        songs = Song.initSong();
        createSong();
        setTimeTotal();
        updateTimeNow();
        mediaPlayer.start();

        btnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    btnPlayPause.setImageResource(R.drawable.btn_play);
                } else {
                    mediaPlayer.start();
                    btnPlayPause.setImageResource(R.drawable.btn_pause);
                }
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                createSong();
                setTimeTotal();
                updateTimeNow();
                mediaPlayer.start();
                btnPlayPause.setImageResource(R.drawable.btn_pause);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                createSong();
                setTimeTotal();
                updateTimeNow();
                mediaPlayer.start();
                btnPlayPause.setImageResource(R.drawable.btn_pause);
            }
        });

        btnReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (replay) {
                    replay = false;
                    btnReplay.setImageResource(R.drawable.btn_replay);
                } else {
                    replay = true;
                    btnReplay.setImageResource(R.drawable.btn_replay_choose);
                }
            }
        });

        btnShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shuffle) {
                    shuffle = false;
                    btnShuffle.setImageResource(R.drawable.btn_shuffle);
                } else {
                    shuffle = true;
                    btnShuffle.setImageResource(R.drawable.btn_shuffle_choose);
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlaySongActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        sbSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(sbSong.getProgress());
            }
        });
    }

    private void mapping() {
        txtTitle = (TextView) findViewById(R.id.txt_title);
        txtArtist = (TextView) findViewById(R.id.txt_artist);
        txtTimeNow = (TextView) findViewById(R.id.timeNow);
        txtTimeTotal = (TextView) findViewById(R.id.timeTotal);
        sbSong = (SeekBar) findViewById(R.id.seek_bar);
        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
        btnBack = (ImageButton) findViewById(R.id.btn_back);
        btnReplay = (ImageButton) findViewById(R.id.btn_replay);
        btnPrev = (ImageButton) findViewById(R.id.btn_prev);
        btnPlayPause = (ImageButton) findViewById(R.id.btn_play_pause);
        btnNext = (ImageButton) findViewById(R.id.btn_next);
        btnShuffle = (ImageButton) findViewById(R.id.btn_shuffle);
    }

    private void updateTimeNow() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat format = new SimpleDateFormat("mm:ss");
                txtTimeNow.setText(format.format(mediaPlayer.getCurrentPosition()));
                sbSong.setProgress(mediaPlayer.getCurrentPosition());

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if (!replay) {
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
                            createSong();
                            setTimeTotal();
                            updateTimeNow();
                            mediaPlayer.start();
                            btnPlayPause.setImageResource(R.drawable.btn_pause);
                        } else {
                            mediaPlayer.start();
                        }
                    }
                });

                handler.postDelayed(this, 500);
            }
        }, 100);
    }

    private void setTimeTotal() {
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        txtTimeTotal.setText(format.format(mediaPlayer.getDuration()));
        sbSong.setMax(mediaPlayer.getDuration());
    }

    private void createSong() {
        mediaPlayer = MediaPlayer.create(PlaySongActivity.this, songs.get(position).getFile());
        txtTitle.setText(songs.get(position).getTitle());
        txtArtist.setText(songs.get(position).getArtist());
        ivPhoto.setImageResource(songs.get(position).getPhoto());
    }
}