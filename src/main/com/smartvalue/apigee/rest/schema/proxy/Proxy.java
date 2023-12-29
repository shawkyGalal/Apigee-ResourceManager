package com.smartvalue.apigee.rest.schema.proxy;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.rest.schema.proxyDeployment.auto.Environment;
import com.smartvalue.apigee.configuration.filteredList.FilteredList;
import com.smartvalue.apigee.configuration.filteredList.ListFilter;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.rest.schema.proxyDeployment.ProxyDeployment;
import com.smartvalue.apigee.rest.schema.proxyDeployment.auto.Revision;
import com.smartvalue.apigee.rest.schema.proxyRevision.ProxyRevision;



public class Proxy extends com.smartvalue.apigee.rest.schema.proxy.auto.Proxy {

	
	
	@SuppressWarnings("unchecked")
	public ProxyDeployment  getDeployments() throws UnirestException, IOException
	{
		ProxyDeployment result = null; 
		String apiPath = getResourcePath()+"/deployments" ; 
		result = this.getManagmentServer().executeGetMgmntAPI(apiPath , ProxyDeployment.class ) ;
		return result ; 
	}	
	
	@SuppressWarnings("unchecked")
	public ProxyRevision  getRevision(String revision ) throws UnirestException, IOException
	{
		ProxyRevision result = null; 
		String apiPath = getResourcePath()+"/revisions/" + revision ; 
		result = this.getManagmentServer().executeGetMgmntAPI(apiPath , ProxyRevision.class ) ;
		result.setManagementServer(this.getManagmentServer()) ; 
		result.setOrgName(this.getOrgName());
		return result ; 
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<Integer , ProxyRevision>  getAllRevision( ) throws UnirestException, IOException
	{
		HashMap<Integer , ProxyRevision> result = new HashMap<> ();
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
	/**
	 * 
	 * @return a hashmap containing information about the deployments of a proxy 
	 * [
	 * "Env01" : [2 , 5]   // Proxy revisions 2, 5 are deployed to Env01
	 * "Env02" : [1 , 4]   // Proxy revisions 1, 4 are deployed to Env02
	 * ]
	 * @throws UnirestException
	 * @throws IOException
	 */
	public HashMap<String , ArrayList<String>> getDeployedRevisions() throws UnirestException, IOException
	{
		HashMap<String , ArrayList<String>> result = new  HashMap<String , ArrayList<String>>() ; 
		ProxyDeployment proxyDeployment = this.getDeployments();
		for ( Environment env : proxyDeployment.getEnvironment() ) 
		{
			String envName= env.getName(); 
			ArrayList<String> deployedRevision = new ArrayList<String>();  
			for (Revision revision  : env.getRevision())
			{
				deployedRevision.add(revision.getName()); 
			}
			result.put(envName, deployedRevision); 
		}
		
		return result ; 
		
	}
	
	public void export(int revision , String folderDest) throws UnirestException, IOException
	{
		HttpResponse<InputStream> result = null; 
		String apiPath = getResourcePath()+"/revisions/"+revision+"?format=bundle" ; 
		ManagementServer ms = this.getManagmentServer() ; 
		result = ms.getGetHttpBinResponse(apiPath ) ;
		
		Files.copy(result.getBody(), Paths.get(folderDest + this.getName()+".zip") , java.nio.file.StandardCopyOption.REPLACE_EXISTING );

	}

	
	public HashMap<String , Exception>  exportAllDeployedRevisions(String folderDest ) throws NumberFormatException, UnirestException, IOException
	{
		HashMap<String , Exception> failedResult = new HashMap<String , Exception>();  
		
		for ( String  DeployedEnvName :  this.getDeployedRevisions().keySet()) 
		{
			ArrayList<String > envRevisions = this.getDeployedRevisions().get(DeployedEnvName) ;
			for (String revisionString : envRevisions  )
			{
				int revision = Integer.parseInt(revisionString);
				try {
					String path = folderDest+"\\" + DeployedEnvName + "\\"+ this.getName()+"\\" + revision+"\\" ; 
					Path pathObj = Paths.get(path);
			        Files.createDirectories(pathObj);
					export(revision , path) ;
					System.out.println("Proxy " + this.getName() + " Revision " +  revision + " Exported Successfully");
				}
				catch (Exception e) {
					failedResult.put(revisionString, e); 
				}
			}
				
		}
		
		return failedResult ; 
	}

	public String getResourcePath() {
			return "/v1/organizations/"+this.getOrgName()+"/apis/"+this.getName();
	}

	
	


}
