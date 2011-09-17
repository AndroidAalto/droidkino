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
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class DroidkinoMain extends Activity {

    public static final String LOG_TAG = DroidkinoMain.class.getCanonicalName();

    private final static int ENABLE_BUTTON_MESSAGE = 0;

    public static final String APP_PREFS_FILE = "appx prefs file";

    private List<MovieInfo> moviesList;

    private Button searchButon;
    
    private TextView startTimeTextView;
    
    private TextView endTimeTextView;
    
    private Calendar startTime;
    
    private Calendar endTime;
    
    private ArrayAdapter<String> titlesArrayAdapter;
    
    private ArrayAdapter<String> theatreArrayAdapter;

    private static final int START_TIME_DIALOG_ID = 0;
    
    private static final int END_TIME_DIALOG_ID = 1;

    private final Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            Log.d(LOG_TAG, "DroidkinoMain::handle");
            switch (msg.what) {
                case ENABLE_BUTTON_MESSAGE:
                    searchButon.setEnabled(true);
                    break;

                default:
                    break;
            }
            super.handleMessage(msg);
        }

    };

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
                Toast.makeText(DroidkinoMain.this, "Fetch failed", Toast.LENGTH_SHORT).show();
            } else {
                moviesList = (List<MovieInfo>) intent
                        .getSerializableExtra(DroidKinoIntent.MOVIE_LIST_EXTRA);
                DroidKinoApplication app = (DroidKinoApplication) getApplication();
                app.setMovies(moviesList);
                // We use an Handler to make sure that the action is done in the
                // UI thread
                for (String movieTitle : app.getMovieTitles()) {
                    titlesArrayAdapter.add(movieTitle);                    
                }
                
                for (String theatre : app.getTheatres()) {
                    theatreArrayAdapter.add(theatre);                    
                }

                
                Message m = mHandler.obtainMessage(ENABLE_BUTTON_MESSAGE);
                mHandler.sendMessage(m);
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

    // updates the time we display in the TextView
    private void updateTimeTextView(TextView timeTextView, Calendar dateTime) {
        
        // get the current time
        int mHour = dateTime.get(Calendar.HOUR_OF_DAY);
        int mMinute = dateTime.get(Calendar.MINUTE);

        timeTextView.setText(
            new StringBuilder()
                    .append(pad(mHour)).append(":")
                    .append(pad(mMinute)));
    }

    // adds a zero to the hour or minutes if it is smaller than 10
    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }
    
    //  the callback received when the user "sets" the time in the dialog
    private TimePickerDialog.OnTimeSetListener startTimeSetListener =
        new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                startTime.set(Calendar.HOUR, hourOfDay);
                startTime.set(Calendar.MINUTE, minute);
                updateTimeTextView(startTimeTextView, startTime);
            }
        };
    
    //  the callback received when the user "sets" the time in the dialog
    private TimePickerDialog.OnTimeSetListener endTimeSetListener =
        new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                endTime.set(Calendar.HOUR, hourOfDay);
                endTime.set(Calendar.MINUTE, minute);
                updateTimeTextView(endTimeTextView, endTime);
            }
        };
            
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case START_TIME_DIALOG_ID:
            return new TimePickerDialog(this,
                    startTimeSetListener, startTime.get(Calendar.HOUR), startTime.get(Calendar.MINUTE), false);
        case END_TIME_DIALOG_ID:
            return new TimePickerDialog(this,
                    endTimeSetListener, endTime.get(Calendar.HOUR), endTime.get(Calendar.MINUTE), false);
        }
        return null;
    }
        
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        
        
        
        // Fill up the spinners of movies and theatres based on the retreieved list of movie info.
        titlesArrayAdapter = new ArrayAdapter<String>(this, 
                android.R.layout.simple_spinner_item);
        titlesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner titleSpinner = (Spinner) findViewById(R.id.spinner_movie);
        titleSpinner.setAdapter(titlesArrayAdapter);
        
        theatreArrayAdapter = new ArrayAdapter<String>(this, 
                android.R.layout.simple_spinner_item);
        theatreArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner theatreSpinner = (Spinner) findViewById(R.id.spinner_place);
        theatreSpinner.setAdapter(theatreArrayAdapter);
        
        
        // add a click listener to the button
        startTime = Calendar.getInstance();
        endTime = Calendar.getInstance();
        endTime.add(Calendar.HOUR, 2);
        startTimeTextView = (TextView) findViewById(R.id.text_start_time);
        endTimeTextView = (TextView) findViewById(R.id.text_end_time);
        updateTimeTextView(startTimeTextView, startTime);
        updateTimeTextView(endTimeTextView, endTime);
        
        startTimeTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(START_TIME_DIALOG_ID);
            }
        });

        endTimeTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(END_TIME_DIALOG_ID);
            }
        });
        
        
        //search by checkboxes
        final CheckBox searchByTitleCheckBox = (CheckBox) findViewById(R.id.searchByTitle);
        final CheckBox searchByTimeCheckBox = (CheckBox) findViewById(R.id.searchByTime);
        final CheckBox searchByTheatreCheckBox = (CheckBox) findViewById(R.id.searchByTheatre);
        
        // adding functionality to the search button
        searchButon = (Button) findViewById(R.id.search);
        searchButon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DroidkinoMain.this, MovieList.class);
                Bundle extras = new Bundle();
                if (searchByTitleCheckBox.isChecked()) {
                    String title = titlesArrayAdapter.getItem(titleSpinner.getSelectedItemPosition());
                    extras.putString(BaseFinnkinoParser.TITLE, title);
                }
                if (searchByTheatreCheckBox.isChecked()) {
                    String theatre = theatreArrayAdapter.getItem(theatreSpinner.getSelectedItemPosition());
                    extras.putString(BaseFinnkinoParser.THEATRE, theatre);
                }
                if (searchByTimeCheckBox.isChecked()) {
                    String startTime = (String) startTimeTextView.getText();
                    String endTime = (String) endTimeTextView.getText();
                    extras.putString(BaseFinnkinoParser.DTTM_SHOW_START, startTime);
                    extras.putString(BaseFinnkinoParser.DTTM_SHOW_END, endTime);
                }
                i.putExtras(extras);
                startActivity(i);
            }
        });
 
        
        Log.d(LOG_TAG, "Starting the service");
    }

    private void startDataFetchService() {
        Intent serviceIntent = new Intent(DroidkinoMain.this, DataFetchService.class);
        startService(serviceIntent);
    }

    @Override
    protected void onStop() {
        unregisterReceiver(mBroadcastReceiver);
        super.onStop();
    }
}
