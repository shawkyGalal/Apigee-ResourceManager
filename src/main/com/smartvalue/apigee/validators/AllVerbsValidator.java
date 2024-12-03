package com.smartvalue.apigee.validators;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map.Entry;
import java.util.UUID;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.smartvalue.apigee.migration.ProcessResult;
import com.smartvalue.apigee.migration.ProcessResults;
import com.smartvalue.apigee.proxyBundle.BundleProxyEndPoint;
import com.smartvalue.apigee.proxyBundle.ProxyBundleParser;
import com.smartvalue.apigee.rest.schema.proxyEndPoint.auto.Flow;

public class AllVerbsValidator extends ProxyValidator {

	/**
	 * This Validator checks that at least there are flow for each verb (GET, POST , DELETE , PUT )
	 */
	@Override
	public ProcessResults validate(File proxyBundle , UUID uuid)  {
		ProcessResults prs = new ProcessResults(this.getClass().getName() + " Validating Proxy Pundle "+ proxyBundle , uuid); 
		try {
			ProxyBundleParser pbp = new ProxyBundleParser(proxyBundle.toString());
			for (Entry<String, BundleProxyEndPoint> xx :  pbp.getProxies().entrySet() ) 
			{
				String proxyEndPointName = xx.getKey(); 
				BundleProxyEndPoint bundleProxyEndPoint = xx.getValue(); 
				for (Flow flow :  bundleProxyEndPoint.getFlows())
				{
					
				}
			}
		} catch (XPathExpressionException | FileNotFoundException | ParserConfigurationException | SAXException e) {
			prs.add(new ProcessResult(e) ) ; 
			e.printStackTrace();
		} 
		return prs;
	}

	@Override
	public boolean filter(String  proxyBundleFileName) {
		return true;
	}

}
