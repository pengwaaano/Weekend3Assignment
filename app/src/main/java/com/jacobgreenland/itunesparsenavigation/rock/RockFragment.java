package com.jacobgreenland.itunesparsenavigation.rock;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.jacobgreenland.itunesparsenavigation.MainActivity;
import com.jacobgreenland.itunesparsenavigation.R;
import com.jacobgreenland.itunesparsenavigation.adapter.ResultAdapter;
import com.jacobgreenland.itunesparsenavigation.model.Result;

import java.util.List;

/**
 * Created by Jacob on 15/06/16.
 */
public class RockFragment extends Fragment implements RockContract.View{

    RecyclerView rv;
    SwipeRefreshLayout mSwipeRefreshLayout;

    RockContract.Presenter fPresenter;
    View v;

    RecyclerViewHeader header;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.song_recyclerview,container,false);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rv = (RecyclerView) v.findViewById(R.id.songList);

        header = (RecyclerViewHeader) v.findViewById(R.id.header);


        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swiperefresh);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(MainActivity.isOnline)
                    fPresenter.loadRockSongs(MainActivity._api, false);
                else {
                    Snackbar.make(v.findViewById(R.id.snackbarPosition2), "No Internet Connection", Snackbar.LENGTH_SHORT).show();
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        rv.setLayoutManager(new LinearLayoutManager(v.getContext()));
        rv.setItemAnimator(new DefaultItemAnimator());

        fPresenter = new RockPresenter(MainActivity.songRepository,this);
        //fPresenter.loadRockSongs(MainActivity._api, false);
        //header.attachTo(rv);
        fPresenter.loadLocalRockSongs();
    }

    public void loadSongs()
    {
        fPresenter = new RockPresenter(MainActivity.songRepository,this);
        fPresenter.loadRockSongs(MainActivity._api, false);
    }
    public void loadLocalSongs()
    {
        fPresenter = new RockPresenter(MainActivity.songRepository,this);
        fPresenter.loadLocalRockSongs();
    }

    @Override
    public void showDialog()
    {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showUsers(String s) {

    }

    @Override
    public void setAdapters(List<Result> results, boolean fromAPI)
    {
        //Log.d("test", results.getResults().get(0).getTrackName());
        ResultAdapter mAdapterClassic = new ResultAdapter(results, R.layout.song_row,  v.getContext());
        rv.setAdapter(mAdapterClassic);
        if(fromAPI)
            fPresenter.getRepository().getLocalSource().addData(results, "Rock");
    }

    @Override
    public Context getApplicationContext() {
        return null;
    }

    @Override
    public void onResume() {
        super.onResume();
        fPresenter.start();
    }

    @Override
    public void setClassicPresenter(RockContract.Presenter presenter) {

    }

    @Override
    public void setPopPresenter(RockContract.Presenter presenter) {

    }

    @Override
    public void setRockPresenter(RockContract.Presenter presenter) {

        fPresenter = presenter;
    }
}
