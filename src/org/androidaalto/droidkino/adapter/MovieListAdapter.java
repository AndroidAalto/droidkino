
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.androidaalto.droidkino.MovieInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/***
 * Basic implementation of a customer adapter to display the movie list.
 * It should also implement some stuff like filtering.
 * @author rciovati
 */
public class MovieListAdapter extends ArrayAdapter<MovieInfo> {

    private LayoutInflater mInflater;

    private int mResId;

    public MovieListAdapter(Context context, List<MovieInfo> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
        mInflater = LayoutInflater.from(context);
        mResId = android.R.layout.simple_list_item_1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MovieInfo movie = getItem(position);

        ViewCache viewCache;

        if (convertView == null) {
            convertView = (View) mInflater.inflate(mResId, null);
            viewCache = new ViewCache(convertView);
            convertView.setTag(viewCache);
        } else {
            convertView = (View) convertView;
            viewCache = (ViewCache) convertView.getTag();
        }

        TextView movieTitle = viewCache.getTitleTextView();
        movieTitle.setText(movie.getTitle());

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
     * Sort the adapter content by movie start time 
     */
    public void sortByStartTime() {

        this.sort(new Comparator<MovieInfo>() {

            @Override
            public int compare(MovieInfo object1, MovieInfo object2) {
                Date date1 = object1.getStartingDate();
                Date date2 = object2.getStartingDate();
                return date1.compareTo(date2);
            }
        });
    }

    /***
     * Private class for hold already inflated views and avoid to inflate them again.
     * @author rciovati
     */
    private class ViewCache {
        private TextView title;

        private View baseView;

        public ViewCache(View baseView) {
            this.baseView = baseView;
        }

        public TextView getTitleTextView() {

            if (title == null) {
                title = (TextView) baseView.findViewById(android.R.id.text1);
            }

            return title;
        }

    }

}
