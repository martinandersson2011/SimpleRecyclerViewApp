package com.martinandersson.simplerecyclerviewapp.events;

import com.martinandersson.simplerecyclerviewapp.model.Song;

/**
 * Created by martin.andersson on 7/29/15.
 */
public class ShowDetailEvent {
    private Song mSong;

    public ShowDetailEvent(Song song) {
        mSong = song;
    }

    public Song getSong() {
        return mSong;
    }

    public void setSong(Song song) {
        mSong = song;
    }
}
