package org.androidaalto.droidkino;

import java.io.Serializable;

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
}
