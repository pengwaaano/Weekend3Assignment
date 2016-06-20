package com.jacobgreenland.itunesparsenavigation.services;

import com.jacobgreenland.itunesparsenavigation.observables.ISongAPI;
import com.jacobgreenland.itunesparsenavigation.utilities.Constants;

import retrofit.RestAdapter;

/**
 * Created by Jacob on 09/06/16.
 */
public class Services {

    // -----------------------------------------------------------------------------------
    public static ISongAPI _createItemAPI() {

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(Constants.BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL);
        return builder.build().create(ISongAPI.class);
    }
}