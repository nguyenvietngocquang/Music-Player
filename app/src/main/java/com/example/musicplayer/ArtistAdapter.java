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

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder> {

    private Context context;
    private ArrayList<Artist> arraylist;
    private ArrayList<Artist> artists;

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    public class ArtistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView artist;
        ImageView artistPhoto;
        private ItemClickListener itemClickListener;

        ArtistViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            cv = (CardView) itemView.findViewById(R.id.cv);
            artist = (TextView) itemView.findViewById(R.id.artist);
            artistPhoto = (ImageView) itemView.findViewById(R.id.artist_photo);
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

    ArtistAdapter(ArrayList<Artist> artists) {
        this.artists = artists;
        this.arraylist = new ArrayList<Artist>();
        this.arraylist.addAll(ArtistsActivity.artists);
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artist, parent, false);
        ArtistViewHolder svh = new ArtistViewHolder(v);
        return svh;
    }

    @Override
    public void onBindViewHolder(ArtistViewHolder holder, int position) {
        holder.artist.setText(artists.get(position).getArtist());
        holder.artistPhoto.setImageResource(artists.get(position).getPhoto());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(context, ArtistInfoActivity.class);
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
        ArtistsActivity.artists.clear();
        if (charText.length() == 0) {
            ArtistsActivity.artists.addAll(arraylist);
        } else {
            for (Artist artist : arraylist) {
                if (artist.getArtist().toLowerCase(Locale.getDefault()).contains(charText)) {
                    ArtistsActivity.artists.add(artist);
                }
            }
        }
        notifyDataSetChanged();
    }
}