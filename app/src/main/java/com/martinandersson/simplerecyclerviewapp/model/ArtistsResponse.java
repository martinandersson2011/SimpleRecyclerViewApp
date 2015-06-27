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
public class ArtistsResponse {
    public static final String TAG = ArtistsResponse.class.getSimpleName();

    public static final String KEY_RESULTS = "results";

    private List<Artist> mArtists;

    public ArtistsResponse(String jsonString) {
        Log.i(TAG, jsonString);

        mArtists = new ArrayList<Artist>();

        try {
            JSONArray response = new JSONObject(jsonString).getJSONArray(KEY_RESULTS);

            for (int i = 0; i < response.length(); i++) {
                mArtists.add(Artist.fromJSON(response.getJSONObject(i)));
            }

        } catch (JSONException e) {
            Log.e(TAG, "JSONException " + e.toString());
        }

    }

    public List<Artist> getArtists() {
        return mArtists;
    }

    public void setArtists(List<Artist> artists) {
        this.mArtists = artists;
    }

}
