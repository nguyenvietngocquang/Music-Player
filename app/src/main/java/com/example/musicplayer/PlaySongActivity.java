package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
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
    private TextView txtTitle, txtArtist, txtTimeNow, txtTimeTotal;
    private SeekBar sbSong;
    private ImageView ivPhoto;
    private ImageButton btnBack, btnReplay, btnPrev, btnPlayPause, btnNext, btnShuffle;
    private static int position;
    private static boolean replay, shuffle, binded = false;
    private PlaySongService songService;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);

        Intent intent = this.getIntent();
        position = intent.getExtras().getInt("Position");

        Intent intentService = new Intent(getBaseContext(), PlaySongService.class);
        stopService(intentService);
        intentService.putExtra("Position", position);
        startService(intentService);
        bindService(intentService, serviceConnection, BIND_AUTO_CREATE);

        mapping();
        songs = Song.initSong();

        btnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (songService.mediaPlayer.isPlaying()) {
                    btnPlayPause.setImageResource(R.drawable.btn_play);
                } else {
                    btnPlayPause.setImageResource(R.drawable.btn_pause);
                }
                songService.playPause();
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
                songService.stop();

                Intent intentService = new Intent(getBaseContext(), PlaySongService.class);
                intentService.putExtra("Position", position);
                startService(intentService);

                createSong(position);
                setTimeTotal();
                updateTimeNow();
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
                songService.stop();

                Intent intentService = new Intent(getBaseContext(), PlaySongService.class);
                intentService.putExtra("Position", position);
                startService(intentService);

                createSong(position);
                setTimeTotal();
                updateTimeNow();
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
                PlaySongService.replay = replay;
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
                PlaySongService.shuffle = shuffle;
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
                songService.seekTo(sbSong.getProgress());
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        position = PlaySongService.position;
        createSong(PlaySongService.position);
        setTimeTotal();
        updateTimeNow();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (binded) {
            unbindService(serviceConnection);
            binded = false;
        }
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
                txtTimeNow.setText(format.format(songService.getCurrentPosition()));
                sbSong.setProgress(songService.getCurrentPosition());

                PlaySongService.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
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
                        }
                        songService.stop();

                        Intent intentService = new Intent(getBaseContext(), PlaySongService.class);
                        intentService.putExtra("Position", position);
                        startService(intentService);

                        createSong(position);
                        setTimeTotal();
                        updateTimeNow();
                        btnPlayPause.setImageResource(R.drawable.btn_pause);
                    }
                });
                handler.postDelayed(this, 500);
            }
        }, 100);
    }

    private void setTimeTotal() {
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        mediaPlayer = MediaPlayer.create(getApplicationContext(), songs.get(position).getFile());
        txtTimeTotal.setText(format.format(mediaPlayer.getDuration()));
        sbSong.setMax(mediaPlayer.getDuration());
    }

    private void createSong(int pos) {
        txtTitle.setText(songs.get(pos).getTitle());
        txtArtist.setText(songs.get(pos).getArtist());
        ivPhoto.setImageResource(songs.get(pos).getPhoto());
        if (songService.mediaPlayer != null) {
            if (songService.mediaPlayer.isPlaying()) {
                btnPlayPause.setImageResource(R.drawable.btn_pause);
            } else btnPlayPause.setImageResource(R.drawable.btn_play);
        }
        if (songService.replay) {
            btnReplay.setImageResource(R.drawable.btn_replay_choose);
        } else btnReplay.setImageResource(R.drawable.btn_replay);
        if (songService.shuffle) {
            btnShuffle.setImageResource(R.drawable.btn_shuffle_choose);
        } else btnShuffle.setImageResource(R.drawable.btn_shuffle);
    }

    public ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            PlaySongService.SongBinder songBinder = (PlaySongService.SongBinder) iBinder;
            songService = songBinder.getService();
            binded = true;
            replay = PlaySongService.replay;
            shuffle = PlaySongService.shuffle;

            createSong(position);
            setTimeTotal();
            updateTimeNow();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            binded = false;
        }
    };
}