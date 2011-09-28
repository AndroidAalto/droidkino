
package org.androidaalto.droidkino.adapter;

import java.util.Comparator;
import java.util.List;

import org.androidaalto.droidkino.ImageHelper;
import org.androidaalto.droidkino.R;
import org.androidaalto.droidkino.beans.MovieInfo;

import android.content.Context;
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
 * @author rciovati, marcostong17
 */
public class MovieListAdapter extends ArrayAdapter<MovieInfo> {
   
    private LayoutInflater mInflater;

    private int mResId;

    public MovieListAdapter(Context context, List<MovieInfo> movies) {
        super(context, R.layout.schedule_list_item, movies);
        mInflater = LayoutInflater.from(context);
        mResId = R.layout.schedule_list_item; 
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
        
        
        ImageHelper.fillUpImageView( viewCache.smallImagePortrait, movieInfo.getEventSmallImagePortrait(), R.drawable.icon);


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
