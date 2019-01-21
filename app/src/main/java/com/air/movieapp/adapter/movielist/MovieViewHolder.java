package com.air.movieapp.adapter.movielist;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.air.movieapp.databinding.ViewMovieConstraintBinding;
import com.air.movieapp.model.Movie;

import java.util.List;

/**
 * Created by sagar on 10/5/18.
 */
class MovieViewHolder extends RecyclerView.ViewHolder {


    private MovieListAdapter movieListAdapter;
    ViewMovieConstraintBinding binding;

    public MovieViewHolder(MovieListAdapter movieListAdapter, final List<Movie> movieList, final View view) {
        super(view);
        this.movieListAdapter = movieListAdapter;
        binding = DataBindingUtil.bind(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeInvisibleTitlesVisible(movieList);
                Movie movie = binding.getMovie();
                movie.setVisible(false);
                Log.d("MovieListAdapter", "onClick: "+movie.getTitle());
            }
        });
    }

    private void makeInvisibleTitlesVisible(List<Movie> movieList){
        for(Movie movie: movieList){
                movie.setVisible(true);
        }
    }
}
