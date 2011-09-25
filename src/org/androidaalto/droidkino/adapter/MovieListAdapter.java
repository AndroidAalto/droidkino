
package org.androidaalto.droidkino.adapter;

import java.net.URL;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.androidaalto.droidkino.MovieInfo;
import org.androidaalto.droidkino.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/***
 * Basic implementation of a customer adapter to display the movie list. It
 * should also implement some stuff like filtering.
 * 
 * @author rciovati
 */
public class MovieListAdapter extends ArrayAdapter<MovieInfo> {
 
    private static final String LOG_TAG = MovieListAdapter.class.getCanonicalName();
    
    private LayoutInflater mInflater;

    // this is a cache of movie images, used because there are several movie entries for the same title
    // because there no need to download the same image several times, we save them in cache.
    private static Map<String, Drawable> movieDrawables;
    
    private int mResId;

    public MovieListAdapter(Context context, List<MovieInfo> movies) {
        super(context, R.layout.schedule_list_item, movies);
        mInflater = LayoutInflater.from(context);
        mResId = R.layout.schedule_list_item; 
        movieDrawables = new HashMap<String, Drawable>();
    }
  
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MovieInfo movieInfo = getItem(position);

        ViewCache viewCache;

        if (convertView == null) {
            convertView = (View) mInflater.inflate(mResId, null);
            viewCache = new ViewCache();
            viewCache.title = (TextView) convertView.findViewById(R.id.titleTextView);
            viewCache.genres = (TextView) convertView.findViewById(R.id.genresTextView);
            viewCache.productionYear = (TextView) convertView.findViewById(R.id.productionYearTextView);
            viewCache.ratingLabel = (TextView) convertView.findViewById(R.id.ratingLabelTextView);
            viewCache.smallImagePortrait = (ImageView) convertView.findViewById(R.id.smallImagePortrait);
            
            convertView.setTag(viewCache);
        } else {
            convertView = (View) convertView;
            viewCache = (ViewCache) convertView.getTag();
        }

        viewCache.title.setText(movieInfo.getTitle());
        viewCache.genres.setText(movieInfo.getGenres());
        viewCache.productionYear.setText(movieInfo.getProductionYear());
        viewCache.ratingLabel.setText(movieInfo.getRatingLabel());
        try {

            // Since movie entries are repeated (same movie title for several times and theatres, we save the drawable in cache)
            Drawable movieDrawable = null;
            String imageUrl = movieInfo.getEventSmallImagePortrait();
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
     * Sort the adapter content by movie name
     */
    public void sortByTitle() {

        this.sort(new Comparator<MovieInfo>() {
            @Override
            public int compare(MovieInfo object1, MovieInfo object2) {
                String title1 = object1.getTitle();
                String title2 = object2.getTitle();
                return title1.compareTo(title2);
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

        TextView genres;
        
        TextView productionYear;
        
        TextView ratingLabel;

        ImageView smallImagePortrait;
        
    }

}
