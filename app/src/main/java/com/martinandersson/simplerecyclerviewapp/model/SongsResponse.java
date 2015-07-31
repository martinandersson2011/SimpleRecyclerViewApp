package com.martinandersson.simplerecyclerviewapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by martin on 6/26/15.
 */
public class SongsResponse implements Serializable {
    public static final String TAG = SongsResponse.class.getSimpleName();

    public static final String PROPERTY_RESULT_COUNT = "resultCount";
    public static final String PROPERTY_RESULTS = "results";

    @SerializedName(PROPERTY_RESULT_COUNT)
    private int resultCount;

    @SerializedName(PROPERTY_RESULTS)
    private List<Song> songs;

    public SongsResponse() {
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public int getResultCount() {
        return resultCount;
    }

    public void setResultCount(int resultCount) {
        this.resultCount = resultCount;
    }
}
