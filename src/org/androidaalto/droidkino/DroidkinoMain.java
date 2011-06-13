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

import java.util.List;

import org.androidaalto.droidkino.service.DataFetchService;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.Button;
import android.widget.Toast;

public class DroidkinoMain extends Activity {

    public static final String LOG_TAG = DroidkinoMain.class.getCanonicalName();

    private final static int ENABLE_BUTTON_MESSAGE = 0;

    public static final String APP_PREFS_FILE = "appx prefs file";

    private List<MovieInfo> moviesList;

    private Button button;

    private final Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            Log.d(LOG_TAG, "DroidkinoMain::handle");
            switch (msg.what) {
                case ENABLE_BUTTON_MESSAGE:
                    button.setEnabled(true);
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        button = (Button) findViewById(R.id.search);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DroidkinoMain.this, MovieList.class);
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
