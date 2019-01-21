package com.air.movieapp.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.air.movieapp.network.MoviesRepository;

/**
 * Created by sagar on 5/10/17.
 */

public class MovieListViewModelFactory implements ViewModelProvider.Factory {

    private final MoviesRepository mMoviesRepository;
    private String mCategory;
    private int mPage;

    public MovieListViewModelFactory(MoviesRepository moviesRepository, String category, int page) {
        this.mMoviesRepository = moviesRepository;
        this.mCategory = category;
        this.mPage = page;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> aClass) {
        if (aClass.isAssignableFrom(MovieListViewModel.class)) {
            return (T) new MovieListViewModel(mMoviesRepository, mCategory, mPage);
        }
        throw new IllegalArgumentException("Unknown class name");
        }
}
