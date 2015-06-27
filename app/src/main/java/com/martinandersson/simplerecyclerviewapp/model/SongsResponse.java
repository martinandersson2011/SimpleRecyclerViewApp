package com.martinandersson.simplerecyclerviewapp.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * Created by martin on 6/26/15.
 */
public class SongsResponse {
    public static final String TAG = SongsResponse.class.getSimpleName();

    public static final String KEY_RESULTS = "results";

    private List<Song> mSongs;

    public SongsResponse(String jsonString) {
        Log.i(TAG, jsonString);

        mSongs = new ArrayList<Song>();

        try {
            JSONArray response = new JSONObject(jsonString).getJSONArray(KEY_RESULTS);

            for (int i = 0; i < response.length(); i++) {
                mSongs.add(Song.fromJSON(response.getJSONObject(i)));
            }

        } catch (JSONException e) {
            Log.e(TAG, "JSONException " + e.toString());
        }

    }

    public List<Song> getSongs() {
        return mSongs;
    }

    public void setSongs(List<Song> songs) {
        this.mSongs = songs;
    }

}
