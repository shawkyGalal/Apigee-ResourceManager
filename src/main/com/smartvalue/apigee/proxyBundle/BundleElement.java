package com.smartvalue.apigee.proxyBundle;

import java.io.IOException;
import java.io.StringReader;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public abstract class BundleElement {
	
	private Element xmlElement;
	private String bundleName ; 
	
	public Element getXmlElement() {
		return xmlElement;
	}
	
	public String getBundleName() {
		return bundleName;
	}
	
	
	public BundleElement(String m_proxyName , Element element ) {
		this.xmlElement = element ; 
		this.bundleName = m_proxyName ; 
	}
	
	public BundleElement(ZipInputStream zipInputStream) throws ParserConfigurationException, SAXException, IOException {
		this.xmlElement = buildXmlDocument(zipInputStream).getDocumentElement() ;
	}
	
	public Element getChildElement (String childObjectname) throws ParserConfigurationException, SAXException, IOException
	{
		Element requestElement = (Element) this.getXmlElement().getElementsByTagName(childObjectname).item(0);
		return requestElement;
	}

	public static Document buildXmlDocument(ZipInputStream m_zipInputStream) throws ParserConfigurationException, SAXException, IOException
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
		// Parse the XML content into a Document
	    InputSource source = new InputSource(new StringReader(readFromInputStream(m_zipInputStream)));
	    Document document = builder.parse(source);
		
		return document ; 
	}

    public static String readFromInputStream(ZipInputStream zipInputStream) throws IOException
	{
		// Buffer for reading data from the zip entry
	    byte[] buffer = new byte[1024];
	    int bytesRead;
	
	    // Use a StringBuilder to accumulate the XML content
	    StringBuilder xmlContentBuilder = new StringBuilder();
		
	    // Read XML content from the zip entry
	    while ((bytesRead = zipInputStream.read(buffer)) != -1) {
	        // Append the read bytes to the StringBuilder
	        xmlContentBuilder.append(new String(buffer, 0, bytesRead));
	    }
	    return xmlContentBuilder.toString(); 
		
	}

    public String getXpathValue(String xptah) throws XPathExpressionException
    {
    	String result ; 
    	XPath xPath = XPathFactory.newInstance().newXPath();
    	result = xPath.evaluate(xptah, this.getXmlElement(), XPathConstants.STRING).toString();
    	return result ; 
    	
    }

}