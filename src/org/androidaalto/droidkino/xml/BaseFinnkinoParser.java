/*******************************************************************************

Copyright: 2011 Android Aalto Community This file is part of
Droidkino. Droidkino is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 2 of the
License, or (at your option) any later version. Droidkino is
distributed in the hope that it will be useful, but WITHOUT ANY
WARRANTY; without even the implied warranty of MERCHANTABILITY or
FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
for more details. You should have received a copy of the GNU General
Public License along with Droidkino; if not, write to the Free
Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
02110-1301 USA

 ******************************************************************************/

package org.androidaalto.droidkino.xml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/***
 * Base class for all the finniko.fi's parsers
 * 
 * @author rciovati, marcostong17
 */
public abstract class BaseFinnkinoParser implements MovieParser {

    
    // Areas XML Tags
    public static final String THEATRE_AREAS = "TheatreAreas";

    public static final String THEATRE_AREA = "TheatreArea";

    public static final String ID = "ID";

    public static final String NAME = "Name";
    

    //Schedule XML Tags
    public static final String SCHEDULE = "Schedule";

    public static final String SHOWS = "Shows";

    public static final String SHOW = "Show";
    
    public static final String EVENT_ID = "EventID";

    public static final String DTTM_SHOW_START = "dttmShowStart";

    //This is not really part of the xml
    //it is used to search by time
    public static final String DTTM_SHOW_END = "dttmShowEnd";
    
    
    public static final String THEATRE_ID = "TheatreID";

    public static final String THEATRE = "Theatre";

    public static final String THEATRE_AUDITORIUM = "TheatreAuditorium";

    public static final String PRESENTATION_METHOD_AND_LANGAUGE = "PresentationMethodAndLanguage";
    
    
    // Events XML Tags
    public static final String EVENTS = "Events";
    
    public static final String EVENT = "Event";

    public static final String TITLE = "Title";

    public static final String ORIGINAL_TITLE = "OriginalTitle";

    public static final String PRODUCTION_YEAR = "ProductionYear";
    
    public static final String LENGTH_IN_MINUTES = "LengthInMinutes";
    
    public static final String DATE_LOCAL_RELEASE = "dtLocalRelease";
    
    public static final String RATING_LABEL = "RatingLabel";

    public static final String LOGAL_DISTRIBUTOR_NAME = "LocalDistributorName";
    
    public static final String GLOBAL_DISTRIBUTOR_NAME = "GlobalDistributorName";
    
    public static final String GENRES = "Genres";

    public static final String SYNOPSIS = "Synopsis";

    public static final String IMAGES = "Images";
    
    public static final String EVENT_SMALL_IMAGE_PORTRAIT = "EventSmallImagePortrait";
    
    public static final String EVENT_LARGE_IMAGE_PORTRAIT = "EventLargeImagePortrait";
    
    public static final String EVENT_SMALL_IMAGE_LANDSCAPE = "EventSmallImageLandscape";
    
    public static final String EVENT_LARGE_IMAGE_LANDSCAPE = "EventLargeImageLandscape";

    public static final String VIDEOS = "Videos";
    
    public static final String EVENT_VIDEO = "EventVideo";

    public static final String LOCATION = "Location";
    
    public static final String THUMBNAIL_LOCATION = "ThumbnailLocation";
    
    public static final String MEDIA_RESOURCE_FORMAT = "MediaResourceFormat";
    
    public static final String MEDIA_RESOURCE_SUB_TYPE = "MediaResourceSubType";
    


    // URLs
    
    public static final String BASE_FINN_FINO_URL = "http://www.finnkino.fi/xml/";
    
    public static final String PARAM_AREA = "area";
    
    public static final String PARAM_EVENT_ID = "eventID";
    
    public static final String PARAM_DATE = "dt";


    /***
     * get an InputStream object to XMl of Finniko.fi website
     * 
     * @return
     */
    protected InputStream getInputStream(URL url) {
        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
