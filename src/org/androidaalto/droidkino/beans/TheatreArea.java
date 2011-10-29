package org.androidaalto.droidkino.beans;

import java.io.Serializable;


/**
 * This bean represents the info coming for Theatre Area, which is a region of movie theatres
 * This info shall be filled up from the http://www.finnkino.fi/xml/TheatreAreas/
 *  
 * @author marcostong17
 *
 */
public class TheatreArea implements Serializable {
    
    private static final long serialVersionUID = -4149079795724782918L;
    private String id;
    private String name;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public TheatreArea copy() {
        TheatreArea copy = new TheatreArea();
        copy.id = id;
        copy.name = name;
        return copy;
    }
    
    public void clean() {
        this.id = null;
        this.name = null;
    }
}
