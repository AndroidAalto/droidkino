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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DroidkinoMain extends Activity {

    public static final String LOG_TAG = DroidkinoMain.class.getCanonicalName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button movieListButton = (Button) findViewById(R.id.movieListButton);
        Button searchScheduleButton = (Button) findViewById(R.id.searchScheduleButton);
        
        movieListButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent movieListIntent = new Intent(DroidkinoMain.this,
                        MovieList.class);
                startActivity(movieListIntent);
            }
        });
        
        searchScheduleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent searchScheduleIntent = new Intent(DroidkinoMain.this,
                        SearchSchedule.class);
                startActivity(searchScheduleIntent);
            }
        });
    }
}
