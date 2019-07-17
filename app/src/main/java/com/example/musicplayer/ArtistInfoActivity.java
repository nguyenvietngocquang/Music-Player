package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ArtistInfoActivity extends AppCompatActivity {
    private ArrayList<Artist> artists;
    private int position;
    private ImageButton btnBack;
    private TextView txtArtist, txtInfo;
    private ImageView ivPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_info);

        Intent intent = this.getIntent();
        position = intent.getExtras().getInt("Position");

        artists = Artist.initArtist();
        btnBack = (ImageButton) findViewById(R.id.btn_back);
        txtArtist = (TextView) findViewById(R.id.txt_artist);
        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
        txtInfo = (TextView) findViewById(R.id.txt_info);

        txtArtist.setText(artists.get(position).getArtist());
        ivPhoto.setImageResource(artists.get(position).getPhoto());
        txtInfo.setText(artists.get(position).getInfo());

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ArtistInfoActivity.this, ArtistsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}