package com.jacobgreenland.itunesparsenavigation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telecom.ConnectionService;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jacobgreenland.itunesparsenavigation.classic.ClassicFragment;
import com.jacobgreenland.itunesparsenavigation.data.SongRepository;
import com.jacobgreenland.itunesparsenavigation.model.Result;
import com.jacobgreenland.itunesparsenavigation.observables.ISongAPI;
import com.jacobgreenland.itunesparsenavigation.pop.PopFragment;
import com.jacobgreenland.itunesparsenavigation.rock.RockFragment;
import com.jacobgreenland.itunesparsenavigation.utilities.Communicator;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Communicator {

    NavigationView navigationView;
    DrawerLayout drawer;
    FloatingActionButton fab;
    Toolbar toolbar;

    RealmConfiguration realmConfig;
    Realm realm;

    public static boolean isOnline = true;

    @Inject
    public static ISongAPI _api;

    ImageView detailsArtwork;
    TextView detailsName;
    TextView detailsArtist;
    TextView detailsDescription;

    BroadcastReceiver broadcastReceiver;

    public static SongRepository songRepository;

    public static Result chosenSong;

    public static Snackbar admiral;

    public static CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialiseToolbar();
        initialiseNavigationDrawer();

        MainFragment f = new MainFragment();

        Intent intent = new Intent(this, ConnectionService.class);
        startService(intent);

        songRepository = new SongRepository(getApplicationContext());

        ((MyApp) getApplication()).getApiComponent().inject(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        ft.replace(R.id.mainFragment, f, "tabs");
        ft.commit();

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.snackbarPosition);

        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "No Internet Connection", Snackbar.LENGTH_INDEFINITE);
        snackbar.show();

        installListener(snackbar);

        ClassicFragment frag = new ClassicFragment();
        RockFragment frag2 = new RockFragment();
        PopFragment frag3 = new PopFragment();

        if(MainActivity.isOnline) {
            frag.loadSongs();
            frag2.loadSongs();
            frag3.loadSongs();
        }
        else
        {
            frag.loadLocalSongs();
            frag2.loadLocalSongs();
            frag3.loadLocalSongs();
        }
        //admiral.make(coordinatorLayout, "No Internet Connection",Snackbar.LENGTH_INDEFINITE);
        //initialiseFloatingButton();
    }
    public void initialiseToolbar()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/
    }

    public void initialiseFloatingButton()
    {
        /*fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    public void initialiseNavigationDrawer()
    {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        toolbar.setNavigationIcon(R.drawable.ic_menu);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Log.d("test","" + id + " " + R.id.nav_classic);

        if (id == R.id.nav_classic)
        {
            Log.d("test", "classic");
                ClassicFragment cf = new ClassicFragment();
                switchFragment(cf);
        }
        else if (id == R.id.nav_pop)
        {
                PopFragment pf = new PopFragment();
                switchFragment(pf);
        }
        else if (id == R.id.nav_rock)
        {
                RockFragment rf = new RockFragment();
                switchFragment(rf);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void switchFragment(Fragment f)
    {
        //Set starting fragment to Tabs
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.mainFragment, f, "tabs");
        ft.commit();
    }

    public void switchContent(int id, Fragment fragment, View view) {
        //Switch Fragments method
        Log.d("Test", "Fragment changed!");

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(id, fragment, fragment.toString());
        ft.addToBackStack(null);
        ft.commit();
    }
    @Override
    public void setUI(ImageView art, TextView name, TextView artist, TextView description, Button add)
    {
        //Set UI elements for the details screen.
        detailsArtwork = art;
        detailsName = name;
        detailsArtist = artist;
        detailsDescription = description;
    }
    @Override
    public void displayInformation()
    {
        //display detail information
        Picasso.with(MainActivity.this)
                .load(chosenSong.getArtworkUrl100())
                .into( detailsArtwork);
        detailsName.setText(chosenSong.getTrackName());
        detailsArtist.setText(chosenSong.getArtistName());
        detailsDescription.setText("description");
    }

    private void installListener(final Snackbar snack) {

        if (broadcastReceiver == null) {

            broadcastReceiver = new BroadcastReceiver() {

                @Override
                public void onReceive(Context context, Intent intent) {

                    Bundle extras = intent.getExtras();

                    NetworkInfo info = (NetworkInfo) extras
                            .getParcelable("networkInfo");

                    NetworkInfo.State state = info.getState();
                    //Log.d("InternalBroadcastReceiver", info.toString() + " " + state.toString());

                    if (state == NetworkInfo.State.CONNECTED) {

                        MainActivity.isOnline = true;
                        snack.dismiss();
                        Log.d("tag", "" + MainActivity.isOnline);

                    } else {

                        MainActivity.isOnline = false;
                        snack.show();
                        Log.d("tag", "" + MainActivity.isOnline);
                    }

                }
            };

            final IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(broadcastReceiver, intentFilter);
        }
    }
}
