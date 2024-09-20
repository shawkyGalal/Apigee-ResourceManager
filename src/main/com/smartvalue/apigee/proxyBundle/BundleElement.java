package com.smartvalue.apigee.proxyBundle;

import java.io.IOException;
import java.io.StringReader;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.xerces.dom.DeferredElementImpl;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public abstract class BundleElement //extends DeferredElementImpl
{
	private String name ; 
	private String content ;
	private Document xmlDocument ; 
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	} 
	
	public BundleElement (String m_name, ZipInputStream m_zipInputStream) throws IOException {
		this.setName(m_name);
		this.setContent(readFromInputStream(m_zipInputStream) ); 
	}
	
	public Document buildXmlDocument() throws ParserConfigurationException, SAXException, IOException
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
		// Parse the XML content into a Document
	    InputSource source = new InputSource(new StringReader(this.getContent()));
	    Document document = builder.parse(source);
		
		return document ; 
	}
	
	public String readFromInputStream(ZipInputStream zipInputStream) throws IOException
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
	
	public Document getXmlDocument() throws ParserConfigurationException, SAXException, IOException 
	{
		if (xmlDocument == null)
		{
			xmlDocument = this.buildXmlDocument(); 
		}
		return xmlDocument;
	}
	
	public NodeList getXpathNodeList(String xpath) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException
	{
	    XPathFactory xPathfactory = XPathFactory.newInstance();
	    XPath xpathInstance = xPathfactory.newXPath();
	    NodeList nodes = (NodeList) xpathInstance.evaluate(xpath, this.getXmlDocument(), XPathConstants.NODESET);
	    return nodes ; 
	}

}
