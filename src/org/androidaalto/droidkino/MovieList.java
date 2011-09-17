
package org.androidaalto.droidkino;

import java.util.ArrayList;
import java.util.List;

import org.androidaalto.droidkino.adapter.MovieListAdapter;
import org.androidaalto.droidkino.xml.BaseFinnkinoParser;

import android.app.ListActivity;
import android.os.Bundle;

public class MovieList extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DroidKinoApplication app = (DroidKinoApplication) getApplication();

        List<MovieInfo> filteredMovieInfo = filterMovieList(app.getMovies(),  getIntent().getExtras());

        MovieListAdapter adapter = new MovieListAdapter(this, filteredMovieInfo);

        setListAdapter(adapter);

        adapter.sortByTitle();

    }
    
    private List<MovieInfo> filterMovieList(List<MovieInfo> completeMovieList, Bundle movieSearchFilter) {
        List<MovieInfo> filteredMovieList = new ArrayList<MovieInfo>();
        String title = movieSearchFilter.getString(BaseFinnkinoParser.TITLE);
        String theatre = movieSearchFilter.getString(BaseFinnkinoParser.THEATRE);
        String startTime = movieSearchFilter.getString(BaseFinnkinoParser.DTTM_SHOW_START);
        String endTime = movieSearchFilter.getString(BaseFinnkinoParser.DTTM_SHOW_END);
        
        for (MovieInfo movieInfo:completeMovieList) {
            String movieStartTime = movieInfo.getDttmShowStart().substring(11, 16);
            if ((title==null || title.equals(movieInfo.getTitle())) //passed title filter
                && (theatre==null || theatre.equals(movieInfo.getTheatre())) //passed theatre filter
                && (startTime==null || endTime==null || (startTime.compareTo(movieStartTime) <=0  && endTime.compareTo(movieStartTime) >=0))
               ) {
                filteredMovieList.add(movieInfo);
            }
                
        }
        return filteredMovieList;
    }

}
