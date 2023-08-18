package com.smartvalue.apigee.rest.schema.proxy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.rest.schema.proxyRevision.ProxyRevision;

public class Proxy extends com.smartvalue.apigee.rest.schema.proxy.auto.Proxy {

	
	
	@SuppressWarnings("unchecked")
	public Map<String, Object>  getDeployments() throws UnirestException, IOException
	{
		Map<String, Object> result = null; 
		String apiPath = "/v1/o/"+this.getOrgName()+"/apis/"+this.getName()+"/deployments" ; 
		result = this.getManagmentServer().executeMgmntAPI(apiPath , Map.class , "GET") ;
		return result ; 
	}	
	
	@SuppressWarnings("unchecked")
	public ProxyRevision  getRevision(String revision ) throws UnirestException, IOException
	{
		ProxyRevision result = null; 
		String apiPath = "/v1/o/"+this.getOrgName()+"/apis/"+this.getName()+"/revisions/" + revision ; 
		result = this.getManagmentServer().executeMgmntAPIUsingGson(apiPath , ProxyRevision.class , "GET") ;
		result.setManagementServer(this.getManagmentServer()) ; 
		result.setOrgName(this.getOrgName());
		return result ; 
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<Integer , ProxyRevision>  getAllRevision( ) throws UnirestException, IOException
	{
		HashMap<Integer , ProxyRevision> result = new HashMap<Integer , ProxyRevision> ();
		List<String> revisions = this.getRevision(); 
		for (String revision : revisions )
		{
			ProxyRevision proxyRevision = getRevision (revision) ; // this.getManagmentServer().executeMgmntAPI(apiPath , ProxyRevision.class , "GET") ;
			result.put(Integer.parseInt(revision), proxyRevision) ; 
		}
		
		return result ; 
	}
	
	public HashMap<String ,  ArrayList<String>> getRevisionsUsesTargetServer(String m_targetServerName) throws UnirestException, IOException
	{
		HashMap<String , ArrayList<String>> result = new HashMap<String , ArrayList<String>>() ; 
		List<String> revisions = this.getRevision(); 
		for (String rev : revisions )
		{
			ProxyRevision  pr = this.getRevision(rev);
			ArrayList<String> aa = pr.getTargetEndPontsUsesTargetServer(m_targetServerName) ; 
			if ( aa.size() > 0  )
			{
				result.put(rev , aa) ; 
			}
		}
				
		return result ; 
	}
	
	


}
