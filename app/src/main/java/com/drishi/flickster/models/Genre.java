package com.drishi.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by drishi on 8/7/16.
 */
public class Genre {
    Integer id;
    String name;

    public Genre(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.getInt("id");
        this.name = jsonObject.getString("name");
    }

    public String getName() {
        return this.name;
    }

    public static HashMap<Integer, Genre> fromJSONArray(JSONArray array) {
        HashMap<Integer, Genre> results = new HashMap<>();

        for(int x = 0; x < array.length(); x++) {
            try {
                Genre genre = new Genre(array.getJSONObject(x));
                results.put(genre.id, genre);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return results;
    }
}
