package com.smartvalue.apigee.rest.schema.proxy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.rest.schema.proxyDeployment.auto.Environment;
import com.smartvalue.apigee.rest.schema.proxyDeployment.ProxyDeployment;
import com.smartvalue.apigee.rest.schema.proxyDeployment.auto.Revision;
import com.smartvalue.apigee.rest.schema.proxyRevision.ProxyRevision;



public class Proxy extends com.smartvalue.apigee.rest.schema.proxy.auto.Proxy {

	
	
	@SuppressWarnings("unchecked")
	public ProxyDeployment  getDeployments() throws UnirestException, IOException
	{
		ProxyDeployment result = null; 
		String apiPath = "/v1/o/"+this.getOrgName()+"/apis/"+this.getName()+"/deployments" ; 
		result = this.getManagmentServer().executeGetMgmntAPI(apiPath , ProxyDeployment.class ) ;
		return result ; 
	}	
	
	@SuppressWarnings("unchecked")
	public ProxyRevision  getRevision(String revision ) throws UnirestException, IOException
	{
		ProxyRevision result = null; 
		String apiPath = "/v1/o/"+this.getOrgName()+"/apis/"+this.getName()+"/revisions/" + revision ; 
		result = this.getManagmentServer().executeGetMgmntAPI(apiPath , ProxyRevision.class ) ;
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
	
	public HashMap<String ,  ArrayList<String>> getRevisionsUsesTargetServer(String m_targetServerName , boolean m_deployedVersionOnly) throws UnirestException, IOException
	{
		HashMap<String , ArrayList<String>> result = new HashMap<String , ArrayList<String>>() ; 
		ArrayList<String> targetEndpointsUsesTargetServer ; 
		ProxyRevision pr ; 
		if (m_deployedVersionOnly) 
		{ 
			ProxyDeployment deployments  = this.getDeployments() ; 
		
				for ( Environment e : deployments.getEnvironment() ) 
				{
					for ( Revision rev : e.getRevision() )
					{
						pr = this.getRevision(rev.getName()) ;
						targetEndpointsUsesTargetServer = pr.getTargetEndPontsUsesTargetServer(m_targetServerName) ; 
						if ( targetEndpointsUsesTargetServer.size() > 0  )
						{
							result.put(rev.getName() , targetEndpointsUsesTargetServer) ; 
						}
					}
				}
			
		}
		else 
		{
			List<String> allRevisions = this.getRevision();
			for (String rev : allRevisions )
			{
				pr = this.getRevision(rev);
				targetEndpointsUsesTargetServer = pr.getTargetEndPontsUsesTargetServer(m_targetServerName) ; 
				if ( targetEndpointsUsesTargetServer.size() > 0  )
				{
					result.put(rev , targetEndpointsUsesTargetServer) ; 
				}
			}
		}
		
				
		return result ; 
	}
	
	public ArrayList<Object> getRevisionsNotUsingPolices(String[] policies  , boolean m_deployedVersionOnly) throws UnirestException, IOException
	{
		ArrayList<Object> result = new ArrayList<Object>() ; 
		 
		ProxyRevision pr ; 
		if (m_deployedVersionOnly) 
		{ 
			ProxyDeployment deployments  = this.getDeployments() ; 
		
				for ( Environment e : deployments.getEnvironment() ) 
				{
					for ( Revision rev : e.getRevision() )
					{
						pr = this.getRevision(rev.getName()) ;
						if ( ! pr.isUsingPolicy(policies)  )
						{
							result.add(rev.getName() ) ; 
						}
					}
				}
			
		}
		else 
		{
			List<String> allRevisions = this.getRevision();
			for (String rev : allRevisions )
			{
				pr = this.getRevision(rev);
				if ( ! pr.isUsingPolicy(policies) )
				{
					result.add(rev) ; 
				}
			}
		}
		
				
		return result ; 
	}
	
	


}
