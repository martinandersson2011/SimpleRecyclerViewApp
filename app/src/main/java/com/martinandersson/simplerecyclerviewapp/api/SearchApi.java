package com.martinandersson.simplerecyclerviewapp.api;

import com.martinandersson.simplerecyclerviewapp.model.SongsResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by martin.andersson on 7/31/15.
 */
public interface SearchApi {

    @GET("/search/")
    void getItunesSearchResults(@Query("term") String term, Callback<SongsResponse> searchCallback);

}
