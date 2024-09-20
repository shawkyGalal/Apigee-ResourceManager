package com.smartvalue.apigee.proxyBundle;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.smartvalue.apigee.rest.schema.proxyEndPoint.Flow;
import com.smartvalue.apigee.rest.schema.proxyEndPoint.auto.Request;

public class BundleFlow extends Flow {

	    public BundleFlow(Element element) {
		super(element);
		populate(element); 
	}

		
	    private void populateRequest(Element element) {
	        NodeList requestsElements = element.getElementsByTagName("Request");
	        Element requestElement1 = (Element) requestsElements.item(0);
	        this.setRequest(new Request(requestElement1)); 
	    }

	    private void populateResponse(Element element) {
	    	NodeList responseElements = element.getElementsByTagName("Response");
	        Element firstElement1 = (Element) responseElements.item(0);
	        this.setRequest(new Request(firstElement1)); 
	    }


		public void populate(Element e) {
			this.setName(e.getAttribute("name"));
			populateRequest(e) ; 
	    	populateResponse(e); 
			
		}
}
