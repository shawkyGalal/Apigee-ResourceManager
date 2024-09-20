package com.smartvalue.apigee.proxyBundle;

import java.io.IOException;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import org.apache.xerces.dom.DeferredElementImpl;

public class ProxyEndPoint extends BundleElement{

	public ProxyEndPoint(String m_name, ZipInputStream m_zipInputStream) throws IOException {
		super(m_name, m_zipInputStream);
	}
	
	public DeferredElementImpl  getFlowByName(String m_flowName) throws ParserConfigurationException, SAXException, IOException
	{
		DeferredElementImpl result = null ; 
		DeferredElementImpl rootElement = (DeferredElementImpl) this.getXmlDocument().getDocumentElement();
        NodeList flowsElements = rootElement.getElementsByTagName("Flows");
        DeferredElementImpl firstElement1 = (DeferredElementImpl) flowsElements.item(0);
        NodeList flowElements = firstElement1.getElementsByTagName("Flow");
        for (int i = 0 ; i< flowElements.getLength() ; i++ )
        {
        	DeferredElementImpl flowElement = (DeferredElementImpl) flowElements.item(i);
        	if (flowElement.getAttribute("name").equalsIgnoreCase(m_flowName))
        	{
        		result = flowElement ; 
        		break ; 
        	}
        }
        return result ; 
	}
	
	private Element getFlowChildObject (String m_flowName  , String childObjectname) throws ParserConfigurationException, SAXException, IOException
	{
		Element flowElement = this.getFlowByName(m_flowName); 
		Element requestElement = (Element) flowElement.getElementsByTagName(childObjectname).item(0);
		return requestElement;
	}
	
	public Element  getFlowRequest(String m_flowName ) throws ParserConfigurationException, SAXException, IOException
	{
		return getFlowChildObject(m_flowName , "Request"); 
	}
	
	public Element  getFlowResponse(String m_flowName ) throws ParserConfigurationException, SAXException, IOException
	{
		return getFlowChildObject(m_flowName , "Response"); 
	}
	
	public Element  getFlowCondition(String m_flowName ) throws ParserConfigurationException, SAXException, IOException
	{
		return getFlowChildObject(m_flowName , "Condition"); 
	}
	

	

}
