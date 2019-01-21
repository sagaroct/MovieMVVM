/*
 *
 *  * Copyright © 2016, Robosoft Technologies Pvt. Ltd
 *  * Written under contract by Robosoft Technologies Pvt. Ltd.
 *
 */

package com.air.movieapp.view.movielist;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.air.movieapp.MovieApplication;
import com.air.movieapp.R;
import com.air.movieapp.adapter.movielist.MovieListAdapter;
import com.air.movieapp.common.Constants;
import com.air.movieapp.databinding.FragmentMovieBinding;
import com.air.movieapp.model.Movie;
import com.air.movieapp.model.Results;
import com.air.movieapp.network.MoviesRepository;
import com.air.movieapp.view.base.BaseFragment;
import com.air.movieapp.viewmodel.MovieListViewModel;
import com.air.movieapp.viewmodel.MovieListViewModelFactory;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Common fragment for all movie listing
 */
public class MovieListFragment extends BaseFragment implements MovieListAdapter.OnItemClickListener {

    private static final String TAG = MovieListFragment.class.getSimpleName();

    private String mType;
    private MovieListViewModel mMovieListViewModel;
    private FragmentMovieBinding mFragmentMovieBinding;

    @Inject
    MovieListAdapter mMovieListAdapter;

//    @Inject
    private LinearLayoutManager mLinearLayoutManager;

    @Inject
    @Named("SimpleService")
    MoviesRepository mMoviesRepository;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        Bundle args = getArguments();
        mType = args.getString(Constants.TAB);
//        getLifecycle().addObserver(new LifecycleLearningObserver());
    }

    @Override
    protected void setupFragmentComponent() {
        MovieApplication.get(getActivity()).createMovieListComponent(getActivity()).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: called");
//        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        mFragmentMovieBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie, container, false);
        View view = mFragmentMovieBinding.getRoot();
        MovieListViewModelFactory factory = new MovieListViewModelFactory(mMoviesRepository, mType,1);
        mMovieListViewModel  = ViewModelProviders.of(this, factory).get(MovieListViewModel.class);
        mFragmentMovieBinding.setMovieListViewModel(mMovieListViewModel);
        initAdapter();
        getMovieList();
        return view;
    }

    private void getMovieList() {
        mMovieListViewModel.getObservableMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                Log.d(TAG, "onChanged: called");
                mMovieListAdapter.setData(movies);
            }
        });
    }

    public void initAdapter() {
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mFragmentMovieBinding.rvMovie.setItemAnimator(new DefaultItemAnimator());
        mFragmentMovieBinding.rvMovie.setLayoutManager(mLinearLayoutManager);
        mFragmentMovieBinding.rvMovie.setAdapter(mMovieListAdapter);
//        mMovieListAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClicked() {
        Log.d(TAG, "onItemClicked: called");
    }
}
