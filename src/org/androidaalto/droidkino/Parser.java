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
		NodeList nodeList = (NodeList) schedule.getElementsByTagName("Show");

		Show show = null;
		for (int i = 0; i < 10; i++) {
			Element showElement = (Element) nodeList.item(i);
			Node ddtmShowStart = ((NodeList)showElement.getElementsByTagName("dttmShowStart")).item(0);
			Node title = ((NodeList)showElement.getElementsByTagName("Title")).item(0);
			Node teathre = ((NodeList)showElement.getElementsByTagName("TheatreAndAuditorium")).item(0);
			
			show = new Show();
			show.setDttmShowStart(ddtmShowStart.getNodeValue());
			show.setTitle(title.getNodeValue());			
			show.setTheatre(teathre.getNodeValue());
			showList.add(show);
			
		}
		return showList;		
	}

}
