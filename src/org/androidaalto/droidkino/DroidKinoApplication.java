
package org.androidaalto.droidkino;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;

/***
 * This class is like a shared memory for all the application.
 * Data saved in this class is available in all the activities of the application
 * You can get this object in any activity just calling the {@link Activity.getApplication()}
 * @author rciovati
 */
public class DroidKinoApplication extends Application {

    private List<MovieInfo> movies = new ArrayList<MovieInfo>();

    /***
     * Save the list of the movies
     * @param movies
     */
    public void setMovies(List<MovieInfo> movies) {
        this.movies = movies;
    }

    /***
     * Returns a previous saved list of the movies
     * @return 
     */
    public List<MovieInfo> getMovies() {
        return movies;
    }

}
