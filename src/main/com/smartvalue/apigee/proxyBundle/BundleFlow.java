package com.smartvalue.apigee.proxyBundle;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.smartvalue.apigee.rest.schema.proxyEndPoint.Flow;
import com.smartvalue.apigee.rest.schema.proxyEndPoint.auto.Child;
import com.smartvalue.apigee.rest.schema.proxyEndPoint.auto.Request;
import com.smartvalue.apigee.rest.schema.proxyRevision.ProxyRevision;

import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.parser.core.models.SwaggerParseResult;

public class BundleFlow extends Flow {

	    public BundleFlow(String proxyName, Element element) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		super(proxyName, element);
		populate(proxyName , element); 
	}

		
	    private void populateRequest(String proxyName, Element element) throws ParserConfigurationException, SAXException, IOException {
	        NodeList requestsElements = element.getElementsByTagName("Request");
	        Element requestElement1 = (Element) requestsElements.item(0);
	        if (requestElement1 != null) this.setRequest(new Request(proxyName,requestElement1)); 
	    }

	    private void populateResponse(String proxyName, Element element) {
	    	NodeList responseElements = element.getElementsByTagName("Response");
	        Element responseElement = (Element) responseElements.item(0);
	        this.setResponse(new com.smartvalue.apigee.rest.schema.proxyEndPoint.auto.Response (proxyName,responseElement)); 
	    }
	    
	    private void populateCondition(String proxyName, Element element) throws XPathExpressionException {
	    	String cond = this.getXpathValue("Condition") ; 
	        this.setCondition(cond); 
	    }


		public void populate(String proxyName , Element e) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
			this.setName(e.getAttribute("name"));
			populateRequest(proxyName , e) ; 
	    	populateResponse(proxyName, e); 
	    	populateCondition(proxyName, e) ; 
			
		}
		
		public String getUniqueIdentifier()
		{
			com.smartvalue.apigee.rest.schema.proxyEndPoint.ProxyEndpoint pep = this.getParentProxyEndPoint() ; 
			
			return this.getBundleName() + "."+ pep.getName() +"." + this.getName() ; //+"."+ this.extractPathSuffixFromCondition() +"."+this.extractVerbFromCondition(); 
		}
		
		
	


}
