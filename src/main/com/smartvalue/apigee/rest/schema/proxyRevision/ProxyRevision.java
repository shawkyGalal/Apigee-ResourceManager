package com.smartvalue.apigee.rest.schema.proxyRevision;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.infra.ManagementServer;

public class ProxyRevision extends com.smartvalue.apigee.rest.schema.proxyRevision.auto.ProxyRevision{

	public void setManagementServer(ManagementServer managmentServer) {
		// TODO Auto-generated method stub
		this.setManagmentServer(managmentServer) ; 
	}
	

	
	public HashMap<String , Object >  getTargetEndPointDetails(  ) throws UnirestException, IOException
	{
		HashMap<String , Object > result = new HashMap<>() ; 
		for (Object targetEndpoint :   this.getTargetEndpoints())
		{
			String path = getResourcePath() + "/targets/"+(String) targetEndpoint  ; 
			HashMap<String , Object > xx =  this.getManagmentServer().executeGetMgmntAPI(path, HashMap.class) ; 	
			result.put((String) targetEndpoint, xx) ; 
		}
		return result ; 
	}
	
	public ArrayList<String> getTargetEndPontsUsesTargetServer(String m_targetServerName) throws UnirestException, IOException
	{
		ArrayList<String> result = new ArrayList<String>() ; 
		HashMap<String , Object > tepds = 	this.getTargetEndPointDetails() ; 
		for (String name  : tepds.keySet() )
		{
			@SuppressWarnings("unchecked")
			HashMap<String, Object> tepd = (HashMap<String, Object>) tepds.get(name) ;
			Map connection = (Map) tepd.get("connection") ;
			if (connection != null)
			{
				Map loadBalancer = (Map) connection.get("loadBalancer") ;
				if (loadBalancer != null )
				{
					ArrayList<Object> servers = (ArrayList <Object>)loadBalancer.get("server") ;
					if (servers != null )
					{
						String serverName = (String) ((Map)servers.get(0)).get("name") ;   
						if( serverName.equalsIgnoreCase(m_targetServerName) )
						{
							result.add(name) ; 
						}
						
						
					}
				}
			
			}
		}
		return result ; 
	}
	/**
	 * Checks if this proxy revision is using a policy with the given names - useful to check validity of the proxy to conform to standards and uses a shared flow like ELK-Logger 
	 * @param policynames
	 * @return
	 */
	public boolean isUsingPolicy(String[] policynames )
	{
		boolean result = false ; 
		for (String policyname : policynames )
		{
			List<String> polices = this.getPolicies() ; 
			if ( polices.contains(policyname) )
			{
				result = true ; 
				break ; 
			}
		}
		return result;
		
	}

	public String getResourcePath() {
		return "/v1/organizations/"+this.getOrgName()+"/apis/"+this.getName()+"/revisions/" + this.getRevision();
	}

}
