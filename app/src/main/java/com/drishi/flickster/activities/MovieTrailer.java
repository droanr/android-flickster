package com.drishi.flickster.activities;

import android.os.Bundle;

import com.drishi.flickster.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class MovieTrailer extends YouTubeBaseActivity {

    private static final String youtube_api_key= "AIzaSyAdy8CnnFcbSJbdpxceOeLrIbqqEB2ZxuA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailer);

        final YouTubePlayerView yvTrailer = (YouTubePlayerView) findViewById(R.id.player);
        yvTrailer.initialize(youtube_api_key,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                        youTubePlayer.loadVideo("WawU4ouldxU");
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
    }
}
