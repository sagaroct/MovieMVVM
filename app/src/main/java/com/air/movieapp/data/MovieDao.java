package com.air.movieapp.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.air.movieapp.model.Movie;

import java.util.List;

/**
 * Created by sagar on 22/9/17.
 */

@Dao
public interface MovieDao {

    @Query("SELECT * FROM Movie")
    List<Movie> getAll();


    @Query("SELECT * FROM Movie WHERE type = :category")
    List<Movie> getMoviesByCategory(String category);

    @Insert
    void insertAll(List<Movie> movies);

    @Delete
    void delete(Movie movie);



}
