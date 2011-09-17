/*******************************************************************************

   Copyright: 2011 Android Aalto Community

   This file is part of Droidkino.

   Droidkino is free software; you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation; either version 2 of the License, or
   (at your option) any later version.

   Droidkino is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with Droidkino; if not, write to the Free Software
   Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

 ******************************************************************************/

package org.androidaalto.droidkino;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MovieInfo implements Serializable {

    private static final long serialVersionUID = -6630023918429921176L;

    private long id;

    private String dttmShowStart;

    private long eventId;

    private String title;

    private String originalTitle;

    private String productionYear;

    private String lenghtInMinutes;

    private String dtLocalRelease;

    private String ratingLabel;

    private String genres;

    private int theathreId;

    private String theatre;

    private String theatreAuditorium;

    private boolean is3D;

    private String language;

    // private List<String> eventSeries;
    private String showUrl;

    private String eventUrl;

    private String eventSmallImagePortrait;

    private String eventLargeImagePortrait;

    private String eventSmallImageLandscape;

    private String eventLargeImageLandscape;

    
  

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDttmShowStart() {
        return dttmShowStart;
    }

    public void setDttmShowStart(String dttmShowStart) {
        this.dttmShowStart = dttmShowStart;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(String productionYear) {
        this.productionYear = productionYear;
    }

    public String getLenghtInMinutes() {
        return lenghtInMinutes;
    }

    public void setLenghtInMinutes(String lenghtInMinutes) {
        this.lenghtInMinutes = lenghtInMinutes;
    }

    public String getDtLocalRelease() {
        return dtLocalRelease;
    }

    public void setDtLocalRelease(String dtLocalRelease) {
        this.dtLocalRelease = dtLocalRelease;
    }

    public String getRatingLabel() {
        return ratingLabel;
    }

    public void setRatingLabel(String ratingLabel) {
        this.ratingLabel = ratingLabel;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public int getTheathreId() {
        return theathreId;
    }

    public void setTheathreId(int theathreId) {
        this.theathreId = theathreId;
    }

    public String getTheatre() {
        return theatre;
    }

    public void setTheatre(String theatre) {
        this.theatre = theatre;
    }

    public String getTheatreAuditorium() {
        return theatreAuditorium;
    }

    public void setTheatreAuditorium(String theatreAuditorium) {
        this.theatreAuditorium = theatreAuditorium;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean is3D() {
        return is3D;
    }

    public void set3D(boolean is3D) {
        this.is3D = is3D;
    }

    public String getShowUrl() {
        return showUrl;
    }

    public void setShowUrl(String showUrl) {
        this.showUrl = showUrl;
    }

    public String getEventUrl() {
        return eventUrl;
    }

    public void setEventUrl(String eventUrl) {
        this.eventUrl = eventUrl;
    }

    public String getEventSmallImagePortrait() {
        return eventSmallImagePortrait;
    }

    public void setEventSmallImagePortrait(String eventSmallImagePortrait) {
        this.eventSmallImagePortrait = eventSmallImagePortrait;
    }

    public String getEventLargeImagePortrait() {
        return eventLargeImagePortrait;
    }

    public void setEventLargeImagePortrait(String eventLargeImagePortrait) {
        this.eventLargeImagePortrait = eventLargeImagePortrait;
    }
    
    
    public String getEventSmallImageLandscape() {
        return eventSmallImageLandscape;
    }

    public void setEventSmallImageLandscape(String eventSmallImageLandscape) {
        this.eventSmallImageLandscape = eventSmallImageLandscape;
    }

    public String getEventLargeImageLandscape() {
        return eventLargeImageLandscape;
    }

    public void setEventLargeImageLandscape(String eventLargeImageLandscape) {
        this.eventLargeImageLandscape = eventLargeImageLandscape;
    }
    
    

    public Date getStartingDate() {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ss"); // like
        // 2011-05-14T11:00:00
        try {
            return formatter.parse(dttmShowStart);
        } catch (ParseException e) {
            return null; // not so bad returning null
        }
    }

    public MovieInfo copy() {
        MovieInfo copy = new MovieInfo();
        copy.id = id;
        copy.dttmShowStart = dttmShowStart;
        copy.eventId = eventId;
        copy.title = title;
        copy.originalTitle = originalTitle;
        copy.productionYear = productionYear;
        copy.lenghtInMinutes = lenghtInMinutes;
        copy.dtLocalRelease = dtLocalRelease;
        copy.ratingLabel = ratingLabel;
        copy.genres = genres;
        copy.theathreId = theathreId;
        copy.theatre = theatre;
        copy.theatreAuditorium = theatreAuditorium;
        copy.language = language;
        copy.is3D = is3D;
        copy.showUrl = showUrl;
        copy.eventUrl = eventUrl;
        copy.eventSmallImagePortrait = eventSmallImagePortrait;
        copy.eventLargeImagePortrait = eventLargeImagePortrait;

        copy.eventSmallImageLandscape = eventSmallImageLandscape;
        copy.eventLargeImageLandscape = eventLargeImageLandscape;
        return copy;
    }

}
