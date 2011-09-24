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

package org.androidaalto.droidkino;

import java.util.Calendar;
import java.util.List;

import org.androidaalto.droidkino.service.DataFetchService;
import org.androidaalto.droidkino.xml.BaseFinnkinoParser;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

public class SearchSchedule extends Activity {

    public static final String LOG_TAG = SearchSchedule.class.getCanonicalName();

    public static final String APP_PREFS_FILE = "appx prefs file";

    private List<TheatreArea> theatreAreaList;
    
    private ArrayAdapter<String> theatreArrayAdapter;

    private Button searchButton;
    
    private Button dateButton;
    
    private static final int DATE_DIALOG_ID = 0;
    
    private Calendar date;
    
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
                Toast.makeText(SearchSchedule.this, "Fetch failed", Toast.LENGTH_SHORT).show();
            } else {
                
                DroidKinoApplication app = (DroidKinoApplication) getApplication();
                
                // We use an Handler to make sure that the action is done in the
                // UI thread
                
                
                theatreAreaList = (List<TheatreArea>) intent
                    .getSerializableExtra(DroidKinoIntent.THEATRE_AREAS_EXTRA);
                    app.setTheatres(theatreAreaList);
                    for (TheatreArea theatreArea: app.getTheatres()) {
                        theatreArrayAdapter.add(theatreArea.getName());                    
                    }
                
               
            }
        }
    };

    private ProgressDialog fetchingXmlProgress;

    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter filter = new IntentFilter(DroidKinoIntent.FETCH_COMPLETE.getAction());
        filter.addAction(DroidKinoIntent.FETCH_FAILED.getAction());
        registerReceiver(mBroadcastReceiver, filter);

        DroidKinoApplication app = (DroidKinoApplication) getApplication();
        if (app != null && app.getMovies().size() > 0) {
            return;
        }

        final SharedPreferences prefs = getSharedPreferences(APP_PREFS_FILE, MODE_PRIVATE);

        if (prefs.getBoolean(DataFetchService.SERVICE_WORKING, false)) {
            return;
        }

        fetchingXmlProgress = ProgressDialog.show(this, "", getString(R.string.fetching_movies));

        startDataFetchService();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case DATE_DIALOG_ID:
            return new DatePickerDialog(this,
                    dateSetListener, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
       }
        return null;
    }
    
    
    
    //  the callback received when the user "sets" the time in the dialog
    private DatePickerDialog.OnDateSetListener dateSetListener =
        new DatePickerDialog.OnDateSetListener() {
            
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(Calendar.YEAR, year);
                date.set(Calendar.MONTH, monthOfYear);
                date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateButton(date);
            }
        };
    
    private void updateDateButton(Calendar date)
    {
        StringBuilder dateText = new StringBuilder();
        dateText.append(pad(date.get(Calendar.DAY_OF_MONTH)));
        dateText.append(".");
        dateText.append(pad(date.get(Calendar.MONTH)));
        dateText.append(".");
        dateText.append(date.get(Calendar.YEAR));
        dateButton.setText(dateText.toString());
    }
        
    // adds a zero to the hour or minutes if it is smaller than 10
    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_schedule);

        // initializing the date button
        date = Calendar.getInstance();
        dateButton = (Button) findViewById(R.id.button_date);
        dateButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(DATE_DIALOG_ID);
                }
            });
        updateDateButton(date);
        
        // initializing the theatre area spinner
        theatreArrayAdapter = new ArrayAdapter<String>(this,  android.R.layout.simple_spinner_item);
        theatreArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner theatreSpinner = (Spinner) findViewById(R.id.spinner_place);
        theatreSpinner.setAdapter(theatreArrayAdapter);
        
        
        
        // adding functionality to the search button
        searchButton = (Button) findViewById(R.id.search);
        
        searchButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SearchSchedule.this, ScheduleList.class);
                Bundle extras = new Bundle();
                
                // pass the date parameter
                String dateString = dateButton.getText().toString();
                extras.putString(BaseFinnkinoParser.PARAM_DATE, dateString);
                
                // pass the theatre area id parameter
                TheatreArea theatreArea = theatreAreaList.get(theatreSpinner.getSelectedItemPosition());
                extras.putString(BaseFinnkinoParser.PARAM_AREA, theatreArea.getId());

                i.putExtras(extras);
                startActivity(i);
            }
        });
 
        
        Log.d(LOG_TAG, "Starting the service");
    }

    private void startDataFetchService() {
        Intent serviceIntent = new Intent(SearchSchedule.this, DataFetchService.class);
        serviceIntent.putExtra(DataFetchService.DATA_TO_FETCH, DroidKinoIntent.THEATRE_AREAS_EXTRA);
        startService(serviceIntent);
    }

    
    @Override
    protected void onStop() {
        unregisterReceiver(mBroadcastReceiver);
        super.onStop();
    }
}
