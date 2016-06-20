package com.jacobgreenland.itunesparsenavigation.adapter;

/**
 * Created by Jacob on 09/06/16.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jacobgreenland.itunesparsenavigation.DetailsScreen;
import com.jacobgreenland.itunesparsenavigation.MainActivity;
import com.jacobgreenland.itunesparsenavigation.R;
import com.jacobgreenland.itunesparsenavigation.model.Result;
import com.jacobgreenland.itunesparsenavigation.utilities.ItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;


public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder>{

    private List<Result> Results;
    private int rowLayout;
    private Context mContext;
    DetailsScreen mFragment;

    private CompositeSubscription _subscriptions = new CompositeSubscription();
    private ProgressDialog pDialog;


    public ResultAdapter(List<Result> r, int rowLayout, Context context) {

        this.Results= r;
        this.rowLayout = rowLayout;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final Result Result = Results.get(i);
        viewHolder.ResultName.setText(Result.getTrackName());
        viewHolder.ResultDescription.setText(Result.getArtistName());
        Picasso.with(mContext)
                .load(Result.getArtworkUrl100())
                .into( viewHolder.ResultArtwork);
        viewHolder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                MainActivity.chosenSong = Result;
                    fragmentJump(view);
                    //((MyApp) mContext.getApplicationContext()).getApiComponent().inject((MainActivity) mContext);
                    //Toast.makeText(mContext, "#" + position + " - " + Result.getTrackName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fragmentJump(View view) {
        //call switch content to proceed with changing fragment
        mFragment = new DetailsScreen();
        switchContent(R.id.mainFragment, mFragment, view);
    }

    public void switchContent(int id, DetailsScreen fragment, View view) {
        if (mContext == null) {
            Log.d("test", "this isn't good");
            return;
        }
        // jump to main activity to switch fragment
        Log.d("test", "this is better");
        MainActivity mainActivity = (MainActivity) mContext;
        DetailsScreen frag = fragment;
        mainActivity.switchContent(id, frag, view);
        //}
    }

    private void hidePDialog()
    {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public int getItemCount() {
        return Results == null ? 0 : Results.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        @BindView(R.id.songName) TextView ResultName;
        @BindView(R.id.songDescription) TextView ResultDescription;
        @BindView(R.id.songArtwork) ImageView ResultArtwork;
        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }
        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getPosition());
            return true;
        }
    }
}
