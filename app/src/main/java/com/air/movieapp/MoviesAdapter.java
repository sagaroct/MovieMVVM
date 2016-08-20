/*
 *
 *  * Copyright © 2016, Robosoft Technologies Pvt. Ltd
 *  * Written under contract by Robosoft Technologies Pvt. Ltd.
 *
 */

package com.air.movieapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.air.movieapp.model.Movie;

import java.util.List;

/**
 * Created by sagar on 20/8/16.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List<Movie> movies;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick();
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener mOnDirectoryPathChangeClickListener) {
        this.onItemClickListener = mOnDirectoryPathChangeClickListener;
    }

    public MoviesAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public int getItemCount() {
        return this.movies.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.row_movie, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder viewHolder, int position) {
        viewHolder.setData(movies.get(position));
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mTvMovieTitle;
        private TextView mTvReleaseDate;
        private TextView mTvMovieDesc;
        private TextView mTvMovieRating;
        private RelativeLayout mRlMovieContainer;

        public MovieViewHolder(View view) {
            super(view);
            mTvMovieTitle = (TextView) view.findViewById(R.id.tv_movie_name);
            mTvReleaseDate = (TextView) view.findViewById(R.id.tv_release_date);
            mTvMovieDesc = (TextView) view.findViewById(R.id.tv_movie_desc);
            mTvMovieRating = (TextView) view.findViewById(R.id.tv_rating);
            mRlMovieContainer = (RelativeLayout) view.findViewById(R.id.rl_movie_container);
            mRlMovieContainer.setOnClickListener(this);
        }

        public void setData(Movie movie){
            mTvMovieTitle.setText(movie.getTitle());
            mTvReleaseDate.setText(movie.getReleaseDate());
            mTvMovieDesc.setText(movie.getOverview());
            mTvMovieRating.setText((int) movie.getVoteAverage());
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick();
        }
    }

}

