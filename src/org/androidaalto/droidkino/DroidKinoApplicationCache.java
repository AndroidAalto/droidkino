
package org.androidaalto.droidkino;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.graphics.drawable.Drawable;

/***
 * This class is like a shared memory for all the application. Data saved in
 * this class is available in all the activities of the application You can get
 * this object in any activity just calling the {@link
 * Activity.getApplication()}
 * 
 * @author rciovati, marcostong17
 */
public class DroidKinoApplicationCache {

    private static volatile DroidKinoApplicationCache instance;
    
    private List<MovieInfo> movies;

    private List<TheatreArea> theatres;
    
    private List<MovieSchedule> schedules;
    
    // this is a cache of movie images, used because there are several movie entries for the same title
    // because there no need to download the same image several times, we save them in cache.
    private Map<String, Drawable> movieDrawables;

    private DroidKinoApplicationCache() {
        movies = new ArrayList<MovieInfo>();
        theatres = new ArrayList<TheatreArea>();
        schedules = new ArrayList<MovieSchedule>();
        movieDrawables = new HashMap<String, Drawable>();
    }
    
    public static synchronized DroidKinoApplicationCache getInstance() {
        if (instance==null) {
                instance = new DroidKinoApplicationCache();
        }
        return instance;
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
     * Returns a list of unique theatres that are in the movie list of the app (this might be hardcoded since the list is quite small and not changing much)
     * 
     * @return
     */
    public List<TheatreArea> getTheatres() {
       return this.theatres;
    }

    public List<MovieSchedule> getSchedules() {
        return this.schedules;
    }
    
    public Map<String, Drawable> getMovieDrawables() {
        return this.movieDrawables;
    }
}
