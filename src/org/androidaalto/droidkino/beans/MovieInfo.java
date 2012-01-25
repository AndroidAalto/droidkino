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

package org.androidaalto.droidkino.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This bean represents the info coming for movie info, i.e. a movie release with the info like year, synopsis, etc.
 * (do not confuse with schedules, which repeat info about movie but are intended for a particular show). 
 * This info shall be filled up from http://www.finnkino.fi/xml/Events
 * @author marcostong17
 *
 */
public class MovieInfo implements Serializable {

    private static final long serialVersionUID = -6630023918429921176L;

    private String eventId;

    private String title;

    private String originalTitle;

    private String productionYear;

    private String lenghtInMinutes;

    private String dtLocalRelease;

    private String ratingLabel;
    
    private String localDistributorName;
    
    private String globalDistributorName;
    
    private String genres;
    
    private String synopsis;

    private String eventSmallImagePortrait;

    private String eventLargeImagePortrait;

    private String eventSmallImageLandscape;

    private String eventLargeImageLandscape;

    private List<MovieTrailer> movieTrailers;

    public MovieInfo() {
        this.movieTrailers = new ArrayList<MovieTrailer>();
    }
    
    
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
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

    public String getLocalDistributorName() {
        return localDistributorName;
    }

    public void setLocalDistributorName(String localDistributorName) {
        this.localDistributorName = localDistributorName;
    }

    public String getGlobalDistributorName() {
        return globalDistributorName;
    }

    public void setGlobalDistributorName(String globalDistributorName) {
        this.globalDistributorName = globalDistributorName;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
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
   
    public void addMovieTrailer(MovieTrailer movieTrailer) {
        this.movieTrailers.add(movieTrailer);
    }
}
