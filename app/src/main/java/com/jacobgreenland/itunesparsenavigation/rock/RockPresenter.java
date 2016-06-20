package com.jacobgreenland.itunesparsenavigation.rock;

import com.jacobgreenland.itunesparsenavigation.data.SongRepository;
import com.jacobgreenland.itunesparsenavigation.model.Results;
import com.jacobgreenland.itunesparsenavigation.observables.ISongAPI;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Jacob on 16/06/16.
 */
public class RockPresenter implements RockContract.Presenter {

    private final SongRepository songRepository;

    private final RockContract.View mView;

    private String s;

    private CompositeSubscription _subscriptions = new CompositeSubscription();

    Results results;
    int i = 0;

    @Inject
    public RockPresenter(SongRepository songRepo, RockContract.View view) {
        mView = view;
        songRepository = songRepo;

        mView.setRockPresenter(this);
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
    public void loadRockSongs(ISongAPI _api, final boolean initialLoad)
    {
        songRepository.getRemoteSource().getRockSongs(_api, initialLoad, mView,songRepository);
        /*_subscriptions.add(_api.getRockSongs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(5000, TimeUnit.MILLISECONDS)
                .retry()
                .distinct()
                .subscribe(new Observer<Results>() {
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Retrofit", "Error");
                    }
                    @Override
                    public void onCompleted() {
                        Log.i("Retrofit", "onCompleted");
                        if(initialLoad)
                            songRepository.getLocalSource().addData(results.getResults(), "Rock");
                        else
                            mView.setAdapters(results.getResults(), true);
                    }
                    @Override
                    public void onNext(Results results2) {
                        Log.i("Retrofit", "onNext");
                        results = results2;
                    }
                }));*/
    }
    @Override
    public void loadLocalRockSongs()
    {
        mView.setAdapters(songRepository.getLocalSource().getDataFromLocal("Rock"),false);
    }

    @Inject
    @Override
    public void start() {

    }
}
