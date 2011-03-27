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
	
	public static List<Show> retrieveShows() throws Exception
	{
		
		List<Show> showList = new ArrayList<Show>();
        
		Document doc = null;
		try
		{
			URL url = new URL(
			"http://www.finnkino.fi/xml/Schedule/");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(new InputSource(url.openStream()));
			doc.getDocumentElement().normalize();
		}
		catch(Exception e)
		{
			throw new Exception("unable to retreieve movies info", e);
		}
        
		
		Element schedule = (Element) ((NodeList) doc.getElementsByTagName("Schedule")).item(0);
		Element shows = (Element) ((NodeList) schedule.getElementsByTagName("Shows")).item(0);
		NodeList showNodeList = (NodeList) schedule.getElementsByTagName("Show");

		Show show = null;
		for (int i = 0; i < showNodeList.getLength()-1; i++) {
			Node showNode = showNodeList.item(i); 
			if (showNode.getNodeType() == Node.ELEMENT_NODE) {
				Element showElement = (Element) showNode;
				
				show = new Show();
				show.setDttmShowStart(getTagValue(showElement, "dttmShowStart"));
				show.setTitle(getTagValue(showElement, "Title"));			
				show.setTheatre(getTagValue(showElement, "TheatreAndAuditorium"));
				showList.add(show);
			}
			
		}
		return showList;		
	}
	
	private static String getTagValue(Element eElement, String sTag) {
	    NodeList nlList= eElement.getElementsByTagName(sTag).item(0).getChildNodes();
	    Node nValue = (Node) nlList.item(0); 
	 
	    return nValue.getNodeValue();    
	 }

}
