package com.jacobgreenland.itunesparsenavigation.data;

import android.content.Context;

import com.jacobgreenland.itunesparsenavigation.data.local.LocalSongSource;
import com.jacobgreenland.itunesparsenavigation.data.remote.RemoteSongSource;

/**
 * Created by Jacob on 17/06/16.
 */
public class SongRepository {

    private LocalSongSource localSource;

    private RemoteSongSource remoteSource;

    public SongRepository(Context c)
    {
        this.localSource = new LocalSongSource(c);
        this.remoteSource = new RemoteSongSource();
    }

    public RemoteSongSource getRemoteSource()
    {
        return this.remoteSource;
    }
    public void setRemoteSource(RemoteSongSource remote)
    {
        this.remoteSource = remote;
    }

    public LocalSongSource getLocalSource()
    {
        return this.localSource;
    }
    public void setLocalSource(LocalSongSource local)
    {
        this.localSource = local;
    }
}
