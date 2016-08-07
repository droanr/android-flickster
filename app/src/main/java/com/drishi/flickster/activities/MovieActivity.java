package com.drishi.flickster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.drishi.flickster.R;
import com.drishi.flickster.adapters.MovieArrayAdapter;
import com.drishi.flickster.models.Genre;
import com.drishi.flickster.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class MovieActivity extends AppCompatActivity {

    private static final String movie_url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private static final String movie_genre = "https://api.themoviedb.org/3/genre/movie/list?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    ArrayList<Movie> movies;
    MovieArrayAdapter movieAdapter;
    ListView lvItems;
    SwipeRefreshLayout swipeContainer;
    HashMap<Integer, Genre> genreLookup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        lvItems = (ListView) findViewById(R.id.lvMovies);
        movies = new ArrayList<>();
        genreLookup = new HashMap<Integer, Genre>();
        movieAdapter = new MovieArrayAdapter(this, movies);
        lvItems.setAdapter(movieAdapter);
        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        fetchGenresAsync();
        fetchMoviesAsync(0);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchMoviesAsync(0);
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Movie movie = movies.get(position);
                Intent intent = new Intent(MovieActivity.this, MovieSynopsisActivity.class);
                intent.putExtra("movie_title", movie.getOriginalTitle());
                intent.putExtra("movie_image", movie.getPosterPath());
                intent.putExtra("movie_overview", movie.getOverview());
                intent.putExtra("movie_rating", movie.getRating());
                intent.putExtra("movie_vote_count", movie.getVoteCount());
                intent.putExtra("movie_release_date", movie.getReleaseDate());
                intent.putExtra("movie_genres", movie.getGenres());
                startActivityForResult(intent, 1);
            }
        });

    }

    public AsyncHttpClient getClient() {
        return new AsyncHttpClient();
    }

    public void fetchMoviesAsync(int page) {
        movieAdapter.clear();
        AsyncHttpClient client = getClient();
        client.get(movie_url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieJsonResults = null;

                try {
                    movieJsonResults = response.getJSONArray("results");
                    movies.addAll(Movie.fromJSONArray(movieJsonResults, genreLookup));
                    movieAdapter.notifyDataSetChanged();
                    Log.d("DEBUG", movieJsonResults.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    public void fetchGenresAsync() {
        AsyncHttpClient client = getClient();
        client.get(movie_genre, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray genreJsonResults = null;

                try {
                    genreJsonResults = response.getJSONArray("genres");
                    genreLookup= Genre.fromJSONArray(genreJsonResults);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
}
