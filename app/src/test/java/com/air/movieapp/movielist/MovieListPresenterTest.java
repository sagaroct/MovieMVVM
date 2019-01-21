package com.air.movieapp.movielist;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Observer;

import com.air.movieapp.common.Constants;
import com.air.movieapp.model.Movie;
import com.air.movieapp.model.Results;
import com.air.movieapp.network.MoviesRepository;
import com.air.movieapp.network.NetworkUtils;
import com.air.movieapp.network.ResponseCallback;
import com.air.movieapp.viewmodel.MovieListViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by sagar on 14/12/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class MovieListPresenterTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private List<Movie> MOVIES = Arrays.asList(new Movie("someTitle", "1992-12-10"
        , "soneOverView", 1.00f, Constants.POPULAR ));

    private Results results = new Results();

    private static List<Movie> EMPTY_MOVIES = new ArrayList<>(0);

    @Mock
    private NetworkUtils mNetworkUtils;

    @Mock
    private MoviesRepository mMoviesRepository;

    @Captor
    private ArgumentCaptor<ResponseCallback> mLoadMoviesCallbackCaptor;

    private MovieListViewModel movieListViewModel;

    @Mock
    private Observer<List<Movie>> observer;

    @Before
    public void setupMovieListPresenter() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        movieListViewModel = new MovieListViewModel(mMoviesRepository, Constants.POPULAR, 1);
    }

    @Test
    public void loadMoviesFromRepositoryAndLoadIntoView() {
        // Given an initialized MovieListPresenter with initialized movies
        // When loading of Movies is requested
//        when(mNetworkUtils.isNetworkConnected()).thenReturn(true);
        movieListViewModel.getMoviesFromNetwork(Constants.POPULAR, 1);
        // Callback is captured and invoked with stubbed movies
        verify(mMoviesRepository).getMoviesLiveData(eq(Constants.POPULAR), eq(1), mLoadMoviesCallbackCaptor.capture());
        results.setMovies(MOVIES);
        mLoadMoviesCallbackCaptor.getValue().successFromNetwork(results);
        movieListViewModel.getObservableMovies().observeForever(observer);
        verify(observer).onChanged((MOVIES));

        // Then progress indicator is hidden and movies are shown in UI.
       /* verify(mView, atMost(2)).showProgress(false);
        verify(mView, atMost(2)).showList(MOVIES);*/
    }

    /*@Test
    public void loadEmptyMoviesIntoView() {
        when(mNetworkUtils.isNetworkConnected()).thenReturn(true);
        // Given an initialized MovieListPresenter with initialized movies
        // When loading of Movies is requested
        movieListViewModel.fetchMovies(Constants.POPULAR);

        // Callback is captured and invoked with stubbed movies
        verify(mMoviesRepository).getMovies(eq(Constants.POPULAR), eq(1), mLoadMoviesCallbackCaptor.capture());
        results.setMovies(EMPTY_MOVIES);
        mLoadMoviesCallbackCaptor.getValue().successFromNetwork(results);

        // Then progress indicator is hidden and emptyview is shown in UI.
        verify(mView, atMost(2)).showProgress(false);
        verify(mView).showEmptyView();
    }

    @Test
    public void loadMoviesWithNoInternetError() {
        // Given an initialized MovieListPresenter with initialized movies
        // When loading of Movies is requested
        when(mNetworkUtils.isNetworkConnected()).thenReturn(false);
        movieListViewModel.fetchMovies(Constants.POPULAR);
//        assertEquals(true, mNetworkUtils.isNetworkConnected());
//        doReturn(false).when(mNetworkUtils).isNetworkConnected();

        // No internet toast shown and emptyview displayed.
        verify(mView).showNoInternetDialog();
//        verify(mView).showEmptyView();
    }*/
}
