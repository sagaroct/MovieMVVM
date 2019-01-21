package com.air.movieapp;

import android.app.Application;
import android.content.Context;

import com.air.movieapp.data.DataModule;
import com.air.movieapp.network.NetworkModule;
import com.air.movieapp.view.movielist.MovieListComponent;
import com.air.movieapp.view.movielist.MovieListModule;

/**
 * Created by sagar on 4/8/17.
 */

public class MovieApplication extends Application {

    public AppComponent mAppComponent;
    public MovieListComponent mMovieListComponent;


    public static MovieApplication get(Context context) {
        return (MovieApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder()
                .networkModule(new NetworkModule(getApplicationContext()))
                .dataModule(new DataModule(getApplicationContext()))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public MovieListComponent createMovieListComponent(Context context) {
        if(mMovieListComponent == null) {
            mMovieListComponent = mAppComponent.plus(new MovieListModule(context));
        }
        return mMovieListComponent;
    }

    public void releaseMovieListComponent() {
        mMovieListComponent = null;
    }
}
