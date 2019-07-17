package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import java.util.ArrayList;

public class ArtistsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private SearchView searchView;
    private RecyclerView rv;
    public static ArrayList<Artist> artists;
    private ArtistAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artists);

        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        artists = Artist.initArtist();

        adapter = new ArtistAdapter(artists);
        adapter.onAttachedToRecyclerView(rv);
        rv.setAdapter(adapter);

        searchView = (SearchView) findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(this);

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

        Button btnAbout = (Button) findViewById(R.id.btn_about);
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ArtistsActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        String text = s;
        adapter.filter(text);
        return false;
    }
}