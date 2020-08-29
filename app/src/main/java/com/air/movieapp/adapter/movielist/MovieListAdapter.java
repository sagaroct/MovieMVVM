/*
 *
 *  * Copyright © 2016, Robosoft Technologies Pvt. Ltd
 *  * Written under contract by Robosoft Technologies Pvt. Ltd.
 *
 */

package com.air.movieapp.adapter.movielist;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.air.movieapp.R;
import com.air.movieapp.databinding.ViewMovieConstraintBinding;
import com.air.movieapp.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sagar on 20/8/16.
 */
public class MovieListAdapter extends RecyclerView.Adapter<MovieViewHolder> implements Filterable {

    private List<Movie> mMovieList;

    public MovieListAdapter(List<Movie> movies) {
        this.mMovieList = movies;
    }

    @Override
    public int getItemCount() {
        return this.mMovieList !=null ? mMovieList.size():0;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        ViewMovieConstraintBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()),
                R.layout.view_movie_constraint,
                viewGroup, false);
        return new MovieViewHolder( mMovieList, binding.getRoot());
    }

    @Override
    public void onBindViewHolder(MovieViewHolder viewHolder, int position) {
        Movie movie = mMovieList.get(position);
        viewHolder.binding.setMovie(movie);
    }

    @BindingAdapter("android:visibility")
    public static void setVisibility(View view, Boolean value) {
        view.setVisibility(value ? View.VISIBLE : View.GONE);
    }

    public void setData(List<Movie> data) {
        mMovieList.clear();
        mMovieList.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<Movie> data) {
        mMovieList.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        mMovieList.clear();
        notifyDataSetChanged();
    }


    @Override
    public Filter getFilter() {
        return filter;
    }

    //Filter based on movie title, type and release date.
    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Movie> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mMovieList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Movie item : mMovieList) {
                    if (item.getTitle().toLowerCase().contains(filterPattern)
                        || item.getType().toLowerCase().contains(filterPattern)
                        || item.getRelease_date().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values != null) {
                mMovieList.clear();
                mMovieList.addAll((List) results.values);
                notifyDataSetChanged();
            }
        }
    };
}

