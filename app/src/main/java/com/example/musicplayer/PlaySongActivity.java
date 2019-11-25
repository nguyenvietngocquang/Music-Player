package com.example.musicplayer;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;

public class PlaySongActivity extends AppCompatActivity {

    public static TextView txtTitle, txtArtist, txtTimeNow, txtTimeTotal;
    public static SeekBar sbSong;
    public static ImageView ivPhoto;
    public static ImageButton btnBack, btnShuffle, btnPrev, btnPlayPause, btnNext, btnRepeat;
    public static int position;
    public static boolean binded = false;
    private PlaySongService songService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);
        mapping();

        Intent intent = this.getIntent();
        position = intent.getExtras().getInt("Position");

        Intent intentService = new Intent(getBaseContext(), PlaySongService.class);
        stopService(intentService);
        intentService.putExtra("Position", position);
        startService(intentService);
        bindService(intentService, serviceConnection, BIND_AUTO_CREATE);

        btnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                songService.playPause();
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                songService.prev();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                songService.next();
            }
        });

        btnShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                songService.shuffle();
            }
        });

        btnRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                songService.repeat();
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
                if (b) {
                    SimpleDateFormat format = new SimpleDateFormat("mm:ss");
                    txtTimeNow.setText(format.format(i));
                    sbSong.setProgress(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                songService.seekbarPress = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                songService.seekbarPress = false;
                songService.seekTo(sbSong.getProgress());
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        songService.start();
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
        btnShuffle = (ImageButton) findViewById(R.id.btn_shuffle);
        btnPrev = (ImageButton) findViewById(R.id.btn_prev);
        btnPlayPause = (ImageButton) findViewById(R.id.btn_play_pause);
        btnNext = (ImageButton) findViewById(R.id.btn_next);
        btnRepeat = (ImageButton) findViewById(R.id.btn_repeat);
    }

    public ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            PlaySongService.SongBinder songBinder = (PlaySongService.SongBinder) iBinder;
            songService = songBinder.getService();
            binded = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            binded = false;
        }
    };
}