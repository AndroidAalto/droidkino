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
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class DroidkinoMain extends Activity {

    List<MovieInfo> moviesList;

    private Button button;

    final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

        @SuppressWarnings("unchecked")
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DroidKinoIntent.FETCH_FAILED.getAction())) {
                Toast.makeText(DroidkinoMain.this, "Fetch failed", Toast.LENGTH_SHORT).show();
            } else {
                moviesList = (List<MovieInfo>) intent
                        .getSerializableExtra(DroidKinoIntent.MOVIE_LIST_EXTRA);
                Toast.makeText(DroidkinoMain.this, "Fetched " + moviesList.size() + "movies",
                        Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(DroidKinoIntent.FETCH_COMPLETE.getAction());
        filter.addAction(DroidKinoIntent.FETCH_FAILED.getAction());
        registerReceiver(mBroadcastReceiver, filter);
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        button = (Button) findViewById(R.id.search);
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(DroidkinoMain.this, DataFetchService.class);
                startService(i);
            }
        });
    }

    @Override
    protected void onStop() {
        unregisterReceiver(mBroadcastReceiver);
        super.onPause();
    }

}
