/*******************************************************************************

Copyright: 2011 Android Aalto Community This file is part of
Droidkino. Droidkino is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 2 of the
License, or (at your option) any later version. Droidkino is
distributed in the hope that it will be useful, but WITHOUT ANY
WARRANTY; without even the implied warranty of MERCHANTABILITY or
FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
for more details. You should have received a copy of the GNU General
Public License along with Droidkino; if not, write to the Free
Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
02110-1301 USA

 ******************************************************************************/

package org.androidaalto.droidkino.xml;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/***
 * Base class for all the finniko.fi's parsers
 * 
 * @author rciovati
 */
public abstract class BaseFinnkinoParser implements MovieParser {

    // XML Tags
    protected static final String SCHEDULE = "Schedule";

    protected static final String SHOWS = "Shows";

    protected static final String SHOW = "Show";

    protected static final String DTTM_SHOW_START = "dttmShowStart";

    protected static final String TITLE = "Title";

    protected static final String THEATRE_AND_AUDITORIUM = "TheatreAndAuditorium";

    protected final URL finnikoUrl;

    public BaseFinnkinoParser() throws MalformedURLException {
        this.finnikoUrl = new URL("http://www.finnkino.fi/xml/Schedule/");
    }

    /***
     * get an InputStream object to XMl of Finniko.fi website
     * 
     * @return
     */
    protected InputStream getInputStream() {
        try {
            return finnikoUrl.openConnection().getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
