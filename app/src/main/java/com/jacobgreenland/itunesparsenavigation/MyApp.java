package com.jacobgreenland.itunesparsenavigation;

import android.app.Application;

import com.jacobgreenland.itunesparsenavigation.injection.components.APIComponents;
import com.jacobgreenland.itunesparsenavigation.injection.components.DaggerAPIComponents;
import com.jacobgreenland.itunesparsenavigation.injection.components.DaggerNetComponent;
import com.jacobgreenland.itunesparsenavigation.injection.components.NetComponent;
import com.jacobgreenland.itunesparsenavigation.injection.modules.APIModule;
import com.jacobgreenland.itunesparsenavigation.injection.modules.AppModule;
import com.jacobgreenland.itunesparsenavigation.injection.modules.NetModule;
import com.jacobgreenland.itunesparsenavigation.utilities.Constants;


/**
 * Created by Jacob on 14/06/16.
 */
public class MyApp extends Application {

    private NetComponent mNetComponent;
    private APIComponents mApiComponents;
    @Override
    public void onCreate() {
        super.onCreate();
        mNetComponent = DaggerNetComponent.builder()
                .netModule(new NetModule(Constants.BASE_URL))
                .appModule(new AppModule(this))
                .build();

        mApiComponents = DaggerAPIComponents.builder()
                .netComponent(mNetComponent)
                .aPIModule(new APIModule())
                .build();
    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }

    public APIComponents getApiComponent() {
        return mApiComponents;
    }

}
