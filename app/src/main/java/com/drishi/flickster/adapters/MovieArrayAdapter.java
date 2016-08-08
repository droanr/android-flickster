package com.drishi.flickster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.drishi.flickster.R;
import com.drishi.flickster.activities.MovieTrailer;
import com.drishi.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by drishi on 8/2/16.
 */
public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super (context, android.R.layout.simple_list_item_1, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get the data item for position
        final Movie movie = getItem(position);

        // check the existing view being reused
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());

            // false --> don't actually attach it
            convertView = inflater.inflate(R.layout.item_movie, parent, false);
        }

        int orientation = getContext().getResources().getConfiguration().orientation;

        // find the image view
        ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);
        // clear out the image from convertView
        ivImage.setImageResource(0);

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Picasso.with(getContext()).load(movie.getPosterPath()).placeholder(R.mipmap.placeholder).into(ivImage);
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Picasso.with(getContext()).load(movie.getBackdropPath()).placeholder(R.mipmap.placeholder).into(ivImage);
        }

        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvOverview = (TextView) convertView.findViewById(R.id.tvOverview);
        RatingBar rbMovieRating = (RatingBar) convertView.findViewById(R.id.rbMovieRating);
        ImageButton ibMovieTrailer = (ImageButton) convertView.findViewById(R.id.ibMovieTrailer);

        ibMovieTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), MovieTrailer.class);
                i.putExtra("video_key", movie.getVideoKey());
                getContext().startActivity(i);

            }
        });

        rbMovieRating.setRating(movie.getRating());

        // populate data
        tvTitle.setText(movie.getOriginalTitle());
        tvOverview.setText(movie.getOverview());

        //return the view
        return convertView;
    }
}
