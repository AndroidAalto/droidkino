
package org.androidaalto.droidkino;

import org.androidaalto.droidkino.adapter.MovieListAdapter;

import android.app.ListActivity;
import android.os.Bundle;

public class MovieList extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DroidKinoApplication app = (DroidKinoApplication) getApplication();

        MovieListAdapter adapter = new MovieListAdapter(this, app.getMovies());

        setListAdapter(adapter);

        adapter.sortByTitle();

    }

}
