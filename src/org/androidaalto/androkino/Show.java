package org.androidaalto.androkino;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Show {

	private long id;
	private Date dttmShowStart;
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
    private String PresentationMethod;
    //private List<String> eventSeries;
    private String showUrl;
    private String eventUrl;
    private List<String> eventSmallImagePortraits;
    private List<String> eventLargeImagePortraits;
    
    public Show()
    {
    	eventSmallImagePortraits = new ArrayList<String>();
    	eventLargeImagePortraits = new ArrayList<String>();
    	
    }
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getDttmShowStart() {
		return dttmShowStart;
	}
	public void setDttmShowStart(Date dttmShowStart) {
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
	public void setPresentationMethodAndLanguage(
			String presentationMethodAndLanguage) {
		this.presentationMethodAndLanguage = presentationMethodAndLanguage;
	}
	public String getPresentationMethod() {
		return PresentationMethod;
	}
	public void setPresentationMethod(String presentationMethod) {
		PresentationMethod = presentationMethod;
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
