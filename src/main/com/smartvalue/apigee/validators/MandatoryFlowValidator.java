package com.smartvalue.apigee.validators;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.Map.Entry;


import com.smartvalue.apigee.migration.ProcessResult;
import com.smartvalue.apigee.migration.ProcessResults;
import com.smartvalue.apigee.proxyBundle.BundleProxyEndPoint;
import com.smartvalue.apigee.proxyBundle.ProxyBundleParser;
import com.smartvalue.apigee.rest.schema.proxyEndPoint.auto.Flow;

public class MandatoryFlowValidator extends ProxyValidator {

	private String mandatoryFlowNames ; //"GetOAS"
	private String atLeastOnce  ; 
	private String checkLocation ; 
	private String location ; 
	
	@Override
	public ProcessResults validate(File proxyBundleFile , UUID uuid) {
		ProcessResults prs = new ProcessResults(this.getClass().getName() + " Validating Proxy Pundle "+ proxyBundleFile , uuid) ; 
		String[] mandatoryFlowNamesArray = mandatoryFlowNames.split(",") ; 
		
        try {
       	       System.out.println("Starting the Checking Proxy : " + proxyBundleFile.getName() );
               ProxyBundleParser proxyBundle = new ProxyBundleParser(proxyBundleFile.getAbsolutePath()) ;
               HashMap<String, BundleProxyEndPoint> proxyEndPoints =  proxyBundle.getProxies() ;
               boolean atLeastOnceBoolean = this.atLeastOnce != null && this.atLeastOnce.equalsIgnoreCase("true") ; 
               boolean checkLocationBool = checkLocation!= null && checkLocation.equalsIgnoreCase("true") ; 
               for ( Entry<String, BundleProxyEndPoint> pepEntrySet :  proxyEndPoints.entrySet())
               {
            	   String pepname = pepEntrySet.getKey(); 
            	   BundleProxyEndPoint pep = pepEntrySet.getValue() ;
            	   
            	   List<Flow> flows = pep.getFlows() ; 
            	   boolean flowExist = false ;
            	   for (String mandatoryFlowName : mandatoryFlowNamesArray)
            	   {   
            		   mandatoryFlowName = mandatoryFlowName.trim(); 
	            	   if (checkLocationBool)
	            	   {   
	            		   if(this.location == null) {throw new Exception(" checkLocation is true and location is not provided ");  }
	            		   int location = this.location.equalsIgnoreCase("last")? flows.size(): Integer.parseInt(this.location) ; 
	            		   if (flows.size() > 0 && flows.get(location-1).getName().equalsIgnoreCase(mandatoryFlowName))
	            		   {  flowExist = true;  break ;  }
	            	   }
	            	   else
	            	   {
		            	   for (Flow flow : flows)
		            	   {
		            		   flowExist = flow.getName().equalsIgnoreCase(mandatoryFlowName) ; 
		            		   if (flowExist) break ; 
		            	   }
		            	   if (flowExist &&  atLeastOnceBoolean ) break; 
	            	   }
            	   	}
	            	if (! flowExist )
	    	        {
	    	           prs.add(new ProcessResult()
	    	        		   .withFailed(true) 
	    	        		   .withSource(mandatoryFlowNamesArray + " Flow Does Not Exist "+((checkLocationBool)? " in Proper Location ":"") +"in Proxy "+ proxyBundleFile.getName() +",  ProxyEndPoint " + pepname )) ;  
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

	public String getMandatoryFlowName() {
		return mandatoryFlowNames;
	}


}
