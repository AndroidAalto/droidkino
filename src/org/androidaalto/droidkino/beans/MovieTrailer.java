package org.androidaalto.droidkino.beans;

import java.io.Serializable;

/**
 * This bean represents the info coming for movie trailer video
 * This info shall be filled up from the "Videos" subelements (http://www.finnkino.fi/xml/Event) 
 * that optionally comes for each "Event" (Movie Info)
 *  
 * @author marcostong17
 *
 */
public class MovieTrailer implements Serializable {

    private static final long serialVersionUID = 4798296070082162066L;
    private String title;
    private String location;
    private String thumbnailLocation;
    private String mediaResourceSubType;
    private String mediaResourceFormat;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getThumbnailLocation() {
        return thumbnailLocation;
    }
    public void setThumbnailLocation(String thumbnailLocation) {
        this.thumbnailLocation = thumbnailLocation;
    }
    public String getMediaResourceSubType() {
        return mediaResourceSubType;
    }
    public void setMediaResourceSubType(String mediaResourceSubType) {
        this.mediaResourceSubType = mediaResourceSubType;
    }
    public String getMediaResourceFormat() {
        return mediaResourceFormat;
    }
    public void setMediaResourceFormat(String mediaResourceFormat) {
        this.mediaResourceFormat = mediaResourceFormat;
    }
    
    public MovieTrailer copy() {
        MovieTrailer movieTrailer = new MovieTrailer();
        movieTrailer.title = title;
        movieTrailer.location = location;
        movieTrailer.thumbnailLocation = thumbnailLocation;
        movieTrailer.mediaResourceFormat = mediaResourceFormat;
        movieTrailer.mediaResourceSubType = mediaResourceSubType;
        return movieTrailer;
    }
}
