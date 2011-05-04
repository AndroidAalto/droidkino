package org.androidaalto.droidkino;

import org.androidaalto.droidkino.DroidkinoMain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;


public class MovieList extends ListActivity{
	
	String[] movies = {};
    ArrayAdapter<String> movieAdapter;
    private String itemSelected;
    private final Context movieContext = this;
	DroidkinoMain kino;
	String requestedlocation;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            getResquestedList();
	}
	
	void getResquestedList(){
		
		 List<MovieInfo> showList = null;
	        try {
	        showList = Parser.retrieveShows();

	        } catch (Exception e) {
	        // TODO Show the error in a toast alert
	        e.printStackTrace();
	        }
	        
	   // Get Movies
	        MovieInfo show = null;
	        List<String> movieList = new ArrayList<String>(); 
	        
	        for(int i = 0; i < showList.size(); i++){
	        	show = showList.get(i);
	        	movieList.add(show.getTitle());
	    }
	        
	    movies = movieList.toArray(new String[movies.length]);
	    ArrayList<String> myList = new ArrayList<String>(Arrays.asList(movies));
	    
	    movieAdapter=new ArrayAdapter<String>(
	    	this,
	    	android.R.layout.simple_list_item_1,
	    	myList);
	    
	    setListAdapter(movieAdapter);
		    
	}
		
}
	

