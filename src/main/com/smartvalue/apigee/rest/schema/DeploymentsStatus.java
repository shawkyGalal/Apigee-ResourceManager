package com.smartvalue.apigee.rest.schema;

import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartvalue.apigee.resourceManager.helpers.Helper;

public class DeploymentsStatus extends HashMap<String, HashMap<String, ArrayList<String>>> {

	private static final long serialVersionUID = 1L;
		
	public ArrayList<String> getDeployedRevisions(String proxyName) {
		ArrayList<String> deployedRevisions = new ArrayList<String>() ;
		HashMap<String, ArrayList<String>> proxyDeployments = this.get(proxyName) ;  
		for ( Entry<String, ArrayList<String>> xx :   proxyDeployments.entrySet())  
		{
			deployedRevisions.addAll(xx.getValue()) ; 
		}
		return deployedRevisions ; 
	}

	public ArrayList<String> getRevisionDeployedEnvs(String proxyName , String m_revision ) {

		ArrayList<String> deployedEnvs = new ArrayList<String>() ; 
		HashMap<String, ArrayList<String>> proxyDeployments = this.get(proxyName) ; 
		for ( Entry<String, ArrayList<String>> xx :   proxyDeployments.entrySet())  
		{
			if ( xx.getValue().contains(m_revision))
			deployedEnvs.add(xx.getKey()) ; 
		}
		return deployedEnvs ; 
	}

	public String toJsonString() throws JsonProcessingException {
		return Helper.mapObjectToJsonStr(this) ; 
	}

	
	
}
