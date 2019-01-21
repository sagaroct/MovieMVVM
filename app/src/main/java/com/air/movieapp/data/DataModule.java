package com.air.movieapp.data;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class DataModule {

    private Context mContext;

    public DataModule(Context mContext) {
        this.mContext = mContext;
    }

    @Provides
    @Singleton
    public DatabaseHelper provideDatabaseHelper() {

        MovieDatabase movieDatabase = MovieDatabase.getInstance(mContext);

        return new DatabaseHelper(movieDatabase);
    }


}
