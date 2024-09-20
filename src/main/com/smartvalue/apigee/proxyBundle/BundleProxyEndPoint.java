package com.smartvalue.apigee.proxyBundle;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.smartvalue.apigee.rest.schema.proxyEndPoint.ProxyEndpoint;
import com.smartvalue.apigee.rest.schema.proxyEndPoint.auto.Flow;

public class BundleProxyEndPoint extends ProxyEndpoint {

	//--------------------------------
	 private Document xmlDocument ; 

	 public BundleProxyEndPoint(ZipInputStream m_zipInputStream) throws ParserConfigurationException, SAXException, IOException 
	 {
	    	this.xmlDocument = buildXmlDocument(m_zipInputStream) ; 
	    	populateFlows();  
	 }   

	 public Document getXmlDocument() {
			return xmlDocument;
	 }

    public Element  populateFlows() throws ParserConfigurationException, SAXException, IOException
	{
    	Element result = null ; 
    	Element rootElement = (Element) this.getXmlDocument().getDocumentElement();
        NodeList flowsElements = rootElement.getElementsByTagName("Flows");
        Element firstElement1 = (Element) flowsElements.item(0);
        NodeList flowElements = firstElement1.getElementsByTagName("Flow");
        List<Flow> allflows = this.getFlows() ;  
        for (int i = 0 ; i< flowElements.getLength() ; i++ )
        {
        	Element flowElement = (Element) flowElements.item(i);
        	allflows.add(new BundleFlow(flowElement)) ; 
        
        }
        return result ; 
	}
	    
	public Document buildXmlDocument(ZipInputStream m_zipInputStream) throws ParserConfigurationException, SAXException, IOException
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

}
