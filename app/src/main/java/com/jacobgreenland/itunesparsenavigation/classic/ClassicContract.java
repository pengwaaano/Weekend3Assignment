package com.jacobgreenland.itunesparsenavigation.classic;

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
public interface ClassicContract {

    interface View extends BaseView<Presenter> {
        void showUsers(String s);
        void setAdapters(List<Result> results, boolean fromAPI);
        void showDialog();
        Context getApplicationContext();
    }

    interface Presenter extends BasePresenter {
        void loadUsers();
        void loadClassicSongs(ISongAPI _api, final boolean initialLoad);
        void loadLocalClassicSongs();
        SongRepository getRepository();
        void setRepository(SongRepository songRepo);
    }
}
