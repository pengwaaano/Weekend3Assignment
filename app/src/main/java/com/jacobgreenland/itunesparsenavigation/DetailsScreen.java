package com.jacobgreenland.itunesparsenavigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jacobgreenland.itunesparsenavigation.utilities.Communicator;

/**
 * Created by Jacob on 11/06/16.
 */
public class DetailsScreen extends Fragment {

    Communicator comm;
    View v;
    ImageView artwork;
    TextView title;
    TextView artist;
    TextView description;
    Button addToBag;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.song_details,container,false);

        comm = (Communicator) getActivity();
        return v;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        artwork = (ImageView) v.findViewById(R.id.sArtwork);
        title = (TextView) v.findViewById(R.id.sName);
        artist = (TextView) v.findViewById(R.id.sArtist);
        description = (TextView) v.findViewById(R.id.sDescription);
        addToBag = (Button) v.findViewById(R.id.pd_AddToBag);
        comm.setUI(artwork,title,artist, description, addToBag);
        comm.displayInformation();

        Log.d("test", "Adapter should have been set by now!");
    }
}
