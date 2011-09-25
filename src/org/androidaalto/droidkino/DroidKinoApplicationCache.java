
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
    
    // this is a cache of movie schedule, so that searches of the same area-date are kept in cache
    private Map<String, List<MovieSchedule>> schedules;
    
    // this is a cache of movie images, used because there are several movie entries for the same title
    // because there no need to download the same image several times, we save them in cache.
    private Map<String, Drawable> movieDrawables;

    private DroidKinoApplicationCache() {
        movies = new ArrayList<MovieInfo>();
        theatres = new ArrayList<TheatreArea>();
        schedules = new HashMap<String, List<MovieSchedule>>();
        movieDrawables = new HashMap<String, Drawable>();
    }
    
    public static synchronized DroidKinoApplicationCache getInstance() {
        if (instance==null) {
                instance = new DroidKinoApplicationCache();
        }
        return instance;
    }
    
    /***
     * Returns the ached list of movie info (for all theatre areas)
     * 
     * @return
     */
    public List<MovieInfo> getMovies() {
        return movies;
    }
    
  
    /***
     * Returns cached list of theatre areas
     * 
     * @return
     */
    public List<TheatreArea> getTheatres() {
       return this.theatres;
    }

    /***
     * Returns the cached map of list of movie schedules
     * 
     * @return A map of movie schedule lists, where the key is the concatenation of area Id, dash (-) and date (dd.mm.yyyy), for example 1012-25.09.2011 
     */
    public Map<String, List<MovieSchedule>> getSchedules() {
        return this.schedules;
    }
    
    /***
     * Returns the cached map of movie drawables
     * 
     * @return A map of movie drawables, where the key is the url of the drawable
     */
    public Map<String, Drawable> getMovieDrawables() {
        return this.movieDrawables;
    }
}
