package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

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
    ImageButton btnPrev, btnPlayPause, btnNext, btnShuffle;
    int position = 0;
    boolean shuffle = false;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);

        mapping();
        initSong();
        createSong();
        setTimeTotal();
        updateTimeNow();

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

    private void initSong() {
        songs = new ArrayList<>();
        songs.add(new Song("Anh Đang Ở Đâu Đấy Anh", "Hương Giang", R.drawable.anh_dang_o_dau_day_anh_huong_giang, R.raw.anh_dang_o_dau_day_anh_huong_giang));
        songs.add(new Song("Anh Nhà Ở Đâu Thế", "AMEE", R.drawable.anh_nha_o_dau_the_amee, R.raw.anh_nha_o_dau_the_amee));
        songs.add(new Song("Đúng Người Đúng Thời Điểm", "Thanh Hưng", R.drawable.dung_nguoi_dung_thoi_diem_thanh_hung, R.raw.dung_nguoi_dung_thoi_diem_thanh_hung));
        songs.add(new Song("Mamacita", "Super Junior", R.drawable.mamacita_super_junior, R.raw.mamacita_super_junior));
        songs.add(new Song("Nếu Anh Đi", "Mỹ Tâm", R.drawable.neu_anh_di_my_tam, R.raw.neu_anh_di_my_tam));
        songs.add(new Song("On My Way", "Alan Walker", R.drawable.on_my_way_alan_walker, R.raw.on_my_way_alan_walker));
        songs.add(new Song("The Chance Of Love", "DBSK", R.drawable.the_chance_of_love_dbsk, R.raw.the_chance_of_love_dbsk));
        songs.add(new Song("Thương Em Hơn Chính Anh", "Jun Phạm", R.drawable.thuong_em_hon_chinh_anh_jun_pham, R.raw.thuong_em_hon_chinh_anh_jun_pham));
        songs.add(new Song("Yêu Em Dại Khờ", "Lou Hoàng", R.drawable.yeu_em_dai_kho_lou_hoang, R.raw.yeu_em_dai_kho_lou_hoang));
    }
}