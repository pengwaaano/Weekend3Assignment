package com.jacobgreenland.itunesparsenavigation.injection.components;

import com.jacobgreenland.itunesparsenavigation.MainActivity;
import com.jacobgreenland.itunesparsenavigation.injection.modules.APIModule;
import com.jacobgreenland.itunesparsenavigation.injection.scope.UserScope;

import dagger.Component;


/**
 * Created by kalpesh on 20/01/2016.
 */

    @UserScope
    @Component(dependencies =NetComponent.class, modules = APIModule.class)
    public interface APIComponents {

    void inject(MainActivity activity);
}
