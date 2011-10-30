package org.androidaalto.droidkino.fragments;

import org.androidaalto.droidkino.ImageHelper;
import org.androidaalto.droidkino.R;
import org.androidaalto.droidkino.beans.ImdbInfo;
import org.androidaalto.droidkino.beans.MovieInfo;
import org.androidaalto.droidkino.imdb.ImdbApiClient;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Activity for displaying detailed information for one movie.
 * It receives a MovieInfo bean as extra in the intent.
 * Basically shows the information in the screen
 * TODO: we have available the link to videos, this functionality can be added to transition to the VideoPlayer activity and play the video
 * 
 * @author marcostong17
 * @see MovieInfo
 */
public class MovieDetail extends Fragment {

    public static final String MOVIE_INFO_EXTRA = "movie_info";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)  {
        super.onActivityCreated(savedInstanceState);
        
        ViewGroup vg = (ViewGroup) inflater.inflate(R.layout.movie_detail, null);
        
        
        
          
        // Getting references to the UI widgets where info is shown
        TextView titleTextView = (TextView) getActivity().findViewById(R.id.movie_title);
        TextView originalTitleTextView = (TextView) getActivity().findViewById(R.id.movie_original_title);
        TextView productionYearTextView = (TextView) getActivity().findViewById(R.id.movie_production_year);
        TextView ratingLabelTextView = (TextView) getActivity().findViewById(R.id.movie_rating_label);
        TextView localDistributorNameTextView = (TextView) getActivity().findViewById(R.id.movie_local_distributor_name);
        TextView genresTextView = (TextView) getActivity().findViewById(R.id.movie_genres);
        TextView synopsysTextView = (TextView) getActivity().findViewById(R.id.movie_synopsis);
        ImageView largePortraitImageView = (ImageView) getActivity().findViewById(R.id.movie_large_portrait);
        
        
        // Getting the MovieInfo bean with all the info about the movie
        Bundle extras = getActivity().getIntent().getExtras(); 
        if (extras != null) {
            final MovieInfo movieInfo = (MovieInfo) extras.get(MOVIE_INFO_EXTRA);
            
            // Setting the info in the UI widgets
            titleTextView.setText(movieInfo.getTitle());
            originalTitleTextView.setText(movieInfo.getOriginalTitle());
            productionYearTextView.setText(movieInfo.getProductionYear());
            ratingLabelTextView.setText(movieInfo.getRatingLabel());
            localDistributorNameTextView.setText(movieInfo.getLocalDistributorName());
            genresTextView.setText(movieInfo.getGenres());
            synopsysTextView.setText(movieInfo.getSynopsis());
            
            ImageHelper.fillUpImageView(largePortraitImageView, movieInfo.getEventLargeImagePortrait(), R.drawable.android_99_146);
            
            fetchImdbInfo(movieInfo.getOriginalTitle(), movieInfo.getProductionYear());
        }
        
        return vg;
    }
    
    private void fetchImdbInfo(final String title, final String year) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final LinearLayout imdbInfoLayout = (LinearLayout) getActivity().findViewById(R.id.movie_imdb_info);
                final RatingBar ratingView = (RatingBar) imdbInfoLayout.findViewById(R.id.movie_imdb_rating_bar);
                
                ImdbInfo info = new ImdbApiClient().fetchInfo(title, year);
                
                final float rating = info.getRating() / 2;
                
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ratingView.setRating(rating);
                        getActivity().findViewById(R.id.movie_imdb_progress).setVisibility(View.GONE);
                        imdbInfoLayout.setVisibility(View.VISIBLE);
                    }
                });
            }
        }).start();
    }
    public static MovieDetail newInstance(int index) {
        MovieDetail f = new MovieDetail();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);

        return f;
    }

    public int getShownIndex() {
        return getArguments().getInt("index", 0);
    }

}
