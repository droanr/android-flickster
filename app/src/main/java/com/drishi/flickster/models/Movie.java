package com.drishi.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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
    int voteCount;
    Date releaseDate;
    ArrayList<Genre> genres;

    public ArrayList<String> getGenres() {
        ArrayList<String> genres = new ArrayList<>();
        for (Genre genre: this.genres) {
            if (genre == null){
                break;
            }
            genres.add(genre.getName());
        }

        return genres;
    }

    public int getVoteCount() { return this.voteCount; }

    public Date getReleaseDate() { return this.releaseDate; }

    public Movie(JSONObject jsonObject, HashMap<Integer, Genre> genreLookup) throws JSONException{
        this.posterPath = jsonObject.getString("poster_path");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.rating = jsonObject.getInt("vote_average")/2;
        this.voteCount = jsonObject.getInt("vote_count");
        this.genres = new ArrayList<>();
        JSONArray genreIds = jsonObject.getJSONArray("genre_ids");
        for (int x = 0; x < genreIds.length(); x++) {
            System.out.println("ASIUDFHILUASDFHILUSDFHAILSUDFHAISLUDFHALISUDFH");
            int foo = genreIds.getInt(x);
            System.out.println(genreLookup.get(Integer.valueOf(foo)));
            this.genres.add(genreLookup.get(genreIds.get(x)));
        }
        SimpleDateFormat sdFmt = new SimpleDateFormat("yyyy-mm-dd");
        try {
            this.releaseDate = sdFmt.parse(jsonObject.getString("release_date"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Movie> fromJSONArray(JSONArray array, HashMap<Integer, Genre> genreLookup) {
        ArrayList<Movie> results = new ArrayList<>();

        for(int x = 0; x < array.length(); x++) {
            try {
                results.add(new Movie(array.getJSONObject(x), genreLookup));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return results;
    }
}
