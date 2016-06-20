package com.jacobgreenland.itunesparsenavigation.data.remote;

import android.util.Log;

import com.jacobgreenland.itunesparsenavigation.classic.ClassicContract;
import com.jacobgreenland.itunesparsenavigation.data.SongRepository;
import com.jacobgreenland.itunesparsenavigation.model.Results;
import com.jacobgreenland.itunesparsenavigation.observables.ISongAPI;
import com.jacobgreenland.itunesparsenavigation.pop.PopContract;
import com.jacobgreenland.itunesparsenavigation.rock.RockContract;

import java.util.concurrent.TimeUnit;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Jacob on 17/06/16.
 */
public class RemoteSongSource {

    private CompositeSubscription _subscriptions = new CompositeSubscription();

    Results results;

    public RemoteSongSource()
    {

    }

    public void getClassicSongs(ISongAPI _api, final boolean initialLoad, final ClassicContract.View mView, final SongRepository songRepository)
    {
        _subscriptions.add(_api.getClassicSongs()
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
                            songRepository.getLocalSource().addData(results.getResults(), "Classic");
                        else
                            mView.setAdapters(results.getResults(), true);

                    }
                    @Override
                    public void onNext(Results results2) {
                        Log.i("Retrofit", "onNext");

                        results = results2;
                    }
                }));
    }
    public void getRockSongs(ISongAPI _api, final boolean initialLoad, final RockContract.View mView, final SongRepository songRepository)
    {
        _subscriptions.add(_api.getRockSongs()
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
                }));
    }
    public void getPopSongs(ISongAPI _api, final boolean initialLoad, final PopContract.View mView, final SongRepository songRepository)
    {
        _subscriptions.add(_api.getPopSongs()
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
                            songRepository.getLocalSource().addData(results.getResults(), "Pop");
                        else
                            mView.setAdapters(results.getResults(), true);
                    }
                    @Override
                    public void onNext(Results results2) {
                        Log.i("Retrofit", "onNext");

                        results = results2;
                    }
                }));
    }
}
