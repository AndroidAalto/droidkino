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

import java.util.List;

import org.androidaalto.droidkino.beans.MovieInfo;
import org.androidaalto.droidkino.beans.MovieSchedule;
import org.androidaalto.droidkino.beans.TheatreArea;

/***
 * All the parser in Droidkino should implement this interface
 * 
 * @author rciovati, marcostong17
 */
public interface MovieParser {

    /** Will parse at list of movie info from the event xml in finnkino
     * @param areaId the Id of the movie theatre area or null for all areas
     * @return a list of movie info for the specified area (if specified) in the order that they are parsed.
     */
    public List<MovieInfo> parseMovies(String areaId);
    
    /** Will parse the area xml in finnkino
     * @return a list of TheatreArea in the order that they are parsed
     */
    public List<TheatreArea> parseAreas(); 

    /** Will parse list of schedules from the schedule xml in finnkino
     * @param areaId (optional) the ID of a particular movie theatre area or null for all areas
     * @param date (optional) the date to look for schedules in the format dd.mm.yyyy, by default today.
     * @param eventId (optional) the ID of a particular movie or null for all movies
     * @return a list of MovieSchedules in the order that they are parsed, if specified it filters by areaId and eventId
     */
    public List<MovieSchedule> parseSchedules(String areaId, String date, String eventId); 

}
