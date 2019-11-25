package com.example.musicplayer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {

    private Context context;
    private ArrayList<Song> arraylist;
    private ArrayList<Song> songs;

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    public class SongViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView songTitle;
        TextView songArtist;
        ImageView songPhoto;
        private ItemClickListener itemClickListener;

        SongViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            cv = (CardView) itemView.findViewById(R.id.cv);
            songTitle = (TextView) itemView.findViewById(R.id.song_title);
            songArtist = (TextView) itemView.findViewById(R.id.song_artist);
            songPhoto = (ImageView) itemView.findViewById(R.id.song_photo);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition());
        }
    }

    SongAdapter(ArrayList<Song> songs) {
        this.songs = songs;
        this.arraylist = new ArrayList<Song>();
        this.arraylist.addAll(this.songs);
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
        SongViewHolder svh = new SongViewHolder(v);
        return svh;
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {
        holder.songTitle.setText(songs.get(position).getTitle());
        holder.songArtist.setText(songs.get(position).getArtist());
        holder.songPhoto.setImageResource(songs.get(position).getPhoto());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(context, PlaySongActivity.class);
                intent.putExtra("Position", position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        MainActivity.songs.clear();
        if (charText.length() == 0) {
            MainActivity.songs.addAll(arraylist);
        } else {
            for (Song song : arraylist) {
                if ((song.getTitle().toLowerCase(Locale.getDefault()).contains(charText)) ||
                        (song.getArtist().toLowerCase(Locale.getDefault()).contains(charText))) {
                    MainActivity.songs.add(song);
                }
            }
        }
        notifyDataSetChanged();
    }
}