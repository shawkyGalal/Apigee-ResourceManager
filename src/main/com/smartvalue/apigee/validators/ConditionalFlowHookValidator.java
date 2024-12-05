package com.smartvalue.apigee.validators;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.UUID;
import java.util.Map.Entry;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.smartvalue.apigee.migration.ProcessResult;
import com.smartvalue.apigee.migration.ProcessResults;
import com.smartvalue.apigee.proxyBundle.BundleProxyEndPoint;
import com.smartvalue.apigee.proxyBundle.ProxyBundleParser;
import com.smartvalue.apigee.rest.schema.proxyEndPoint.auto.Flow;

public class ConditionalFlowHookValidator extends ProxyValidator {

	private String conditionalFlowHookStepName ; //=  "FC-Conditional-Flow-Hook" ;
	
	/**
	 * This Validator checks that each flow Contains the mandatory "Conditional-Flow-How" as the first policy in the flow 
	 */
	@Override
	public ProcessResults validate(File proxyBundle , UUID uuid) {
		ProcessResults prs = new ProcessResults(this.getClass().getName() + " Validating Proxy Pundle "+ proxyBundle  , uuid ); 
		ProxyBundleParser pbp = null ; 
		try {
			pbp = new ProxyBundleParser(proxyBundle.toString());
			} catch (XPathExpressionException | FileNotFoundException | ParserConfigurationException | SAXException e) {
				prs.add(new ProcessResult(e) ) ; 
				e.printStackTrace();
				return prs ; 
			}
		
			for (Entry<String, BundleProxyEndPoint> xx :  pbp.getProxies().entrySet() ) 
			{
				String proxyEndPointName = xx.getKey(); 
				BundleProxyEndPoint bundleProxyEndPoint = xx.getValue(); 
				for (Flow flow :  bundleProxyEndPoint.getFlows())
				{
					String flowName = flow.getName();  
					if ((flowName.equalsIgnoreCase("GetOAS") 
							|| flowName.equalsIgnoreCase("ServiceNotAvailable") ) 
							|| flowName.equalsIgnoreCase("Service-Not-Found") )
					{ continue ;}
					if (flow.getRequest().getChildren().size()>0 )
					{
						String  firstStepName = flow.getRequest().getChildren().get(0).getStep().getName() ;
						if (firstStepName.equalsIgnoreCase(this.getConditionalFlowHookStepName()))
						{ continue ;}
						else
						{
							ProcessResult pr = new ProcessResult()
									.withFailed(true) 
									.withSource( "Takamul Compliance Issue : " + proxyBundle.getName()+"."+ proxyEndPointName + "." +  flowName + " Has No FC-Conditional-Flow-Hook as the first policy in the flow ");
							prs.add(pr); 
						}
					}
				}
			}
		
		return prs;
	}
	
	@Override
	public boolean filter(String  proxyBundleFileName) {
		return true;
	}

	public String getConditionalFlowHookStepName() {
		return conditionalFlowHookStepName;
	}

	public void setConditionalFlowHookStepName(String conditionalFlowHookStepName) {
		this.conditionalFlowHookStepName = conditionalFlowHookStepName;
	}


}
