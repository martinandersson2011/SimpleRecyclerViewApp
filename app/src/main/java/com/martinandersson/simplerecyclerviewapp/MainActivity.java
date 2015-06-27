package com.martinandersson.simplerecyclerviewapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.martinandersson.simplerecyclerviewapp.model.Song;
import com.martinandersson.simplerecyclerviewapp.model.SongsResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    public static final String BASE_URL = "https://itunes.apple.com/search/?term=";
    public static final String DEFAULT_SEARCH_TERM = "rock";

    public static final String TAG = MainActivity.class.getSimpleName();

    @InjectView(R.id.search_text)
    EditText mSearchText;
    @InjectView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private LinearLayoutManager mLayoutManager;
    private SongsAdapter mAdapter;
    private List<Song> mSongs = new ArrayList<Song>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        RequestManager.init(this);

        mSearchText.setText(DEFAULT_SEARCH_TERM);
        mSearchText.setSelection(mSearchText.getText().length());

        mRecyclerView.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new SongsAdapter(this, mSongs);
        mRecyclerView.setAdapter(mAdapter);

        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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

    @OnClick(R.id.search_button)
    public void handleSearch() {
        String searchTerm = mSearchText.getText().toString();
        loadData(searchTerm);

        // Hide keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mSearchText.getWindowToken(), 0);
    }

    private void loadData(String searchTerm) {

        String url = BASE_URL + searchTerm;
        StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse");
                handleSongsResponse(new SongsResponse(response));
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

    private void handleSongsResponse(SongsResponse response) {
        Log.d(TAG, "handleSongsResponse");
        mAdapter.updateData(response.getSongs());

    }

}