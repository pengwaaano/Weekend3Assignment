package com.jacobgreenland.itunesparsenavigation.utilities;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Jacob on 11/06/16.
 */
public interface Communicator {
    public void setUI(ImageView art, TextView name, TextView price, TextView description, Button add);
    public void displayInformation();
}
