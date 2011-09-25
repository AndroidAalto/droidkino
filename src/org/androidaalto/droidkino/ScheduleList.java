
package org.androidaalto.droidkino;

import java.util.List;

import org.androidaalto.droidkino.adapter.ScheduleListAdapter;
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

public class ScheduleList extends ListActivity {

    public static final String LOG_TAG = ScheduleList.class.getCanonicalName();

    private ProgressDialog fetchingXmlProgress;

    public static final String APP_PREFS_FILE = "appx prefs file";

    private List<MovieSchedule> scheduleList;
    
   
    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter filter = new IntentFilter(DroidKinoIntent.FETCH_COMPLETE.getAction());
        filter.addAction(DroidKinoIntent.FETCH_FAILED.getAction());
        registerReceiver(mBroadcastReceiver, filter);

        DroidKinoApplicationCache cache = DroidKinoApplicationCache.getInstance();
        if (cache.getSchedules().size() > 0) {
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
                
                
                //TODO: To save in cache the schedule list in a map that has as key the area-date used to retrieve the list
                //DroidKinoApplicationCache cache = DroidKinoApplicationCache.getInstance();
                //cache.getSchedules(scheduleList);
                
                ScheduleListAdapter adapter = new ScheduleListAdapter(ScheduleList.this, scheduleList);

                setListAdapter(adapter);

                adapter.sortByStartTime();

               
            }
        }
    };
    
    
    private void startDataFetchService() {
        Intent serviceIntent = new Intent(ScheduleList.this, DataFetchService.class);
        String areaId = getIntent().getStringExtra(BaseFinnkinoParser.PARAM_AREA);
        String date = getIntent().getStringExtra(BaseFinnkinoParser.PARAM_DATE);

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
