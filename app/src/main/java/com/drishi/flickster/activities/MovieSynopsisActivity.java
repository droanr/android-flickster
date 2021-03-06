package com.drishi.flickster.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.drishi.flickster.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MovieSynopsisActivity extends AppCompatActivity {

    TextView tvMovieTitle;
    ImageView ivMovieImage;
    TextView tvMovieSummary;
    RatingBar rbMovieRating;
    TextView tvVoteCount;
    TextView tvReleaseDate;
    TextView tvGenres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_synopsis);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);

        TextView tvActionBar = (TextView) findViewById(R.id.tvAppTitle);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/remachine_script_font.ttf");
        tvActionBar.setTypeface(tf);

        tvMovieTitle = (TextView) findViewById(R.id.tvMovieTitle);
        ivMovieImage = (ImageView) findViewById(R.id.ivMovieImage);
        tvMovieSummary = (TextView) findViewById(R.id.tvMovieSummary);
        rbMovieRating = (RatingBar) findViewById(R.id.rbMovieRating);

        // Extract and set movie title
        String movieTitle = getIntent().getStringExtra("movie_title");
        tvMovieTitle.setText(movieTitle);

        // Extract movieImage from intent and set with Picasso
        String movieImage = getIntent().getStringExtra("movie_image");
        ivMovieImage.setImageResource(0);
        Picasso.with(getApplicationContext()).load(movieImage).into(ivMovieImage);

        // Extract and set movie synopsis
        String movieSummary = getIntent().getStringExtra("movie_overview");
        tvMovieSummary.setText(movieSummary);

        Bundle bundle = getIntent().getExtras();
        float movieRating = bundle.getFloat("movie_rating");
        rbMovieRating.setRating(movieRating);

        tvVoteCount = (TextView) findViewById(R.id.tvVoteCount);
        int movieVoteCount = bundle.getInt("movie_vote_count", 0);
        tvVoteCount.setText(Integer.toString(movieVoteCount));

        tvReleaseDate = (TextView) findViewById(R.id.tvReleaseDate);
        Date movieReleaseDate = (Date)getIntent().getSerializableExtra("movie_release_date");
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
        tvReleaseDate.setText(formatter.format(movieReleaseDate));

        tvGenres = (TextView) findViewById(R.id.tvGenres);
        ArrayList<String> genres = (ArrayList<String>)bundle.getStringArrayList("movie_genres");
        String mvGenres = genres.toString();
        tvGenres.setText(mvGenres.substring(1, mvGenres.length()-1));
    }
}
