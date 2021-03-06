package com.martinandersson.simplerecyclerviewapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by martin on 6/26/15.
 */
public class Song implements Serializable {

    public static final String PROPERTY_ARTWORK_URL_100 = "artworkUrl100";
    public static final String PROPERTY_ARTIST_NAME = "artistName";
    public static final String PROPERTY_TRACK_NAME = "trackName";

    @SerializedName(PROPERTY_ARTWORK_URL_100)
    private String artistUrl;

    @SerializedName(PROPERTY_ARTIST_NAME)
    private String artistName;

    @SerializedName(PROPERTY_TRACK_NAME)
    private String songName;

    public Song() {
    }

    public String getArtistUrl() {
        return artistUrl;
    }

    public void setArtistUrl(String artistUrl) {
        this.artistUrl = artistUrl;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

}
