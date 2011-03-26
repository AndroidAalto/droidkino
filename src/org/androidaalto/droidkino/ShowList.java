package org.androidaalto.droidkino;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.provider.Contacts;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

public class ShowList extends Activity {
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //Creating a static list of shows
        List<Show> showList = new ArrayList<Show>();
        showList.add(new Show());
        showList.add(new Show());
        showList.add(new Show());
        showList.add(new Show());
        showList.add(new Show());
        showList.add(new Show());
        showList.add(new Show());
        showList.add(new Show());
        showList.add(new Show());
        
         	 ListView lv= (ListView)findViewById(R.id.listview);
        	
        	// create the grid item mapping
        	String[] from = new String[] {"title", "time", "teathre"};
        	int[] to = new int[] { R.id.item1, R.id.item2, R.id.item3};
        		 
        		        // prepare the list of all records
        			    Show show = null;
        		        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
        		        for(int i = 0; i < showList.size(); i++){
        		        	show = showList.get(i);
        		            HashMap<String, String> map = new HashMap<String, String>();
        		            map.put("title", show.getTitle());
        		            map.put("time", show.getDttmShowStart().toString());
        		            map.put("teathre", show.getTheatre());
        		            fillMaps.add(map);
        		        }
        		 
        		        // fill in the grid_item layout
        		        SimpleAdapter adapter = new SimpleAdapter(this, fillMaps, R.layout.grid_item, from, to);
        		        lv.setAdapter(adapter);
        		    }
}