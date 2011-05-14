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

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class ParserSAX extends DefaultHandler
{
	private Boolean isParam = false;
	String param = "";
	String Value;

	public ParserSAX ()	{
		super();
	}

	@Override
	public void startDocument() throws SAXException {
		System.out.println("Start document");
	}

	@Override
	public void endDocument() throws SAXException {
		System.out.println("End document");
	}

	@Override
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts)
	throws SAXException {
		//currentElement = true;
		try {
			if (localName.equalsIgnoreCase(param))
				this.isParam = true;
		}
		catch (Exception e) {
			System.out.println("startElement exception = " + e);
		}
	}

	@Override
	public void endElement(String namespaceURI, String localName, String qName)
	throws SAXException {
		this.isParam = false;
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException 
	{
		try {
			if (isParam) {
				String now = new String(ch, start, length);
				Value = now; // done indirectly to be "safe", may be needless
			}
		}
		catch (Exception ex) {
			System.out.println("characters exception = " + ex);
		}
	}


	public String getResult() {
		return Value;
	}
}