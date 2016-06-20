package com.jacobgreenland.itunesparsenavigation.pop;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.jacobgreenland.itunesparsenavigation.MainActivity;
import com.jacobgreenland.itunesparsenavigation.R;
import com.jacobgreenland.itunesparsenavigation.adapter.ResultAdapter;
import com.jacobgreenland.itunesparsenavigation.model.Result;

import java.util.List;

/**
 * Created by Jacob on 15/06/16.
 */
public class PopFragment extends Fragment implements PopContract.View
{

    RecyclerView rv;
    SwipeRefreshLayout mSwipeRefreshLayout;
    PopContract.Presenter fPresenter;

    RecyclerViewHeader header;
    View mContainerHeader;
    ObjectAnimator fade;
    View v;

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
                fPresenter.loadPopSongs(MainActivity._api, false);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        rv.setLayoutManager(new LinearLayoutManager(v.getContext()));
        rv.setItemAnimator(new DefaultItemAnimator());

        fade = ObjectAnimator.ofFloat(mContainerHeader, "alpha", 0f, 1f);
        fade.setInterpolator(new DecelerateInterpolator());
        fade.setDuration(400);

        //header.attachTo(rv);

        fPresenter = new PopPresenter(MainActivity.songRepository, this);
        fPresenter.loadLocalPopSongs();
    }
    public void loadSongs()
    {
        fPresenter = new PopPresenter(MainActivity.songRepository,this);
        fPresenter.loadPopSongs(MainActivity._api, false);
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
            fPresenter.getRepository().getLocalSource().addData(results, "Pop");
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
    public void setClassicPresenter(PopContract.Presenter presenter) {

    }

    @Override
    public void setPopPresenter(PopContract.Presenter presenter) {
        fPresenter = presenter;
    }

    @Override
    public void setRockPresenter(PopContract.Presenter presenter) {

    }
}

