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

    private short productionYear;

    private short lenghtInMinutes;

    private Date dtLocalRelease;

    private char raiting;

    private char ratingLabel;

    private String ratingImageUrl;

    private String genres;

    private int theathreId;

    private int theatreAuditriumID;

    private String theatre;

    private String theatreAuditorium;

    private String theatreAndAuditorium;

    private String presentationMethodAndLanguage;

    private String presentationMethod;

    // private List<String> eventSeries;
    private String showUrl;

    private String eventUrl;

    private List<String> eventSmallImagePortraits;

    private List<String> eventLargeImagePortraits;

    public MovieInfo() {
        eventSmallImagePortraits = new ArrayList<String>();
        eventLargeImagePortraits = new ArrayList<String>();

    }

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

    public short getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(short productionYear) {
        this.productionYear = productionYear;
    }

    public short getLenghtInMinutes() {
        return lenghtInMinutes;
    }

    public void setLenghtInMinutes(short lenghtInMinutes) {
        this.lenghtInMinutes = lenghtInMinutes;
    }

    public Date getDtLocalRelease() {
        return dtLocalRelease;
    }

    public void setDtLocalRelease(Date dtLocalRelease) {
        this.dtLocalRelease = dtLocalRelease;
    }

    public char getRaiting() {
        return raiting;
    }

    public void setRaiting(char raiting) {
        this.raiting = raiting;
    }

    public char getRatingLabel() {
        return ratingLabel;
    }

    public void setRatingLabel(char ratingLabel) {
        this.ratingLabel = ratingLabel;
    }

    public String getRatingImageUrl() {
        return ratingImageUrl;
    }

    public void setRatingImageUrl(String ratingImageUrl) {
        this.ratingImageUrl = ratingImageUrl;
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

    public int getTheatreAuditriumID() {
        return theatreAuditriumID;
    }

    public void setTheatreAuditriumID(int theatreAuditriumID) {
        this.theatreAuditriumID = theatreAuditriumID;
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

    public String getTheatreAndAuditorium() {
        return theatreAndAuditorium;
    }

    public void setTheatreAndAuditorium(String theatreAndAuditorium) {
        this.theatreAndAuditorium = theatreAndAuditorium;
    }

    public String getPresentationMethodAndLanguage() {
        return presentationMethodAndLanguage;
    }

    public void setPresentationMethodAndLanguage(String presentationMethodAndLanguage) {
        this.presentationMethodAndLanguage = presentationMethodAndLanguage;
    }

    public String getPresentationMethod() {
        return presentationMethod;
    }

    public void setPresentationMethod(String presentationMethod) {
        this.presentationMethod = presentationMethod;
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

    public List<String> getEventSmallImagePortraits() {
        return eventSmallImagePortraits;
    }

    public void setEventSmallImagePortraits(List<String> eventSmallImagePortraits) {
        this.eventSmallImagePortraits = eventSmallImagePortraits;
    }

    public List<String> getEventLargeImagePortraits() {
        return eventLargeImagePortraits;
    }

    public void setEventLargeImagePortraits(List<String> eventLargeImagePortraits) {
        this.eventLargeImagePortraits = eventLargeImagePortraits;
    }

}
