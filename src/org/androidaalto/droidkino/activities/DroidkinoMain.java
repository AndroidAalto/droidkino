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

import java.util.List;

import org.androidaalto.droidkino.DroidKinoApplicationCache;
import org.androidaalto.droidkino.DroidKinoIntent;
import org.androidaalto.droidkino.R;
import org.androidaalto.droidkino.adapter.MovieListAdapter;
import org.androidaalto.droidkino.beans.MovieInfo;
import org.androidaalto.droidkino.service.DataFetchService;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.widget.Toast;

/**
 * Main activity of the application which is a layout with
 * one fragment for the movie list and optionally a second 
 * fragment for the movie detail (if the screen is wide enough)
 * 
 * @author marcostong17
 *
 */
public class DroidkinoMain extends FragmentActivity {

    public static final String LOG_TAG = DroidkinoMain.class.getCanonicalName();

    public static final String APP_PREFS_FILE = "appx prefs file";

    
    private ProgressDialog fetchingXmlProgress;

    private List<MovieInfo> movieList;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    @Override
    public void onStart() {
        super.onStart();
        
        IntentFilter filter = new IntentFilter(DroidKinoIntent.FETCH_COMPLETE.getAction());
        filter.addAction(DroidKinoIntent.FETCH_FAILED.getAction());
        registerReceiver(mBroadcastReceiver, filter);

        DroidKinoApplicationCache cache = DroidKinoApplicationCache.getInstance();
        if (cache.getMovies().size() > 0) {
            movieList = cache.getMovies();
            publishListFragment();
            return;
        }

        final SharedPreferences prefs = getSharedPreferences(APP_PREFS_FILE, MODE_PRIVATE);

        if (prefs.getBoolean(DataFetchService.SERVICE_WORKING, false)) {
            return;
        }

        fetchingXmlProgress = ProgressDialog.show(this, "", getString(R.string.fetching_movies));

        startDataFetchService();
    }
    

   
    
    private void startDataFetchService() {
        Intent serviceIntent = new Intent(this, DataFetchService.class);
        serviceIntent.putExtra(DataFetchService.DATA_TO_FETCH, DroidKinoIntent.MOVIE_LIST_EXTRA);
        startService(serviceIntent);
    }

    
    
    /***
     * BroadReceiver for getting the result of the data fetching
     * specifically the list of MovieInfo beans (DroidKinoIntent.MOVIE_LIST_EXTRA)
     * and it puts it into the DroidKinoApplicationCache
     */
    final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @SuppressWarnings("unchecked")
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(LOG_TAG, "onReceive");

            fetchingXmlProgress.dismiss();

            if (intent.getAction().equals(DroidKinoIntent.FETCH_FAILED.getAction())) {
                Toast.makeText(getBaseContext(), "Fetch failed", Toast.LENGTH_SHORT).show();
            } else {
                movieList = (List<MovieInfo>) intent
                        .getSerializableExtra(DroidKinoIntent.MOVIE_LIST_EXTRA);
                DroidKinoApplicationCache cache = DroidKinoApplicationCache.getInstance();
                cache.getMovies().addAll(movieList);
                publishListFragment();
            }
        }
    };
    
    private void publishListFragment() {
        ListFragment  movieListFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.list);
        MovieListAdapter adapter = new MovieListAdapter(getBaseContext(), movieList);

        movieListFragment.setListAdapter(adapter);

        adapter.sortByTitle();
    }
    
    @Override
    public void onStop() {
        this.unregisterReceiver(mBroadcastReceiver);
        super.onStop();
    }
}
