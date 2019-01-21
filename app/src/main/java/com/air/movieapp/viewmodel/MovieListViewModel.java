package com.air.movieapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import com.air.movieapp.model.Movie;
import com.air.movieapp.model.Results;
import com.air.movieapp.network.MoviesRepository;
import com.air.movieapp.network.NetworkError;
import com.air.movieapp.network.ResponseCallback;

import java.util.List;

import retrofit2.Call;


/**
 * Created by sagar on 26/9/17.
 */

public class MovieListViewModel extends ViewModel {

    private MutableLiveData<List<Movie>> mObservableMovies;
    private MoviesRepository mMoviesRepository;
    public ObservableBoolean mProgresShow = new ObservableBoolean(false);


    public MovieListViewModel(@NonNull MoviesRepository moviesRepository, String category, int page) {
        mObservableMovies = new MutableLiveData<>();
        this.mMoviesRepository = moviesRepository;
        getMoviesFromNetwork(category, page);
    }

    /**
     * Expose the LiveData Movies query so the UI can observe it.
     */
    public LiveData<List<Movie>> getObservableMovies() {
        return mObservableMovies;
    }

    public void getMoviesFromNetwork(String category, int page) {
//        Log.d("MovieListViewModel", "getMoviesFromNetwork: ");
        mProgresShow.set(true);
        mMoviesRepository.getMoviesLiveData(category, page, new  ResponseCallback<Results>(){
         @Override
         public void successFromNetwork(Results results) {
             mProgresShow.set(false);
             mObservableMovies.setValue(results.getMovies());
         }

         @Override
         public void successFromDatabase(Results results) {
             mProgresShow.set(false);
             mObservableMovies.setValue(results.getMovies());
         }

         @Override
         public void failure(Call call, NetworkError error) {
 //            mView.showProgress(false);
             mProgresShow.set(false);
         }

         @Override
         public void onTimeOut(Call call) {
 //            mView.showProgress(false);
             mProgresShow.set(false);
         }
     });
    }

    public void setObservableMovies(MutableLiveData<List<Movie>> mObservableMovies) {
        this.mObservableMovies = mObservableMovies;
    }
}
