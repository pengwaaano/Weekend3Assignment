package com.jacobgreenland.itunesparsenavigation.classic;

import android.util.Log;

import com.jacobgreenland.itunesparsenavigation.data.SongRepository;
import com.jacobgreenland.itunesparsenavigation.model.Results;
import com.jacobgreenland.itunesparsenavigation.observables.ISongAPI;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Jacob on 16/06/16.
 */
public class ClassicPresenter implements ClassicContract.Presenter {

    private final SongRepository songRepository;

    private final ClassicContract.View mView;

    private String s;

    private CompositeSubscription _subscriptions = new CompositeSubscription();

    Results results;
    int i = 0;

    @Inject
    public ClassicPresenter(SongRepository songRepo, ClassicContract.View view) {
        mView = view;
        songRepository = songRepo;

        mView.setClassicPresenter(this);
    }

    @Override
    public SongRepository getRepository()
    {
        return songRepository;
    }

    @Override
    public void setRepository(SongRepository songRepo)
    {
        //this.songRepository = songRepo;
    }

    @Override
    public void loadUsers() {
        i++;
        s = "Users Loaded " + i;
        mView.showUsers(s);
    }

    @Override
    public void loadClassicSongs(ISongAPI _api, final boolean initialLoad)
    {
        songRepository.getRemoteSource().getClassicSongs(_api, initialLoad, mView,songRepository);
    }
    @Override
    public void loadLocalClassicSongs()
    {
        Log.d("test", "local data loading");
        mView.setAdapters(songRepository.getLocalSource().getDataFromLocal("Classic"),false);
    }

    @Inject
    @Override
    public void start() { }
}
