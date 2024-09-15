package com.smartvalue.apigee.rest.schema.proxy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.rest.schema.proxyDeployment.ProxyDeployment;
import com.smartvalue.apigee.rest.schema.proxyDeployment.auto.Environment;
import com.smartvalue.apigee.rest.schema.proxyDeployment.auto.Revision;
import com.smartvalue.apigee.rest.schema.proxyRevision.ProxyRevision;

public class Proxy extends com.smartvalue.apigee.rest.schema.proxy.auto.Proxy {
	
	
	public HashMap<String ,  ArrayList<String>> getRevisionsUsesTargetServer(String m_targetServerName , boolean m_deployedVersionOnly) throws UnirestException, IOException
	{
		HashMap<String , ArrayList<String>> result = new HashMap<>() ; 
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
		ArrayList<Object> result = new ArrayList<>() ; 
		 
		ProxyRevision pr ; 
		if (m_deployedVersionOnly) 
		{ 
			ProxyDeployment deployments  = this.getDeployments() ; 
			List<Environment> allEnvs =  deployments.getEnvironment() ; 
			for ( Environment e : allEnvs ) 
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
	

	public String getResourcePath() {
			return "/v1/organizations/"+this.getOrgName()+"/apis/"+this.getName();
	}

	public String generateOpenApi(String m_revision) throws UnirestException, IOException
	{
		StringBuffer result = new StringBuffer() ; 
		String delimiter = "__" ; 
		for ( Entry<String, ProxyEndpoint> entry : this.getRevision(m_revision).getProxyEndPointDetails().entrySet()) 
		{
			String proxyEnpointName = entry.getKey();
		    ProxyEndpoint proxyEndPoint = entry.getValue();
		    for (Flow flow : proxyEndPoint.getFlows())
		    {
		    	String operationId = this.getName()+ delimiter +proxyEnpointName+delimiter+flow.getName() ; 
		    	result.append("\n"+operationId) ; 
		    }
			System.out.println(proxyEndPoint) ; 
		}
		return result.toString() ; 
	}
	
	


}
