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
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class DroidkinoMain extends Activity {

    RadioButton theather1, theather2, theather3, theather4;

    RadioGroup group;

    String theatherRequest;

    Button button;

    ProgressDialog pd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        button = (Button) findViewById(R.id.search);
        theather1 = (RadioButton) findViewById(R.id.kino);
        theather2 = (RadioButton) findViewById(R.id.maxi);
        theather3 = (RadioButton) findViewById(R.id.omen);
        theather4 = (RadioButton) findViewById(R.id.tenn);
        group = (RadioGroup) findViewById(R.id.menu_theather);

        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MovieList.class);
                startActivity(intent);
            }
        });

    }

    public String getCheckedThether() {
        int id = group.getCheckedRadioButtonId();
        if (theather1.getId() == id) {
            theatherRequest = "Kinopalatsi Helsinki";
        }
        if (theather2.getId() == id) {
            theatherRequest = "Maxim Helsinki";
        }
        if (theather3.getId() == id) {
            theatherRequest = "Tenispalatsi";
        }
        if (theather4.getId() == id) {
            theatherRequest = "Omena Espoo";
        }
        return theatherRequest;
    }

}
