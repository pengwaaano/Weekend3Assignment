package com.jacobgreenland.itunesparsenavigation.injection.modules;


import com.jacobgreenland.itunesparsenavigation.injection.scope.UserScope;
import com.jacobgreenland.itunesparsenavigation.observables.ISongAPI;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

@Module
public class APIModule {


    @UserScope
    @Provides
    public ISongAPI providesItemsInterface(RestAdapter retrofit) {
        return retrofit.create(ISongAPI.class);
    }
}
