package com.drishi.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by drishi on 8/2/16.
 */
public class Movie {

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342%s", posterPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342%s", backdropPath);
    }

    public float getRating() {
        return this.rating;
    }

    String posterPath;
    String backdropPath;
    String originalTitle;
    String overview;
    float rating;

    public Movie(JSONObject jsonObject) throws JSONException{
        this.posterPath = jsonObject.getString("poster_path");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.rating = jsonObject.getInt("vote_average")/2;
    }

    public static ArrayList<Movie> fromJSONArray(JSONArray array) {
        ArrayList<Movie> results = new ArrayList<>();

        for(int x = 0; x < array.length(); x++) {
            try {
                results.add(new Movie(array.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return results;
    }
}
