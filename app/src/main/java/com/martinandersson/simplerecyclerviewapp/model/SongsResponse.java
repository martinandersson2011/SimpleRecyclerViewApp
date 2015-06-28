package com.martinandersson.simplerecyclerviewapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by martin on 6/26/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SongsResponse extends JSONModel implements Serializable {
    public static final String TAG = SongsResponse.class.getSimpleName();

    public static final String PROPERTY_RESULTS = "results";

    @JsonProperty(PROPERTY_RESULTS)
    private List<Song> songs;

    public SongsResponse() {
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

}
