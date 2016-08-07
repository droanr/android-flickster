package com.drishi.flickster.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.drishi.flickster.R;
import com.squareup.picasso.Picasso;

public class MovieSynopsisActivity extends AppCompatActivity {

    TextView tvMovieTitle;
    ImageView ivMovieImage;
    TextView tvMovieSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_synopsis);
        tvMovieTitle = (TextView) findViewById(R.id.tvMovieTitle);
        ivMovieImage = (ImageView) findViewById(R.id.ivMovieImage);
        tvMovieSummary = (TextView) findViewById(R.id.tvMovieSummary);

        // Extract and set movie title
        String movieTitle = getIntent().getStringExtra("movie_title");
        tvMovieTitle.setText(movieTitle);

        // Extract movieImage from intent and set with Picasso
        String movieImage = getIntent().getStringExtra("movie_image");
        ivMovieImage.setImageResource(0);
        Picasso.with(getApplicationContext()).load(movieImage).into(ivMovieImage);

        // Extract and set movie synopsis
        String movieSummary = getIntent().getStringExtra("movie_summary");
        tvMovieSummary.setText(movieSummary);
    }
}
