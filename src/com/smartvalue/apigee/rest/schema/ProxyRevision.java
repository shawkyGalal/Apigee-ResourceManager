package com.smartvalue.apigee.rest.schema;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.resourceManager.ManagementServer;

public class ProxyRevision extends com.smartvalue.apigee.rest.schema.auto.proxyRevision.ProxyRevision{

	public void setManagementServer(ManagementServer managmentServer) {
		// TODO Auto-generated method stub
		this.setManagmentServer(managmentServer) ; 
	}
	

	
	public HashMap<String , Object >  getTargetEndPointDetails(  ) throws UnirestException, IOException
	{
		HashMap<String , Object > result = new HashMap<String , Object >() ; 
		for (Object targetEndpoint :   this.getTargetEndpoints())
		{
			String path = "/v1/o/"+this.getOrgName()+"/apis/"+this.getName()+"/revisions/" + this.getRevision() + "/targets/"+(String) targetEndpoint  ; 
			HashMap<String , Object > xx =  this.getManagmentServer().executeMgmntAPIUsingGson(path, HashMap.class, "GET") ; 	
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

}
