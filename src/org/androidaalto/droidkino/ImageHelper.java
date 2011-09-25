package org.androidaalto.droidkino;

import java.net.URL;
import java.util.Map;


import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

public class ImageHelper{

    private static final String LOG_TAG = ImageHelper.class.getName(); 
    
    public static void fillUpImageView(ImageView imageView, String url, int defaultIcon) {

        DroidKinoApplicationCache cache = DroidKinoApplicationCache.getInstance();
        Map<String, Drawable> movieDrawables = cache.getMovieDrawables();
        if (movieDrawables.containsKey(url)) {
            imageView.setImageDrawable(movieDrawables.get(url));
        } else {
            URL thumb_u;
            try {
                thumb_u = new URL(url);
                Drawable largePortraitDrawable = Drawable.createFromStream(thumb_u.openStream(), "src");
                imageView.setImageDrawable(largePortraitDrawable);
                movieDrawables.put(url, largePortraitDrawable);
            } catch (Exception e) {
                Log.d(LOG_TAG, e.getMessage());
                imageView.setImageResource(defaultIcon);
            }
        }
    }

}
