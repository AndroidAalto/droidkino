
package org.androidaalto.droidkino.fragments;

import java.util.List;

import org.androidaalto.droidkino.DroidKinoApplicationCache;
import org.androidaalto.droidkino.DroidKinoIntent;
import org.androidaalto.droidkino.R;
import org.androidaalto.droidkino.adapter.MovieListAdapter;
import org.androidaalto.droidkino.beans.MovieInfo;
import org.androidaalto.droidkino.service.DataFetchService;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

/**
 * A list type of activity for movies that makes use of a custom adapter to show a custom list using MovieInfo beans.
 * It first checks if the MovieInfo list is in the DroidKinoApplicationCache to grab the list from there, otherwise I calls
 * the DataFetchService which downloads the info from the FinnKino server.
 * 
 * @author marcostong17
 * @see MovieListAdapter
 * @see MovieInfo
 * @see DroidKinoApplicationCache
 */
public class MovieList extends ListFragment {
    
    public static final String LOG_TAG = MovieList.class.getCanonicalName();

    private ProgressDialog fetchingXmlProgress;

    public static final String APP_PREFS_FILE = "appx prefs file";

    private List<MovieInfo> movieList;
    
    /**
     * Index of the top most item in the list.
     */
    private int savedTopMostElementIndex;

    /**
     * Position in pixels of the top most element of the list.
     */
    private int savedTopMostElementPosition;
    
    /**
     * Initializes the MovieInfo list, either from the DroidKinoApplicationCache if it was already retrieved otherwise it trigggers
     * the DataFetchService to download it from the FinnKino server
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        
        IntentFilter filter = new IntentFilter(DroidKinoIntent.FETCH_COMPLETE.getAction());
        filter.addAction(DroidKinoIntent.FETCH_FAILED.getAction());
        getActivity().registerReceiver(mBroadcastReceiver, filter);

        DroidKinoApplicationCache cache = DroidKinoApplicationCache.getInstance();
        if (cache.getMovies().size() > 0) {
            movieList = cache.getMovies();
            publishListAdapters();
            return;
        }

        final SharedPreferences prefs = getActivity().getSharedPreferences(APP_PREFS_FILE, getActivity().MODE_PRIVATE);

        if (prefs.getBoolean(DataFetchService.SERVICE_WORKING, false)) {
            return;
        }

        fetchingXmlProgress = ProgressDialog.show(getActivity(), "", getString(R.string.fetching_movies));

        startDataFetchService();
    }
    
   /* @Override
    protected void onResume() {
        super.onResume();
        restoreScrollPosition();
    }*/
    
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
                Toast.makeText(getActivity(), "Fetch failed", Toast.LENGTH_SHORT).show();
            } else {
                movieList = (List<MovieInfo>) intent
                        .getSerializableExtra(DroidKinoIntent.MOVIE_LIST_EXTRA);
                DroidKinoApplicationCache cache = DroidKinoApplicationCache.getInstance();
                cache.getMovies().addAll(movieList);
                publishListAdapters();
            }
        }
    };
    
    /**
     * Sets the list of MovieInfo beans to a MovieListAdapter, 
     * which is then set as the adapter for this activity.
     */
    private void publishListAdapters() {
        MovieListAdapter adapter = new MovieListAdapter(getActivity(), movieList);

        setListAdapter(adapter);

        adapter.sortByTitle();
    }
    private void startDataFetchService() {
        Intent serviceIntent = new Intent(getActivity(), DataFetchService.class);
        serviceIntent.putExtra(DataFetchService.DATA_TO_FETCH, DroidKinoIntent.MOVIE_LIST_EXTRA);
        getActivity().startService(serviceIntent);
    }

    @Override
    public void onStop() {
        getActivity().unregisterReceiver(mBroadcastReceiver);
        super.onStop();
    }
    
    /**
     * If an a item is clicked then a the Movie Detail activity is launched, 
     * passing the corresponding MovieInfo bean in the intent extras.
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        /*saveScrollPosition(l);
        Intent i = new Intent(getActivity(), MovieDetail.class);
        i.putExtra(MovieDetail.MOVIE_INFO_EXTRA, movieList.get(position));
        startActivity(i);*/
        showDetails(position);

        
    }

    /**
     * This method saves the scroll position. 
     * 
     * TODO: Position is lost if device orientation is changed. Maybe use
     * onSaveInstanceState/onRestoreInstanceState methods.
     * 
     * @param l ListView
     */
    private void saveScrollPosition(ListView l) {
        this.savedTopMostElementIndex = l.getFirstVisiblePosition();
        View topItem = l.getChildAt(0);
        this.savedTopMostElementPosition = topItem.getTop();
    }
    
    /**
     * Restore the previous scroll position on the list.
     */
    private void restoreScrollPosition() {
        if (this.savedTopMostElementIndex > 0){
            getListView().setSelectionFromTop(this.savedTopMostElementIndex, this.savedTopMostElementPosition);
        }
    }

    
    /**
     * Helper function to show the details of a selected item, either by
     * displaying a fragment in-place in the current UI, or starting a
     * whole new activity in which it is displayed.
     */
    private void showDetails(int index) {
        int mCurCheckPosition = index;
        
        //TODO: determine the right value of mDualPane
        boolean mDualPane = false;
        
        if (mDualPane) {
         // We can display everything in-place with fragments, so update
            // the list to highlight the selected item and show the data.
            getListView().setItemChecked(index, true);

            // Check what fragment is currently shown, replace if needed.
            MovieDetail details = (MovieDetail)
                    getFragmentManager().findFragmentById(R.id.details);
            if (details == null || details.getShownIndex() != index) {
                // Make new fragment to show this selection.
                details = MovieDetail.newInstance(index);

                // Execute a transaction, replacing any existing fragment
                // with this one inside the frame.
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.details, details);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }  else {
                // Otherwise we need to launch a new activity to display
                // the dialog fragment with selected text.
                Intent intent = new Intent();
                intent.setClass(getActivity(), MovieDetail.class);
                intent.putExtra("index", index);
                startActivity(intent);
            }

        }
    }

}
