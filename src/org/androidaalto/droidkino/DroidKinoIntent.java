
package org.androidaalto.droidkino;

import android.content.Intent;

/***
 * Containts all the custom intent defined for DroidKino application
 * @author rciovati
 */
public class DroidKinoIntent {

    /***
     * Extra key name for the movies that the {@link DataFetchService} returns
     */
    public static String MOVIE_LIST_EXTRA = "movies";

    /***
     * Intent fired by {@link DataFetchService} when there is an error during movies fetching
     */
    public static final Intent FETCH_FAILED = new Intent("fetch_failed");

    /***
     * Intent fired by {@link DataFetchService} when fetch of movies is complete. 
     * You can read the movies list reading the extra with {@link DroidKinoIntent.MOVIE_LIST_EXTRA} key.
     */
    public static final Intent FETCH_COMPLETE = new Intent("fetch_complete");

}
