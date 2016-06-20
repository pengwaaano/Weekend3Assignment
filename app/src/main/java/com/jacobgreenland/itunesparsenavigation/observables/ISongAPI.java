package com.jacobgreenland.itunesparsenavigation.observables;

import com.jacobgreenland.itunesparsenavigation.model.Results;
import com.jacobgreenland.itunesparsenavigation.utilities.Constants;

import retrofit.http.GET;
import rx.Observable;

/**
 * Created by Jacob on 10/06/16.
 */
public interface ISongAPI {
    @GET(Constants.CLASSIC_URL)
    Observable<Results> getClassicSongs();
    @GET(Constants.POP_URL)
    Observable<Results> getPopSongs();
    @GET(Constants.ROCK_URL)
    Observable<Results> getRockSongs();
}
