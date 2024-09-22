package com.smartvalue.apigee.proxyBundle;

import java.io.IOException;

import java.util.List;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.smartvalue.apigee.rest.schema.proxyEndPoint.ProxyEndpoint;
import com.smartvalue.apigee.rest.schema.proxyEndPoint.auto.Flow;

public class BundleProxyEndPoint extends ProxyEndpoint {

	public BundleProxyEndPoint(String proxyName, Element element) throws ParserConfigurationException, SAXException, IOException {
		super(proxyName , element);
		this.setName(element.getAttribute("name"));
		populateFlows(proxyName , element) ; 
		
	}
	
	


    private void  populateFlows(String proxyName, Element rootElement) throws ParserConfigurationException, SAXException, IOException
	{
    	
        NodeList flowsElements = rootElement.getElementsByTagName("Flows");
        Element firstElement1 = (Element) flowsElements.item(0);
        NodeList flowElements = firstElement1.getElementsByTagName("Flow");
        List<Flow> allflows = this.getFlows() ;  
        for (int i = 0 ; i< flowElements.getLength() ; i++ )
        {
        	Element flowElement = (Element) flowElements.item(i);
        	allflows.add(new BundleFlow(proxyName , flowElement)) ; 
        
        }

	}
	    


}
