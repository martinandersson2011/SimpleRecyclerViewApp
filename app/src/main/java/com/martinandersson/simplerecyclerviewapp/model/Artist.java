package com.martinandersson.simplerecyclerviewapp.model;

import org.json.JSONObject;

/**
 * Created by martin on 6/26/15.
 */
public class Artist {

    private String artistUrl;
    private String artistName;
    private String songName;

    public static Artist fromJSON(JSONObject json) {
        String artistUrl = json.optString("artworkUrl100", null);
        String artistName = json.optString("artistName", null);
        String songName = json.optString("trackName", null);

        return new Artist(artistUrl, artistName, songName);
    }

    public Artist(String artistUrl, String artistName, String songName) {
        super();
        this.artistUrl = artistUrl;
        this.artistName = artistName;
        this.songName = songName;
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
