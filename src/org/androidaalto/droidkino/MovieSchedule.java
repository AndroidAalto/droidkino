package org.androidaalto.droidkino;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MovieSchedule  implements Serializable {

    private static final long serialVersionUID = -4857864210593759038L;
    private String eventId;
    private String title;
    private int lengthInMinutes;
    private String dttmShowStart;
    private String theatreId;
    private String theatre;
    private String theatreAuditorium;
    private boolean is3D;
    private String language;
    private String eventSmallImagePortrait;

    
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
    public int getLengthInMinutes() {
        return lengthInMinutes;
    }
    public void setLengthInMinutes(int lenghtInMinutes) {
        this.lengthInMinutes = lenghtInMinutes;
    }
    public String getDttmShowStart() {
        return dttmShowStart;
    }
    public void setDttmShowStart(String dttmShowStart) {
        this.dttmShowStart = dttmShowStart;
    }
    public String getTheatreId() {
        return theatreId;
    }
    public void setTheatreId(String theatreId) {
        this.theatreId = theatreId;
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
    
    public String getEventSmallImagePortrait() {
        return eventSmallImagePortrait;
    }

    public void setEventSmallImagePortrait(String eventSmallImagePortrait) {
        this.eventSmallImagePortrait = eventSmallImagePortrait;
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

    
    public MovieSchedule copy() {
        MovieSchedule copy = new MovieSchedule();
        copy.eventId = eventId;
        copy.title = title;
        copy.lengthInMinutes = lengthInMinutes;
        copy.dttmShowStart = dttmShowStart;
        copy.theatreId = theatreId;
        copy.theatre = theatre;
        copy.theatreAuditorium = theatreAuditorium;
        copy.is3D = is3D;
        copy.language = language;
        copy.eventSmallImagePortrait = eventSmallImagePortrait;
        return copy;
    }
}
