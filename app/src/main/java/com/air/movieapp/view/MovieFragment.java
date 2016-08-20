/*
 *
 *  * Copyright © 2016, Robosoft Technologies Pvt. Ltd
 *  * Written under contract by Robosoft Technologies Pvt. Ltd.
 *
 */

package com.air.movieapp.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.air.movieapp.R;
import com.air.movieapp.adapter.MoviesAdapter;
import com.air.movieapp.common.Constants;
import com.air.movieapp.common.RestConstants;
import com.air.movieapp.model.Movie;
import com.air.movieapp.model.Results;
import com.air.movieapp.network.MovieApiService;
import com.air.movieapp.network.RestClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Common fragment for all movie listing
 */
public class MovieFragment extends Fragment {

    private static final String TAG = MovieFragment.class.getSimpleName();

    private MoviesAdapter mMoviesAdapter;
    private ArrayList<Movie> movieList;
    private ProgressBar mProgressBar;
    private RecyclerView mMoviesRecyclerView;
    private int mTab;
    private int mPageCount = 1;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        Bundle args = getArguments();
        mTab = args.getInt(Constants.TAB);
        fetchMoviesList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
       //This means the api is not being called
        //and it is first time oncreateview is called
        if(movieList == null){
            mProgressBar.setVisibility(View.VISIBLE);
        }
        mMoviesRecyclerView = (RecyclerView) view.findViewById(R.id.rv_movie);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mMoviesRecyclerView.setLayoutManager(mLayoutManager);
        mMoviesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mMoviesRecyclerView.addOnScrollListener(getListener(mLayoutManager));
        return view;
    }


    //Api called first time and assigned data to adapter
    private void fetchMoviesList(){
        int defaultPageNo = 1;
        Call<Results> call = getCallResult(mTab, defaultPageNo);
        call.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results>call, Response<Results> response) {
                movieList = (ArrayList<Movie>) response.body().getMovies();
                mMoviesAdapter = new MoviesAdapter(movieList);
                mMoviesRecyclerView.setAdapter(mMoviesAdapter);
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                mProgressBar.setVisibility(View.GONE);
                Log.e(TAG, t.toString());
            }
        });
    }

    //Api for pagination
    private void fetchMoviesList(int page){
        Call<Results> call = getCallResult(mTab, page);
        call.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results>call, Response<Results> response) {
                movieList.addAll(response.body().getMovies());
                mMoviesAdapter.notifyDataSetChanged();
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                mProgressBar.setVisibility(View.GONE);
                Log.e(TAG, t.toString());
            }
        });
    }

    private Call<Results> getCallResult(int tab, int page){
        MovieApiService apiService =
                RestClient.getClient().create(MovieApiService.class);
        switch (tab){
            case Constants.TOP_RATED:
            return apiService.getTopRatedMovies(RestConstants.AP_KEY, page);
            case Constants.UPCOMING:
                return apiService.getUpcomingMovies(RestConstants.AP_KEY, page);
            case Constants.POPULAR:
                return apiService.getPopularMovies(RestConstants.AP_KEY, page);
            default: return apiService.getTopRatedMovies(RestConstants.AP_KEY, page);
        }
    }

    //Pagination is handled in here
    @NonNull
    private RecyclerView.OnScrollListener getListener(final LinearLayoutManager mLayoutManager) {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int visibleItemCount = mLayoutManager.getChildCount();
                    int totalItemCount = mLayoutManager.getItemCount();
                    int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= Constants.PAGE_SIZE) {
                        fetchMoviesList(++mPageCount);
                    }
                }
            }
        };
    }

}
