package org.androidaalto.droidkino;

import java.io.Serializable;

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
