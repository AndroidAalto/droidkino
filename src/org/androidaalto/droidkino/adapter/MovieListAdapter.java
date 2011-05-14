
import java.util.Comparator;
import java.util.List;

import org.androidaalto.droidkino.MovieInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MovieListAdapter extends ArrayAdapter<MovieInfo> {

    private Context mContext;

    private LayoutInflater mInflater;

    private int mResId;

    public MovieListAdapter(Context context, List<MovieInfo> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    public void sortByTitle() {

        this.sort(new Comparator<MovieInfo>() {
            @Override
            public int compare(MovieInfo object1, MovieInfo object2) {
                return object1.getTitle().compareTo(object2.getTitle());
            }
        });

    }

    public void sortByTime() {

        throw new RuntimeException("Not yet implemented");

        // this.sort(new Comparator<MovieInfo>() {
        //
        // @Override
        // public int compare(MovieInfo object1, MovieInfo object2) {
        // return 0;
        // }
        // });
    }

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
