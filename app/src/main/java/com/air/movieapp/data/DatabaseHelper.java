package com.air.movieapp.data;

import com.air.movieapp.model.Movie;

import java.util.List;


public class DatabaseHelper {

    private MovieDatabase mMovieDatabase;

    public DatabaseHelper(MovieDatabase movieDatabase) {
        this.mMovieDatabase = movieDatabase;
    }

    public List<Movie> getMovies(String category) {
       return mMovieDatabase.movieDao().getMoviesByCategory(category);
    }

    private void setCategoryToMovies(String categoryName, List<Movie> movies) {
        if (movies != null && !movies.isEmpty()) {
            for (Movie movie : movies) {
                movie.setType(categoryName);
            }
        }
    }

    public void saveMovieList(String categoryName, final List<Movie> movies) {
        setCategoryToMovies(categoryName, movies);
        mMovieDatabase.movieDao().insertAll(movies);
    }

}
