package com.jacobgreenland.itunesparsenavigation.pop;

import com.jacobgreenland.itunesparsenavigation.data.SongRepository;
import com.jacobgreenland.itunesparsenavigation.model.Results;
import com.jacobgreenland.itunesparsenavigation.observables.ISongAPI;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Jacob on 16/06/16.
 */
public class PopPresenter implements PopContract.Presenter {

    private final SongRepository songRepository;

    private final PopContract.View mView;

    private String s;

    private CompositeSubscription _subscriptions = new CompositeSubscription();

    Results results;
    int i = 0;

    @Inject
    public PopPresenter(SongRepository songRepo, PopContract.View view) {
        mView = view;
        songRepository = songRepo;

        mView.setPopPresenter(this);
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
    public void loadPopSongs(ISongAPI _api, final boolean initialLoad)
    {
        songRepository.getRemoteSource().getPopSongs(_api, initialLoad, mView,songRepository);
    }
    @Override
    public void loadLocalPopSongs()
    {
        mView.setAdapters(songRepository.getLocalSource().getDataFromLocal("Pop"),false);
    }

    @Inject
    @Override
    public void start() { }
}
