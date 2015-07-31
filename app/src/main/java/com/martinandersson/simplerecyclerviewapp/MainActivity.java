package com.martinandersson.simplerecyclerviewapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.martinandersson.simplerecyclerviewapp.api.RestClient;
import com.martinandersson.simplerecyclerviewapp.events.ShowDetailEvent;
import com.martinandersson.simplerecyclerviewapp.model.Song;
import com.martinandersson.simplerecyclerviewapp.model.SongsResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {
    public static final String DEFAULT_SEARCH_TERM = "rock";

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String KEY_SONGS_RESPONSE = "KEY_SONGS_RESPONSE";

    @Bind(R.id.search_text)
    EditText mSearchText;
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @Bind(R.id.no_results)
    TextView mNoResults;
    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;

    private LinearLayoutManager mLayoutManager;
    private SongsAdapter mAdapter;
    private List<Song> mSongs = new ArrayList<Song>();
    private SongsResponse mSongsResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mSearchText.setText(DEFAULT_SEARCH_TERM);
        mSearchText.setSelection(mSearchText.getText().length());

        // Check if we have data to display (after rotation)
        if (savedInstanceState != null) {
            mSongsResponse = (SongsResponse) savedInstanceState.getSerializable(KEY_SONGS_RESPONSE);
            mSongs = mSongsResponse.getSongs();
        } else {
            handleSearch();
        }

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

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public void onEvent(ShowDetailEvent event) {
        Song song = event.getSong();
        Log.d(TAG, "onEvent - ShowDetailEvent: " + song.getSongName());

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.ARG_SONG, song);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_SONGS_RESPONSE, mSongsResponse);
    }

    @OnClick(R.id.search_button)
    public void handleSearch() {
        mProgressBar.setVisibility(View.VISIBLE);
        mNoResults.setVisibility(View.GONE);

        // Hide keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mSearchText.getWindowToken(), 0);

        String searchTerm = mSearchText.getText().toString();
        RestClient.getSearchApi().getItunesSearchResults(searchTerm, new Callback<SongsResponse>() {
            @Override
            public void success(SongsResponse songsResponse, Response response) {
                Log.d(TAG, response.getBody().toString());
                mSongsResponse = songsResponse;
                mSongs = mSongsResponse.getSongs();
                mAdapter.updateData(mSongs);
                mProgressBar.setVisibility(View.GONE);
                mNoResults.setVisibility(mSongs != null && mSongs.size() > 0 ? View.GONE : View.VISIBLE);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(MainActivity.this, "No results found", Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.GONE);
                mNoResults.setVisibility(View.VISIBLE);
            }
        });

    }

}