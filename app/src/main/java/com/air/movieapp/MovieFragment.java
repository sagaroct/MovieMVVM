/*
 *
 *  * Copyright © 2016, Robosoft Technologies Pvt. Ltd
 *  * Written under contract by Robosoft Technologies Pvt. Ltd.
 *
 */

package com.air.movieapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.air.movieapp.common.Constants;
import com.air.movieapp.model.Movie;

import java.util.ArrayList;

/**
 * Created by sagar on 20/8/16.
 */
public class MovieFragment extends Fragment {

    private MoviesAdapter mAdapter;
    private ArrayList<Movie> movieList;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        Bundle args = getArguments();
        String screen = args.getString(Constants.SCREEN);


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        RecyclerView moviesRecyclerView = (RecyclerView) view.findViewById(R.id.rv_movie);
        mAdapter = new MoviesAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        moviesRecyclerView.setLayoutManager(mLayoutManager);
        moviesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        moviesRecyclerView.setAdapter(mAdapter);
        return view;
    }


    private void fetchMoviesList(){

    }

}
