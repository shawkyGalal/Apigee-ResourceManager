package com.smartvalue.apigee.rest.schema;

import java.util.ArrayList;
import java.util.HashMap;

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

	
	
}
