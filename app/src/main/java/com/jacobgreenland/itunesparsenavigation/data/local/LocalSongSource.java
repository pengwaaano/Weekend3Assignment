package com.jacobgreenland.itunesparsenavigation.data.local;

import android.content.Context;
import android.util.Log;

import com.jacobgreenland.itunesparsenavigation.model.Result;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by Jacob on 17/06/16.
 */
public class LocalSongSource {

    RealmConfiguration realmConfig;
    Realm realm;

    public LocalSongSource(Context context)
    {
        realmConfig = new RealmConfiguration.Builder(context).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfig);

        realm = Realm.getDefaultInstance();
    }

    public void addData(List<Result> results, String type)
    {
        realm.beginTransaction();
        Log.d("test", "loading in data");
        for(int i = 0; i < results.size(); i++)
        {
            Result r = results.get(i);
            Log.d("test", r.getTrackName());
            r.setType(type);
            final Result finalResult = realm.copyToRealm(r);
        }
        realm.commitTransaction();
    }

    public List<Result> getDataFromLocal(String genre)
    {
        Log.d("test", "local data loading");
        RealmResults<Result> result2 = realm.where(Result.class)
                .equalTo("type", genre)
                .findAll();
        Log.d("test", result2.get(0).getTrackName());
        return result2;
    }
}
