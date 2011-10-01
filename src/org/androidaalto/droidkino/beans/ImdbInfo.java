package org.androidaalto.droidkino.beans;


public class ImdbInfo {
    
    public static final float UNINITIALIZED_RATING = -1;

    private float rating;
    private String title;
    private String year;
    private String rated;
    private String released;
    private String genre;
    private String director;
    private String writer;
    private String acttors;
    private String plot;
    private String poster;
    private String runtime;
    private String votes;
    private String id;
    private String response;
    
    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getActtors() {
        return acttors;
    }

    public void setActtors(String acttors) {
        this.acttors = acttors;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getVotes() {
        return votes;
    }

    public void setVotes(String votes) {
        this.votes = votes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public ImdbInfo() {
        rating = UNINITIALIZED_RATING;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public float getRating() {
        return rating;
    }
    
    public boolean isUnitialized() {
        return rating == UNINITIALIZED_RATING;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getYear() {
        return year;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public String getRated() {
        return rated;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getReleased() {
        return released;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }
}
