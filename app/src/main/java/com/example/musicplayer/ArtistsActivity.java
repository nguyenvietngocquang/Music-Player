package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ArtistsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artists);

        Button btnSongs = (Button) findViewById(R.id.btn_songs);
        btnSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ArtistsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button btnAlbums = (Button) findViewById(R.id.btn_albums);
        btnAlbums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ArtistsActivity.this, AlbumsActivity.class);
                startActivity(intent);
            }
        });

        Button btnSettings = (Button) findViewById(R.id.btn_settings);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ArtistsActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}
