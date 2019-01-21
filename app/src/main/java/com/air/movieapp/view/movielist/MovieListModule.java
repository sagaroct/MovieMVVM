package com.air.movieapp.view.movielist;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import com.air.movieapp.adapter.movielist.MovieListAdapter;
import com.air.movieapp.model.Movie;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by sagar on 10/8/17.
 */

@Module
public class MovieListModule {

    private Context mContext;

    public MovieListModule(Context context) {
        this.mContext = context;
    }

    @Provides
    @MovieListScope
    public MovieListAdapter provideMovieListAdapter(){
        return new MovieListAdapter(new ArrayList<Movie>());
    }

   /* @Provides
    @MovieListScope
    public LinearLayoutManager provideLinearLayoutManager(){
        return new LinearLayoutManager(mContext);
    }*/

}
