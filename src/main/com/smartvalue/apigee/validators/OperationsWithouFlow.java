package com.smartvalue.apigee.validators;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.Map.Entry;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.smartvalue.apigee.migration.ProcessResult;
import com.smartvalue.apigee.migration.ProcessResults;
import com.smartvalue.apigee.migration.export.ExportResult;
import com.smartvalue.apigee.migration.export.ExportResults;
import com.smartvalue.apigee.proxyBundle.BundleProxyEndPoint;
import com.smartvalue.apigee.proxyBundle.ProxyBundleParser;
import com.smartvalue.apigee.resourceManager.helpers.Helper;
import com.smartvalue.apigee.rest.schema.BundleObjectService;
import com.smartvalue.apigee.rest.schema.proxyEndPoint.auto.Flow;
import com.smartvalue.apigee.rest.schema.proxyRevision.OasOperation;
import com.smartvalue.spring.apigee.DocWithoutFlow;

public class OperationsWithouFlow extends ProxyValidator {

	private String conditionalFlowHookStepName ; //=  "FC-Conditional-Flow-Hook" ;
	
	/**
	 * This Validator checks that each GetOAS Operation Has a matched proxy flow  
	 */
	@Override
	public ProcessResults validate(File proxyBundleFile , UUID uuid) {
		ProcessResults prs = new ProcessResults(this.getClass().getName() + " Validating Proxy Pundle "+ proxyBundleFile , uuid) ; 
        try {
       	       System.out.println("Starting the Checking Proxy : " + proxyBundleFile.getName() );
       	       ProxyBundleParser proxyBundle = new ProxyBundleParser(proxyBundleFile.getAbsolutePath()) ;
	           HashMap<OasOperation, Flow> revisionOperationsFlows = proxyBundle.checkOpenApiConsistancy(false, false) ; 
	           for (Entry<OasOperation, Flow> operationsFlows : revisionOperationsFlows.entrySet())
	           {
	        	   Flow flow = operationsFlows.getValue() ;
	        	   OasOperation  oasOperation = operationsFlows.getKey() ;
	        	   if (flow==null)
	               	{
	        		   ProcessResult zz = new  ProcessResult() ;
	        		   zz.setDescription(this.getClass().getName() + ": InConsistancy between Proxy Flows and OAS Operations") ; 
	        		   zz.setError("OAS Operation with Path = " + oasOperation.getPath() +" and Verb = "+  oasOperation.getVerb() + " Has No Matched Flow in the Proxy" );
	        		   zz.setFailed(true);
	        		   prs.add(zz);    
	               	}					  
	            }
      		
        	} catch (Exception e) {
             e.printStackTrace();
             prs.add(new ProcessResult(e)) ; 
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
