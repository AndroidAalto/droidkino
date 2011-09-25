package org.androidaalto.droidkino;

import android.app.Activity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

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
