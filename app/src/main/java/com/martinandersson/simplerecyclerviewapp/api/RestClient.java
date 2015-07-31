package com.martinandersson.simplerecyclerviewapp.api;

import com.martinandersson.simplerecyclerviewapp.BuildConfig;

import retrofit.RestAdapter;

/**
 * Created by martin.andersson on 7/31/15.
 */
public final class RestClient {
    public static final String ITUNES_URL = "https://itunes.apple.com";
    public static final boolean ENABLE_LOGGING = true;

    private static SearchApi searchApi;

    private RestClient() {
        // Hidden constructor
    }

    static {
        // Setup our rest adapter and all APIs
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(BuildConfig.DEBUG && ENABLE_LOGGING ? RestAdapter.LogLevel.BASIC : RestAdapter.LogLevel.NONE)
                .setEndpoint(ITUNES_URL)
                .build();
        searchApi = restAdapter.create(SearchApi.class);
    }

    public static SearchApi getSearchApi() {
        return searchApi;
    }
}
