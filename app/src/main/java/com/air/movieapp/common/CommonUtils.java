/*
 *
 *  * Copyright © 2016, Robosoft Technologies Pvt. Ltd
 *  * Written under contract by Robosoft Technologies Pvt. Ltd.
 *
 */

package com.air.movieapp.common;

import android.os.Bundle;

/**
 * Created by sagar on 20/8/16.
 */
public class CommonUtils {

    public static Bundle getBundleWithValue(int pos){
        Bundle bundle = new Bundle();
        switch (pos){
            case 0:
                bundle.putString(Constants.SCREEN, Constants.TOP_RATED);
                break;
            case 1:
                bundle.putString(Constants.SCREEN, Constants.UPCOMING);
                break;
            case 2:
                bundle.putString(Constants.SCREEN, Constants.POPULAR);
                break;
        }
        return bundle;
    }

}
