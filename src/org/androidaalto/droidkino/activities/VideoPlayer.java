package org.androidaalto.droidkino.activities;

import org.androidaalto.droidkino.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * This activity would be the one playing movie trailer videos based on a MovieTrailer bean received in the intent extras
 * 
 * TODO: to add functionality for this intent and also to check for the case that the vide is a youtube video,
 * then we need to treat it differently perhaps.
 * @author marcostong17
 *
 */
public class VideoPlayer extends Activity {

    private VideoView mVideoView;

    public final static String VIDEO_URL = "video_url";
    
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.video_player);
        mVideoView = (VideoView) findViewById(R.id.video_movie_trailer);

       

        /*
         * Alternatively,for streaming media you can use
         * mVideoView.setVideoURI(Uri.parse(URLstring));
         */
        String path = getIntent().getStringExtra(VIDEO_URL);
        mVideoView.setVideoPath(path);
        mVideoView.setMediaController(new MediaController(this));
        mVideoView.requestFocus();

    }

}
