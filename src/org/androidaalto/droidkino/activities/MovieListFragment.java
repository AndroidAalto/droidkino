
package org.androidaalto.droidkino.activities;

import org.androidaalto.droidkino.DroidKinoApplicationCache;
import org.androidaalto.droidkino.adapter.MovieListAdapter;
import org.androidaalto.droidkino.beans.MovieInfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.TypedValue;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

/**
 * A list type of fragment for movies that makes use of a custom adapter to show
 * a custom list using MovieInfo beans.
 * 
 * @see MovieListAdapter
 * @see MovieInfo
 * @see DroidKinoApplicationCache
 */
public class MovieListFragment extends ListFragment {

    public static final String LOG_TAG = MovieListFragment.class.getCanonicalName();

    private List<MovieInfo> currentMovieList;

    /**
     * Index of the top most item in the list.
     */
    private int savedTopMostElementIndex;

    /**
     * Position in pixels of the top most element of the list.
     */
    private int savedTopMostElementPosition;

    @Override
    public void onResume() {
        super.onResume();
        restoreScrollPosition();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScrollView scroller = new ScrollView(getActivity());
        TextView text = new TextView(getActivity());
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getActivity()
                .getResources().getDisplayMetrics());
        text.setPadding(padding, padding, padding, padding);
        scroller.addView(text);
        /*
         * Sets the list of MovieInfo beans to a MovieListAdapter, which is then
         * set as the adapter for this activity.
         */
        if (currentMovieList != null) {
            MovieListAdapter adapter = new MovieListAdapter(getActivity(), currentMovieList);
            setListAdapter(adapter);
            adapter.sortByTitle();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListShown(currentMovieList != null);
    }
    /**
     * If an a item is clicked then a the Movie Detail activity is launched,
     * passing the corresponding MovieInfo bean in the intent extras.
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        saveScrollPosition(l);
        Intent i = new Intent(getActivity(), MovieDetail.class);
        i.putExtra(MovieDetail.MOVIE_INFO_EXTRA, currentMovieList.get(position));
        startActivity(i);
    }

    /**
     * This method saves the scroll position. TODO: Position is lost if device
     * orientation is changed. Maybe use
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
        if (this.savedTopMostElementIndex > 0) {
            getListView().setSelectionFromTop(this.savedTopMostElementIndex,
                    this.savedTopMostElementPosition);
        }
    }

    /**
     * @return the hash code for the current movie list
     */
    public int getCurrentMovieListHashCode() {
        return currentMovieList != null ? currentMovieList.hashCode() : 0;
    }

    public void setCurrentMovieList(List<MovieInfo> movieList) {
        this.currentMovieList = movieList;
    }

    /**
     * @param movieList the list of movies to display
     * @return
     */
    public static MovieListFragment newInstance(List<MovieInfo> movieList) {
        MovieListFragment movieListFragment = new MovieListFragment();
        // Supply movie list
        movieListFragment.setCurrentMovieList(movieList);
        return movieListFragment;
    }
}
