package com.jacobgreenland.itunesparsenavigation.injection.modules;

import com.jacobgreenland.itunesparsenavigation.classic.ClassicContract;

import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 */
@Module
public class PresenterModule {

    private final ClassicContract.View mView;

    public PresenterModule(ClassicContract.View view) { mView = view; }

    @Provides
    ClassicContract.View provideTasksContractView()
    {
        return mView;
    }
}
