package com.martinandersson.simplerecyclerviewapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.martinandersson.simplerecyclerviewapp.model.Artist;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by martin on 6/26/15.
 */
public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ViewHolder> {

    public static final String TAG = ArtistsAdapter.class.getSimpleName();

    private Context mContext;
    private List<Artist> mArtists;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView artist;
        TextView song;

        public ViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.row_image);
            artist = (TextView) v.findViewById(R.id.row_artist);
            song = (TextView) v.findViewById(R.id.row_song);
        }

    }

    public ArtistsAdapter(Context context, List<Artist> artists) {
        mContext = context;
        mArtists = artists;
    }

    public void updateData(List<Artist> artists) {
        mArtists = artists;
        notifyDataSetChanged();
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ArtistsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_artist, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Artist artist = mArtists.get(position);
        holder.artist.setText(artist.getArtistName());
        holder.song.setText(artist.getSongName());
        Picasso.with(mContext).load(artist.getArtistUrl()).into(holder.image);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mArtists.size();
    }

}
