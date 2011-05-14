package org.androidaalto.droidkino;

import android.content.Intent;

public class DroidKinoIntent {

    public static String MOVIE_LIST_EXTRA = "movies";
    
    public static final Intent FETCH_FAILED = new Intent("fetch_failed");
    public static final Intent FETCH_COMPLETE = new Intent("fetch_complete");
    
}
