
package org.androidaalto.droidkino;

import java.util.List;

import org.androidaalto.droidkino.adapter.MovieListAdapter;
import org.androidaalto.droidkino.service.DataFetchService;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class MovieList extends ListActivity {
    
    public static final String LOG_TAG = MovieList.class.getCanonicalName();

    private ProgressDialog fetchingXmlProgress;

    public static final String APP_PREFS_FILE = "appx prefs file";

    private List<MovieInfo> movieList;
    
    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter filter = new IntentFilter(DroidKinoIntent.FETCH_COMPLETE.getAction());
        filter.addAction(DroidKinoIntent.FETCH_FAILED.getAction());
        registerReceiver(mBroadcastReceiver, filter);

        DroidKinoApplication app = (DroidKinoApplication) getApplication();
        if (app != null && app.getMovies().size() > 0) {
            movieList = app.getMovies();
            publishListAdapters();
            return;
        }

        final SharedPreferences prefs = getSharedPreferences(APP_PREFS_FILE, MODE_PRIVATE);

        if (prefs.getBoolean(DataFetchService.SERVICE_WORKING, false)) {
            return;
        }

        fetchingXmlProgress = ProgressDialog.show(this, "", getString(R.string.fetching_movies));

        startDataFetchService();
    }
    
    /***
     * BroadReceiver for getting the result of the data fetching
     */
    final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @SuppressWarnings("unchecked")
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(LOG_TAG, "onReceive");

            fetchingXmlProgress.dismiss();

            if (intent.getAction().equals(DroidKinoIntent.FETCH_FAILED.getAction())) {
                Toast.makeText(MovieList.this, "Fetch failed", Toast.LENGTH_SHORT).show();
            } else {
                movieList = (List<MovieInfo>) intent
                        .getSerializableExtra(DroidKinoIntent.MOVIE_LIST_EXTRA);
                DroidKinoApplication app = (DroidKinoApplication) getApplication();
                app.setMovies(movieList);
                publishListAdapters();
            }
        }
    };
    
    private void publishListAdapters() {
        MovieListAdapter adapter = new MovieListAdapter(MovieList.this, movieList);

        setListAdapter(adapter);

        adapter.sortByTitle();
    }
    private void startDataFetchService() {
        Intent serviceIntent = new Intent(MovieList.this, DataFetchService.class);
        serviceIntent.putExtra(DataFetchService.DATA_TO_FETCH, DroidKinoIntent.MOVIE_LIST_EXTRA);
        startService(serviceIntent);
    }

    @Override
    protected void onStop() {
        unregisterReceiver(mBroadcastReceiver);
        super.onStop();
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(MovieList.this, MovieDetail.class);
        i.putExtra(MovieDetail.MOVIE_INFO_EXTRA, movieList.get(position));
        startActivity(i);
    }

}
