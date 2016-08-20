/*
 *
 *  * Copyright © 2016, Robosoft Technologies Pvt. Ltd
 *  * Written under contract by Robosoft Technologies Pvt. Ltd.
 *
 */

package com.air.movieapp;

import android.app.Application;

import com.air.movieapp.network.RestClient;

/**
 * Created by sagar on 20/8/16.
 */
public class MovieApplication extends Application {

    private RestClient mRestClient;

//TODO: Move this class out of application class if possible
    public RestClient getRestClient() {
        if (mRestClient == null) {
            synchronized (RestClient.class) {
                mRestClient = new RestClient();
            }
        }
        return mRestClient;
    }

}
