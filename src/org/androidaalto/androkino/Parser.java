package org.androidaalto.androkino;

import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import org.androidaalto.androkino.R;

public class Parser {
	
	public static List<Show> retrieveShows()
	{
		String url = R.string.shows_url;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new InputSource(url.openStream()));
		doc.getDocumentElement().normalize();

		NodeList nodeList = doc.getElementsByTagName("item");
		
		
		return null;		
	}

}
