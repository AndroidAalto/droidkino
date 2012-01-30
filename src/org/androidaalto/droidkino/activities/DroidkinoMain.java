/*******************************************************************************

   Copyright: 2011 Android Aalto Community

   This file is part of Droidkino.

   Droidkino is free software; you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation; either version 2 of the License, or
   (at your option) any later version.

   Droidkino is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with Droidkino; if not, write to the Free Software
   Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

 ******************************************************************************/

package org.androidaalto.droidkino.activities;

import org.androidaalto.droidkino.DroidKinoApplicationCache;
import org.androidaalto.droidkino.DroidKinoIntent;
import org.androidaalto.droidkino.R;
import org.androidaalto.droidkino.beans.MovieInfo;
import org.androidaalto.droidkino.service.DataFetchService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

/**
 * Main activity of the application.It first checks if the MovieInfo list is in
 * the DroidKinoApplicationCache to grab the list from there. Otherwise, it
 * calls the DataFetchService which downloads the info from the FinnKino server.
 */
public class DroidkinoMain extends FragmentActivity {
    public static final String APP_PREFS_FILE = "appx prefs file";

    public static final String LOG_TAG = DroidkinoMain.class.getCanonicalName();

    /***
     * BroadcastReceiver for getting the result of the data fetching
     * specifically the list of MovieInfo beans
     * (DroidKinoIntent.MOVIE_LIST_EXTRA) and it puts it into the
     * DroidKinoApplicationCache
     */
    final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @SuppressWarnings("unchecked")
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(LOG_TAG, "onReceive");
            if (intent.getAction().equals(DroidKinoIntent.FETCH_FAILED.getAction())) {
                Toast.makeText(DroidkinoMain.this, "Fetch failed", Toast.LENGTH_SHORT).show();
            } else {
                List<MovieInfo> movieList = (List<MovieInfo>) intent
                        .getSerializableExtra(DroidKinoIntent.MOVIE_LIST_EXTRA);
                DroidKinoApplicationCache cache = DroidKinoApplicationCache.getInstance();
                cache.getMovies().addAll(movieList);
                publishList(movieList);
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        startFetchMovieListService();
    }

    /**
     * Initializes the MovieInfo list, either from the DroidKinoApplicationCache
     * if it was already retrieved otherwise it triggers the DataFetchService to
     * download it from the FinnKino server
     */
    private void startFetchMovieListService() {
        IntentFilter filter = new IntentFilter(DroidKinoIntent.FETCH_COMPLETE.getAction());
        filter.addAction(DroidKinoIntent.FETCH_FAILED.getAction());
        registerReceiver(mBroadcastReceiver, filter);

        DroidKinoApplicationCache cache = DroidKinoApplicationCache.getInstance();
        if (cache.getMovies().size() > 0) {
            List<MovieInfo> movieList = cache.getMovies();
            publishList(movieList);
            return;
        }

        final SharedPreferences prefs = getSharedPreferences(APP_PREFS_FILE, MODE_PRIVATE);

        if (prefs.getBoolean(DataFetchService.SERVICE_WORKING, false)) {
            return;
        }
        startDataFetchService();
    }

    /**
     * @param movieList the movie list to use for the new fragment
     */
    private void publishList(List<MovieInfo> movieList) {
        MovieListFragment movieListFragment = (MovieListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if (movieListFragment == null
                || movieListFragment.getCurrentMovieListHashCode() != movieList.hashCode()) {
            Log.d(LOG_TAG, "Creating movie list fragment with " + movieList.size() + " elements");

            // Make new fragment to show the movie list.
            movieListFragment = MovieListFragment.newInstance(movieList);

            // Execute a transaction, replacing any existing fragment
            // with this one inside the frame.
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, movieListFragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }
    }

    private void startDataFetchService() {
        Intent serviceIntent = new Intent(this, DataFetchService.class);
        serviceIntent.putExtra(DataFetchService.DATA_TO_FETCH, DroidKinoIntent.MOVIE_LIST_EXTRA);
        startService(serviceIntent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // gather references to the buttons
        Button searchScheduleButton = (Button) findViewById(R.id.searchScheduleButton);

        searchScheduleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent searchScheduleIntent = new Intent(DroidkinoMain.this, SearchSchedule.class);
                startActivity(searchScheduleIntent);
            }
        });

        // Create dummy empty fragment. Used to display a progress icon
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, MovieListFragment.newInstance(null));
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mBroadcastReceiver);
    }
}
