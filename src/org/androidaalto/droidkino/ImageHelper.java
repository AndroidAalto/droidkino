package org.androidaalto.droidkino;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import java.net.URL;
import java.util.Map;


/**
 * This class provides functionality to download an image from the FinnKino server and fillup an ImageView with it.
 * 
 * 
 * @author marcostong17
 *
 */
public class ImageHelper{

    private static final String LOG_TAG = ImageHelper.class.getName(); 
    
    public static void fillUpImageView(ImageView imageView, String url, int defaultIcon) {

        DroidKinoApplicationCache cache = DroidKinoApplicationCache.getInstance();
        Map<String, Drawable> movieDrawables = cache.getMovieDrawables();
        if (movieDrawables.containsKey(url)) {
            imageView.setImageDrawable(movieDrawables.get(url));
        } else if (url != null){
            try {
                URL thumb_u = new URL(url);
                Drawable largePortraitDrawable = Drawable.createFromStream(thumb_u.openStream(), "src");
                imageView.setImageDrawable(largePortraitDrawable);
                movieDrawables.put(url, largePortraitDrawable);
            } catch (Exception e) {
                Log.e(LOG_TAG, "Unable to load image", e);
                imageView.setImageResource(defaultIcon);
            }
        }
    }

}
