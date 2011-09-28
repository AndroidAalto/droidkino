
package org.androidaalto.droidkino.activities;

import java.util.List;

import org.androidaalto.droidkino.DroidKinoApplicationCache;
import org.androidaalto.droidkino.DroidKinoIntent;
import org.androidaalto.droidkino.R;
import org.androidaalto.droidkino.adapter.ScheduleListAdapter;
import org.androidaalto.droidkino.beans.MovieSchedule;
import org.androidaalto.droidkino.service.DataFetchService;
import org.androidaalto.droidkino.xml.BaseFinnkinoParser;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

/**
 * A list type of activity for schedules that makes use of a custom adapter to show a custom list using MovieSchedule beans.
 * The list depends is specific to a particular Theathre Area and Date.
 * It first checks if the requested MovieSchedule list is in the DroidKinoApplicationCache to grab the list from there, otherwise I calls
 * the DataFetchService which downloads the info from the FinnKino server.
 * 
 * @author marcostong17
 * @see MovieScheduleAdapter
 * @see MovieSchedule
 * @see DroidKinoApplicationCache
 */
public class ScheduleList extends ListActivity {

    public static final String LOG_TAG = ScheduleList.class.getCanonicalName();

    private ProgressDialog fetchingXmlProgress;

    public static final String APP_PREFS_FILE = "appx prefs file";

    private List<MovieSchedule> scheduleList;
    
    private String areaId;
    
    private String date;
   
    /**
     * Initializes the MovieSchedule list, either from the DroidKinoApplicationCache if it was already retrieved otherwise it trigggers
     * the DataFetchService to download it from the FinnKino server
     */
    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter filter = new IntentFilter(DroidKinoIntent.FETCH_COMPLETE.getAction());
        filter.addAction(DroidKinoIntent.FETCH_FAILED.getAction());
        registerReceiver(mBroadcastReceiver, filter);

        DroidKinoApplicationCache cache = DroidKinoApplicationCache.getInstance();
        areaId = getIntent().getStringExtra(BaseFinnkinoParser.PARAM_AREA);
        date = getIntent().getStringExtra(BaseFinnkinoParser.PARAM_DATE);
        
        
        if (cache.getSchedules().containsKey(areaId + "-" + date)) {
            
            scheduleList = cache.getSchedules().get(areaId + "-" + date);
            publishListAdapters();
            return;
        }

        final SharedPreferences prefs = getSharedPreferences(APP_PREFS_FILE, MODE_PRIVATE);

        if (prefs.getBoolean(DataFetchService.SERVICE_WORKING, false)) {
            return;
        }

        fetchingXmlProgress = ProgressDialog.show(this, "", getString(R.string.fetching_movies));

        startDataFetchService();
    }
    
    
    /***
     * BroadReceiver for getting the result of the data fetching
     * specifically the list of MovieSchedule beans (DroidKinoIntent.SCHEDULE_LIST_EXTRA)
     * and it puts it into the DroidKinoApplicationCache using the key: areaId-date
     */
    final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @SuppressWarnings("unchecked")
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(LOG_TAG, "onReceive");

            fetchingXmlProgress.dismiss();

            if (intent.getAction().equals(DroidKinoIntent.FETCH_FAILED.getAction())) {
                Toast.makeText(ScheduleList.this, "Fetch failed", Toast.LENGTH_SHORT).show();
            } else {
                scheduleList = (List<MovieSchedule>) intent
                        .getSerializableExtra(DroidKinoIntent.SCHEDULE_LIST_EXTRA);
                
                DroidKinoApplicationCache cache = DroidKinoApplicationCache.getInstance();
                cache.getSchedules().put(areaId+"-"+date, scheduleList);
                
                publishListAdapters();
               
            }
        }
    };
    
    /**
     * Sets the list of MovieInfo beans to a MovieListAdapter, 
     * which is then set as the adapter for this activity.
     */
    private void publishListAdapters() {
        ScheduleListAdapter adapter = new ScheduleListAdapter(ScheduleList.this, scheduleList);
        setListAdapter(adapter);
        adapter.sortByStartTime();
    }
    
    /**
     * It triggers the DataFetchService to get the list of MovieSchedules,
     * it passes over the search parameters received in the intent extras, namely:
     * BaseFinnkinoParser.PARAM_AREA and BaseFinnkinoParser.PARAM_DATE, corresponding
     * to the Theatre Area ID and the date (dd.mm.yyyy) to grab the schedule list for.
     */
    private void startDataFetchService() {
        Intent serviceIntent = new Intent(ScheduleList.this, DataFetchService.class);
        
        serviceIntent.putExtra(DataFetchService.DATA_TO_FETCH, DroidKinoIntent.SCHEDULE_LIST_EXTRA);
        serviceIntent.putExtra(BaseFinnkinoParser.PARAM_AREA, areaId);
        serviceIntent.putExtra(BaseFinnkinoParser.PARAM_DATE, date);

        startService(serviceIntent);
    }

    @Override
    protected void onStop() {
        unregisterReceiver(mBroadcastReceiver);
        super.onStop();
    }
    
   

}
