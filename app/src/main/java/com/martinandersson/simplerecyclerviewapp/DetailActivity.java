package com.martinandersson.simplerecyclerviewapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.martinandersson.simplerecyclerviewapp.model.Song;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailActivity extends ActionBarActivity {

    public static final String ARG_SONG = "ARG_SONG";

    @Bind(R.id.image)
    ImageView mImage;

    @Bind(R.id.artist)
    TextView mArtist;

    @Bind(R.id.song)
    TextView mSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Song song = (Song) intent.getSerializableExtra(ARG_SONG);

        mArtist.setText(song.getArtistName());
        mSong.setText(song.getSongName());
        Picasso.with(this).load(song.getArtistUrl()).into(mImage);
    }

}
