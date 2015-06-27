package com.martinandersson.simplerecyclerviewapp;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Manager class for the volley request queue
 */
public final class RequestManager {
    private static final String TAG = RequestManager.class.getSimpleName();
    public static final int REQUEST_TIMEOUT = 20; // Seconds
    public static final int MAX_NUMBER_OF_RETRIES = 1;
    public static final float BACKOFF_MULTIPLIER = 1.0f;

    private static RequestQueue mRequestQueue;

    private RequestManager() {
        // no instances
    }

    /**
     * @param context application context
     */
    public static void init(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    /**
     * @return instance of the queue
     * @throws IllegalStateException if init has not yet been called
     */
    public static RequestQueue getRequestQueue() {
        if (mRequestQueue != null) {
            return mRequestQueue;
        } else {
            throw new IllegalStateException("Not initialized");
        }
    }

    /**
     * Adds the specified request to the global queue, if tag is specified then it is used else Default TAG is used.
     *
     * @param req the request to add
     * @param tag the tag to use
     */
    public static <T> void addToRequestQueue(Request<T> req, String tag) {
        Log.d(TAG, ((req.getMethod() == Request.Method.POST) ? "POST" : "GET") + " ---> " + req.getUrl());

        // Set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        req.setRetryPolicy(new DefaultRetryPolicy(REQUEST_TIMEOUT * 1000, MAX_NUMBER_OF_RETRIES, BACKOFF_MULTIPLIER));

        getRequestQueue().add(req);
    }

    /**
     * Adds the specified request to the global queue using the Default TAG.
     *
     * @param req the request to add
     */
    public static <T> void addToRequestQueue(Request<T> req) {
        addToRequestQueue(req, TAG);
    }
}
