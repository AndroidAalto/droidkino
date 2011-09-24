
package org.androidaalto.droidkino;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import android.app.Application;

/***
 * This class is like a shared memory for all the application. Data saved in
 * this class is available in all the activities of the application You can get
 * this object in any activity just calling the {@link
 * Activity.getApplication()}
 * 
 * @author rciovati 
 */
public class DroidKinoApplication extends Application {

    private List<MovieInfo> movies = new ArrayList<MovieInfo>();

    private List<TheatreArea> theatres = new ArrayList<TheatreArea>();
    
    /***
     * Save the list of the movies
     * 
     * @param movies
     */
    public void setMovies(List<MovieInfo> movies) {
        this.movies = movies;
    }

    /***
     * Returns a previous saved list of the movies
     * 
     * @return
     */
    public List<MovieInfo> getMovies() {
        return movies;
    }
    
    /***
     * Returns a list of unique movie titles that are in the movie list of the app
     * 
     * @return
     */
    public List<String> getMovieTitles() {
        SortedSet<String> movieTitles = new TreeSet<String>();
        for (MovieInfo movieInfo : movies) {
            movieTitles.add(movieInfo.getTitle());
        }
        return new ArrayList<String>(movieTitles);
    }
    
    
    
    /***
     * Sets the map of theatres <id, name>
     * 
     * @return
     */
    public void setTheatres(List<TheatreArea> theatres) {
       this.theatres = theatres;
    }
    
    /***
     * Returns a list of unique theatres that are in the movie list of the app (this might be hardcoded since the list is quite small and not changing much)
     * 
     * @return
     */
    public List<TheatreArea> getTheatres() {
       return this.theatres;
    }

}
