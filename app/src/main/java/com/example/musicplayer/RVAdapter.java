package com.example.musicplayer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.SongViewHolder> {
    public static class SongViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView songName;
        TextView songArtist;
        ImageView songPhoto;

        SongViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            songName = (TextView) itemView.findViewById(R.id.song_name);
            songArtist = (TextView) itemView.findViewById(R.id.song_artist);
            songPhoto = (ImageView) itemView.findViewById(R.id.song_photo);
        }
    }

    ArrayList<Song> songs;

    RVAdapter(ArrayList<Song> songs){
        this.songs = songs;
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        SongViewHolder svh = new SongViewHolder(v);
        return svh;
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {
        holder.songName.setText(songs.get(position).getName());
        holder.songArtist.setText(songs.get(position).getArtist());
        holder.songPhoto.setImageResource(songs.get(position).getPhoto());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}