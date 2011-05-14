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

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Parser {
   
    public static List<MovieInfo> retrieveShows() throws Exception {

        List<MovieInfo> movieList = new ArrayList<MovieInfo>();

        Document doc = null;
        try {
            URL url = new URL("http://www.finnkino.fi/xml/Schedule/");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(new InputSource(url.openStream()));
            doc.getDocumentElement().normalize();
        } catch (Exception e) {
            throw new Exception("unable to retreieve movies info", e);
        }

        Element schedule = (Element) ((NodeList) doc.getElementsByTagName("Schedule")).item(0);
        Element shows = (Element) ((NodeList) schedule.getElementsByTagName("Shows")).item(0);
        NodeList showNodeList = (NodeList) schedule.getElementsByTagName("Show");

        MovieInfo movie = null;
        
        for (int i = 0; i < showNodeList.getLength() - 1; i++) {
            Node showNode = showNodeList.item(i);
            if (showNode.getNodeType() == Node.ELEMENT_NODE) {
                Element showElement = (Element) showNode;
                movie = new MovieInfo();
                movie.setDttmShowStart(getTagValue(showElement, "dttmShowStart"));
                movie.setTitle(getTagValue(showElement, "Title"));
                movie.setTheatre(getTagValue(showElement, "TheatreAndAuditorium"));
                movieList.add(movie);
            }

        }
        return movieList;
    }

    private static String getTagValue(Element eElement, String sTag) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);

        return nValue.getNodeValue();
    }

}
