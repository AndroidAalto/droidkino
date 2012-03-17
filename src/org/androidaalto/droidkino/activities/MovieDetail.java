package org.androidaalto.droidkino.activities;

import org.androidaalto.droidkino.ImageHelper;
import org.androidaalto.droidkino.R;
import org.androidaalto.droidkino.beans.ImdbInfo;
import org.androidaalto.droidkino.beans.MovieInfo;
import org.androidaalto.droidkino.imdb.ImdbApiClient;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Facebook.DialogListener;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
public class MovieDetail extends Activity {
    Facebook facebook = new Facebook("269568249790049");

    String FILENAME = "AndroidSSO_data";
    private SharedPreferences facebookPreferences;

    public static final String MOVIE_INFO_EXTRA = "movie_info";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        
        // Getting the MovieInfo bean with all the info about the movie
        final MovieInfo movieInfo = (MovieInfo) getIntent().getExtras().get(MOVIE_INFO_EXTRA);
        
        // Getting references to the UI widgets where info is shown
        TextView titleTextView = (TextView) findViewById(R.id.movie_title);
        TextView originalTitleTextView = (TextView) findViewById(R.id.movie_original_title);
        TextView productionYearTextView = (TextView) findViewById(R.id.movie_production_year);
        TextView ratingLabelTextView = (TextView) findViewById(R.id.movie_rating_label);
        TextView localDistributorNameTextView = (TextView) findViewById(R.id.movie_local_distributor_name);
        TextView genresTextView = (TextView) findViewById(R.id.movie_genres);
        TextView synopsysTextView = (TextView) findViewById(R.id.movie_synopsis);
        ImageView largePortraitImageView = (ImageView) findViewById(R.id.movie_large_portrait);
        
        
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
        
        /*ImageView videoThumbnailImageView = (ImageView) findViewById(R.id.image_video_thumbnail);
        videoThumbnailImageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent movieTrailerIntent = new Intent(MovieDetail.this,
                        VideoPlayer.class);
                movieTrailerIntent.putExtra(VideoPlayer.VIDEO_URL, movieInfo.get);
                startActivity(movieTrailerIntent);
            }
        });*/
        
        /*
         * Get existing access_token if any
         */
        facebookPreferences = getPreferences(MODE_PRIVATE);
        String access_token = facebookPreferences.getString("access_token", null);
        long expires = facebookPreferences.getLong("access_expires", 0);
        if(access_token != null) {
            facebook.setAccessToken(access_token);
        }
        if(expires != 0) {
            facebook.setAccessExpires(expires);
        }
        
        /*
         * Only call authorize if the access_token has expired.
         */
        if(!facebook.isSessionValid()) {
            // sign on to facebook
            facebook.authorize(MovieDetail.this, new String[] { "email", "publish_checkins" }, new DialogListener() {
                @Override
                public void onComplete(Bundle values) {
                    
                    SharedPreferences.Editor editor = facebookPreferences.edit();
                    editor.putString("access_token", facebook.getAccessToken());
                    editor.putLong("access_expires", facebook.getAccessExpires());
                    editor.commit();
                }
    
                @Override
                public void onCancel() {}
    
                @Override
                public void onFacebookError(FacebookError e) {
                    // TODO Auto-generated method stub
                    
                }
    
                @Override
                public void onError(DialogError e) {
                    // TODO Auto-generated method stub
                    
                }
            });
        }
        
        Button facebookButton = (Button) findViewById(R.id.facebookButton);
        facebookButton.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                //post on friend's wall.
                Bundle params = new Bundle();
                params.putString("description", movieInfo.getTitle());
                facebook.dialog(MovieDetail.this, "feed", params, new Facebook.DialogListener() {

                    @Override
                    public void onComplete(Bundle values) {
                        // TODO Auto-generated method stub
                        
                    }

                    @Override
                    public void onFacebookError(FacebookError e) {
                        // TODO Auto-generated method stub
                        
                    }

                    @Override
                    public void onError(DialogError e) {
                        // TODO Auto-generated method stub
                        
                    }

                    @Override
                    public void onCancel() {
                        // TODO Auto-generated method stub
                        
                    }
                    
                });
            }
        });
    }
    
    
    private void fetchImdbInfo(final String title, final String year) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final LinearLayout imdbInfoLayout = (LinearLayout) findViewById(R.id.movie_imdb_info);
                final RatingBar ratingView = (RatingBar) imdbInfoLayout.findViewById(R.id.movie_imdb_rating_bar);
                
                ImdbInfo info = new ImdbApiClient().fetchInfo(title, year);
                
                final float rating = info.getRating() / 2;
                
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ratingView.setRating(rating);
                        findViewById(R.id.movie_imdb_progress).setVisibility(View.GONE);
                        imdbInfoLayout.setVisibility(View.VISIBLE);
                    }
                });
            }
        }).start();
    }
    
}
