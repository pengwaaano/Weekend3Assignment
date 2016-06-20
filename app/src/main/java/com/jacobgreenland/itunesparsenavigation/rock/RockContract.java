package com.jacobgreenland.itunesparsenavigation.rock;

import android.content.Context;

import com.jacobgreenland.itunesparsenavigation.BasePresenter;
import com.jacobgreenland.itunesparsenavigation.BaseView;
import com.jacobgreenland.itunesparsenavigation.data.SongRepository;
import com.jacobgreenland.itunesparsenavigation.model.Result;
import com.jacobgreenland.itunesparsenavigation.observables.ISongAPI;

import java.util.List;

/**
 * Created by Jacob on 16/06/16.
 */
public interface RockContract {

    interface View extends BaseView<RockContract.Presenter> {
        void showUsers(String s);
        void setAdapters(List<Result> results, boolean fromAPI);
        void showDialog();
        Context getApplicationContext();
    }

    interface Presenter extends BasePresenter {
        void loadUsers();
        void loadRockSongs(ISongAPI _api, final boolean initialLoad);
        void loadLocalRockSongs();
        SongRepository getRepository();
        void setRepository(SongRepository songRepo);
    }
}
