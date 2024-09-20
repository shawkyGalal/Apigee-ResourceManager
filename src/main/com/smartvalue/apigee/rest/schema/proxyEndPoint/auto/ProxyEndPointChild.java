package com.smartvalue.apigee.rest.schema.proxyEndPoint.auto;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public  class ProxyEndPointChild {
	
	private Element xmlElement;

	
	public ProxyEndPointChild(Element element ) {
	}

	public Element getXmlElement() {
		return xmlElement;
	}
	
	public Element getChildElement (String childObjectname) throws ParserConfigurationException, SAXException, IOException
	{
		Element requestElement = (Element) this.getXmlElement().getElementsByTagName(childObjectname).item(0);
		return requestElement;
	}

}
