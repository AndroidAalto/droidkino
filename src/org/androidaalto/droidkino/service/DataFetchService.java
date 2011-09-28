/*******************************************************************************

   Copyright: 2011 Android Aalto Community

   This file is part of Droidkino.

   Droidkino is free software; you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation; either version 2 of the License, or
   (at your option) any later version.

   Droidkino is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with Droidkino; if not, write to the Free Software
   Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

 ******************************************************************************/

package org.androidaalto.droidkino.service;

import java.io.Serializable;
import java.util.List;

import org.androidaalto.droidkino.DroidKinoIntent;
import org.androidaalto.droidkino.beans.MovieInfo;
import org.androidaalto.droidkino.beans.MovieSchedule;
import org.androidaalto.droidkino.beans.TheatreArea;
import org.androidaalto.droidkino.xml.BaseFinnkinoParser;
import org.androidaalto.droidkino.xml.ParserFactory;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

/***
 * Service that fetch the movie list from the data source. It fires:
 * <ul>
 * <li>{@link DroidKinoIntent.FETCH_COMPLETE} if the fetching its ok. You can
 * read the movies list in the {@link DroidKinoIntent.MOVIE_LIST_EXTRA} key.
 * <li>{@link DroidKinoIntent.FETCH_FAILED} if there was some problems during
 * the fetch process</li>
 * </ul>
 * After getting the data and firing the intent it shutdown automatically
 * 
 * @author rciovati
 */
public class DataFetchService extends Service {

    private static final String LOG_TAG = DataFetchService.class.getCanonicalName();

    public static final String SERVICE_WORKING = "fetch_service_working";
    
    public static final String DATA_TO_FETCH = "data_to_fetch";
    

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {

        Log.d(LOG_TAG, "Started");

        final SharedPreferences prefs = getSharedPreferences(SERVICE_WORKING, MODE_PRIVATE);

        final Bundle extras =  intent.getExtras();
        
        Runnable r = new Runnable() {

            @Override
            public void run() {
                
                List<MovieInfo> showList = null;

                List<TheatreArea> theatreAreas = null;
                
                List<MovieSchedule> scheduleList = null;
                
                Editor edit = prefs.edit();

                try {
                    edit.putBoolean(SERVICE_WORKING, true);
                    edit.commit();

                    String dataToFetch = extras.getString(DATA_TO_FETCH);
                    
                    if (DroidKinoIntent.MOVIE_LIST_EXTRA.equals(dataToFetch)) {
                        String areaId = extras.getString(BaseFinnkinoParser.PARAM_AREA);
                        showList = ParserFactory.getParser().parseMovies(areaId);
                    }
                    if (DroidKinoIntent.THEATRE_AREAS_EXTRA.equals(dataToFetch)) {
                        theatreAreas = ParserFactory.getParser().parseAreas();
                    }
                    if (DroidKinoIntent.SCHEDULE_LIST_EXTRA.equals(dataToFetch)) {
                        String areaId = extras.getString(BaseFinnkinoParser.PARAM_AREA);
                        String date = extras.getString(BaseFinnkinoParser.PARAM_DATE);
                        String eventId = extras.getString(BaseFinnkinoParser.PARAM_EVENT_ID);
                        scheduleList = ParserFactory.getParser().parseSchedules(areaId, date, eventId);
                    }
                    edit.putBoolean(SERVICE_WORKING, false);
                    edit.commit();

                    Intent completeIntent = DroidKinoIntent.FETCH_COMPLETE;

                    if (DroidKinoIntent.MOVIE_LIST_EXTRA.equals(dataToFetch)) {
                        completeIntent.putExtra(DroidKinoIntent.MOVIE_LIST_EXTRA,
                            (Serializable) showList);
                    }
                    if (DroidKinoIntent.THEATRE_AREAS_EXTRA.equals(dataToFetch)) {
                        completeIntent.putExtra(DroidKinoIntent.THEATRE_AREAS_EXTRA,
                            (Serializable) theatreAreas);
                    }
                    if (DroidKinoIntent.SCHEDULE_LIST_EXTRA.equals(dataToFetch)) {
                        completeIntent.putExtra(DroidKinoIntent.SCHEDULE_LIST_EXTRA,
                            (Serializable) scheduleList);
                    }
                    
                    sendBroadcast(completeIntent);

                    Log.d(LOG_TAG, "Sent fetch complete broadcast... stopping the service");
                } catch (Exception e) {
                    Log.e(LOG_TAG, "Error while fetching data", e);
                    sendBroadcast(DroidKinoIntent.FETCH_FAILED);
                } finally {
                    stopSelf();
                }

            }
        };

        new Thread(r).start();

        super.onStart(intent, startId);
    }

}
