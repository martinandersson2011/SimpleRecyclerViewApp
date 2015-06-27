package com.martinandersson.simplerecyclerviewapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.martinandersson.simplerecyclerviewapp.model.Artist;
import com.martinandersson.simplerecyclerviewapp.model.ArtistsResponse;

import java.util.ArrayList;

public class MainActivity extends Activity {

    public static final String BASE_URL = "https://itunes.apple.com/search/?term=";
    public static final String DEFAULT_SEARCH_TERM = "rock";

    public static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private EditText mEditText;
    private Button mButton;
    private LinearLayoutManager mLayoutManager;
    private ArtistsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RequestManager.init(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mEditText = (EditText) findViewById(R.id.search_text);
        mButton = (Button) findViewById(R.id.search_button);

        mButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                handleSearch();
            }
        });

        mEditText.setText(DEFAULT_SEARCH_TERM);
        mEditText.setSelection(mEditText.getText().length());

        mRecyclerView.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ArtistsAdapter(this, new ArrayList<Artist>());
        mRecyclerView.setAdapter(mAdapter);

        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    handleSearch();
                    return true;
                }
                return false;
            }
        });
        loadData(DEFAULT_SEARCH_TERM);
    }

    private void handleSearch() {
        String searchTerm = mEditText.getText().toString();
        loadData(searchTerm);

        // Hide keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    private void loadData(String searchTerm) {

        String url = BASE_URL + searchTerm;
        StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse");
                handleSongsResponse(new ArtistsResponse(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w(TAG, "onErrorResponse");
                Toast.makeText(MainActivity.this, "No results found", Toast.LENGTH_SHORT).show();
                // handleError();
            }
        });
        // Enable caching and add the request to the RequestQueue.
        req.setShouldCache(true);
        RequestManager.addToRequestQueue(req, TAG);

    }

    private void handleSongsResponse(ArtistsResponse response) {
        Log.d(TAG, "handleSongsResponse");
        mAdapter.updateData(response.getArtists());

    }

}