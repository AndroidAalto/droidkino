
package org.androidaalto.droidkino;

import org.androidaalto.droidkino.DroidkinoMain;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

public class MovieList extends ListActivity {

    private static final String LOG_TAG = ListActivity.class.getCanonicalName();

    String[] movies = {};

    ArrayAdapter<String> movieAdapter;

    DroidkinoMain kino;

    String requestedlocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        movieAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                new ArrayList<String>());

        setListAdapter(movieAdapter);
        
        new MovieFetcher().execute();

    }

    class MovieFetcher extends AsyncTask<Void, String, Void> {

        private ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(MovieList.this, "", getString(R.string.searching_movies));
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            List<MovieInfo> showList = null;

            try {
                showList = Parser.retrieveShows();
            } catch (Exception e) {
                Log.e(LOG_TAG, e.getMessage());
            }

            for (MovieInfo movieInfo : showList) {
                publishProgress(movieInfo.getTitle());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            pd.dismiss();
            super.onPostExecute(result);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            movieAdapter.add(values[0]);
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled() {
            pd.dismiss();
            super.onCancelled();
        }

    }
}
