
package org.androidaalto.droidkino;

import java.util.ArrayList;
import java.util.List;

import org.androidaalto.droidkino.adapter.MovieListAdapter;
import org.androidaalto.droidkino.service.DataFetchService;
import org.androidaalto.droidkino.xml.BaseFinnkinoParser;
import org.androidaalto.droidkino.xml.ParserFactory;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MovieList extends ListActivity {

    private static final String LOG_TAG = ListActivity.class.getCanonicalName();

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<MovieSchedule> movieSchedules = getMovieSchedules(getIntent().getExtras());

        MovieListAdapter adapter = new MovieListAdapter(this, movieSchedules);

        setListAdapter(adapter);

        adapter.sortByTitle();

    }
    
    private List<MovieSchedule> getMovieSchedules(Bundle movieSearchFilter) {
        
        String areaId = movieSearchFilter.getString(BaseFinnkinoParser.PARAM_AREA);
        String date = movieSearchFilter.getString(BaseFinnkinoParser.PARAM_DATE);
        String eventId = movieSearchFilter.getString(BaseFinnkinoParser.PARAM_EVENT_ID);
        
        List<MovieSchedule> movieSchedules = null;
        
        try {
            movieSchedules = ParserFactory.getParser().parseSchedules(areaId, date, eventId);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error while fetching movie schedule data", e);
            Toast.makeText(MovieList.this, "Fetching Movie Schedules failed", Toast.LENGTH_SHORT).show();
            return new ArrayList<MovieSchedule>();
        } 
        List<MovieSchedule> filteredMovieSchedules = new ArrayList<MovieSchedule>();
        
        String startTime = movieSearchFilter.getString(BaseFinnkinoParser.DTTM_SHOW_START);
        String endTime = movieSearchFilter.getString(BaseFinnkinoParser.DTTM_SHOW_END);
        
        for (MovieSchedule movieSchedule : movieSchedules) {
            String movieStartTime = movieSchedule.getDttmShowStart().substring(11, 16);
            if ( (startTime==null || endTime==null || (startTime.compareTo(movieStartTime) <=0  && endTime.compareTo(movieStartTime) >=0))
               ) {
                filteredMovieSchedules.add(movieSchedule);
            }
                
        }
        return filteredMovieSchedules;
    }

}
