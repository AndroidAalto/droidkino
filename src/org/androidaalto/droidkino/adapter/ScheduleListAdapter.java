
package org.androidaalto.droidkino.adapter;

import java.net.URL;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.androidaalto.droidkino.MovieSchedule;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import org.androidaalto.droidkino.R;


/***
 * Basic implementation of a customer adapter to display the movie list. It
 * should also implement some stuff like filtering.
 * 
 * @author rciovati
 */
public class ScheduleListAdapter extends ArrayAdapter<MovieSchedule> {
 
    private static final String LOG_TAG = ScheduleListAdapter.class.getCanonicalName();


    private LayoutInflater mInflater;

    // this is a cache of movie images, used because there are several movie entries for the same title
    // because there no need to download the same image several times, we save them in cache.
    private static Map<String, Drawable> movieDrawables;
    
    private int mResId;

    public ScheduleListAdapter(Context context, List<MovieSchedule> schedule) {
        super(context, R.layout.movie_list_item, schedule);
        mInflater = LayoutInflater.from(context);
        mResId = R.layout.movie_list_item; 
        movieDrawables = new HashMap<String, Drawable>();
    }
  
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MovieSchedule movieSchedule = getItem(position);

        ViewCache viewCache;

        if (convertView == null) {
            convertView = (View) mInflater.inflate(mResId, null);
            viewCache = new ViewCache();
            viewCache.title = (TextView) convertView.findViewById(R.id.titleTextView);
            viewCache.theatre = (TextView) convertView.findViewById(R.id.theatreTextView);
            viewCache.lenghtInMinutes = (TextView) convertView.findViewById(R.id.lengthInMinutesTextView);
            viewCache.dttmShowStart = (TextView) convertView.findViewById(R.id.dttmShowStartTextView);
            viewCache.smallImagePortrait = (ImageView) convertView.findViewById(R.id.smallImagePortrait);
            
            convertView.setTag(viewCache);
        } else {
            convertView = (View) convertView;
            viewCache = (ViewCache) convertView.getTag();
        }

        viewCache.title.setText(movieSchedule.getTitle());
        viewCache.lenghtInMinutes.setText(String.valueOf(movieSchedule.getLengthInMinutes()) + "m");
        viewCache.dttmShowStart.setText(movieSchedule.getDttmShowStart().substring(11, 16));
        viewCache.theatre.setText(movieSchedule.getTheatre() + "-" + movieSchedule.getTheatreAuditorium());
        try {

            // Since movie entries are repeated (same movie title for several times and theatres, we save the drawable in cache)
            Drawable movieDrawable = null;
            String imageUrl = movieSchedule.getEventSmallImagePortrait();
            if (movieDrawables.containsKey(imageUrl)) {
                movieDrawable = movieDrawables.get(imageUrl);
            } else {
                URL thumb_u = new URL(imageUrl);
                movieDrawable = Drawable.createFromStream(thumb_u.openStream(), "src");
                movieDrawables.put(imageUrl, movieDrawable);
            }
            
            viewCache.smallImagePortrait.setImageDrawable(movieDrawable);
        }
        catch (Exception e) {
            Log.d(LOG_TAG, e.getMessage());
            viewCache.smallImagePortrait.setImageResource(R.drawable.icon);
        }

        return convertView;
    }

    /***
     * Sort the adapter content by movie start time
     */ 
    public void sortByStartTime() {

        this.sort(new Comparator<MovieSchedule>() {

            @Override
            public int compare(MovieSchedule object1, MovieSchedule object2) {
                Date date1 = object1.getStartingDate();
                Date date2 = object2.getStartingDate();
                return date1.compareTo(date2);
            }
        });
    }

    /***
     * Private class for hold already inflated views and avoid to inflate them
     * again.
     * 
     * @author rciovati
     */
    static class ViewCache {
        TextView title;

        TextView theatre;
        
        TextView lenghtInMinutes;
        
        TextView dttmShowStart;

        ImageView smallImagePortrait;
        
    }

}
