package org.androidaalto.droidkino;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieDetail extends Activity {

    public static final String MOVIE_INFO_EXTRA = "movie_info";
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        final MovieInfo movieInfo = (MovieInfo) getIntent().getExtras().get(MOVIE_INFO_EXTRA);
        
        TextView titleTextView = (TextView) findViewById(R.id.movie_title);
        TextView originalTitleTextView = (TextView) findViewById(R.id.movie_original_title);
        TextView productionYearTextView = (TextView) findViewById(R.id.movie_production_year);
        TextView ratingLabelTextView = (TextView) findViewById(R.id.movie_rating_label);
        TextView localDistributorNameTextView = (TextView) findViewById(R.id.movie_local_distributor_name);
        TextView genresTextView = (TextView) findViewById(R.id.movie_genres);
        TextView synopsysTextView = (TextView) findViewById(R.id.movie_synopsis);
        ImageView largePortraitImageView = (ImageView) findViewById(R.id.movie_large_portrait);
        
        
        titleTextView.setText(movieInfo.getTitle());
        originalTitleTextView.setText(movieInfo.getOriginalTitle());
        productionYearTextView.setText(movieInfo.getProductionYear());
        ratingLabelTextView.setText(movieInfo.getRatingLabel());
        localDistributorNameTextView.setText(movieInfo.getLocalDistributorName());
        genresTextView.setText(movieInfo.getGenres());
        synopsysTextView.setText(movieInfo.getSynopsis());
        
        
        ImageHelper.fillUpImageView(largePortraitImageView, movieInfo.getEventLargeImagePortrait(), R.drawable.android_99_146);
        
        
        
        /*ImageView videoThumbnailImageView = (ImageView) findViewById(R.id.image_video_thumbnail);
        videoThumbnailImageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent movieTrailerIntent = new Intent(MovieDetail.this,
                        VideoPlayer.class);
                movieTrailerIntent.putExtra(VideoPlayer.VIDEO_URL, movieInfo.get);
                startActivity(movieTrailerIntent);
            }
        });*/
    }
    
    
    
}
