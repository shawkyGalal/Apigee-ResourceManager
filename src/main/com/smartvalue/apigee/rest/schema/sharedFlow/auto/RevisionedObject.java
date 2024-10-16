package com.smartvalue.apigee.rest.schema.sharedFlow.auto;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.migration.export.ExportResult;
import com.smartvalue.apigee.migration.export.ExportResults;
import com.smartvalue.apigee.rest.schema.ApigeeComman;
import com.smartvalue.apigee.rest.schema.proxyDeployment.ProxyDeployment;
import com.smartvalue.apigee.rest.schema.proxyDeployment.auto.Environment;
import com.smartvalue.apigee.rest.schema.proxyDeployment.auto.Revision;
import com.smartvalue.apigee.rest.schema.proxyRevision.ProxyRevision;

public abstract class RevisionedObject extends ApigeeComman {
	
	public abstract String getResourcePath();
	public abstract List<String> getRevision(); 
	public abstract String getName(); 
	
	public void export(int revision , String folderDest) throws UnirestException, IOException
	{
		HttpResponse<InputStream> result = null; 
		String apiPath = getResourcePath()+"/revisions/"+revision+"?format=bundle" ; 
		ManagementServer ms = this.getManagmentServer() ; 
		result = ms.getGetHttpBinResponse(apiPath ) ;
		Files.copy(result.getBody(), Paths.get(folderDest + this.getName()+".zip") , java.nio.file.StandardCopyOption.REPLACE_EXISTING );
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
			List<Revision> revisions = env.getRevision() ;  
			for (Revision revision  : revisions )
			{
				deployedRevision.add(revision.getName()); 
			}
			result.put(envName, deployedRevision); 
		}
		
		return result ; 
		
	}
	
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
		result.setParentProxy(this);
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
	
	public ExportResults  exportAllDeployedRevisions(String folderDest ) throws NumberFormatException, UnirestException, IOException
	{
		ExportResults exportResults = new  ExportResults();   
		HashMap<String, ArrayList<String>> deployedRevisions = this.getDeployedRevisions() ; 
		for ( String  DeployedEnvName :  deployedRevisions.keySet()) 
		{
			ArrayList<String > envRevisions = deployedRevisions.get(DeployedEnvName) ;
			ExportResult er ; 
			for (String revisionString : envRevisions  )
			{
				int revision = Integer.parseInt(revisionString);
				try {
					String path = folderDest+ File.separatorChar + DeployedEnvName + File.separatorChar + this.getName()+ File.separatorChar + revision+ File.separatorChar ; 
					Path pathObj = Paths.get(path);
			        Files.createDirectories(pathObj);
					export(revision , path) ;
					
					er = new ExportResult() ;
					er.setDestination(path) ;
					er.setSource(DeployedEnvName + "." +this.getName()+ "." + revision);
					er.setFailed(false);
					
					exportResults.add(er) ; 
					System.out.println(this.getClass().getName() +" : " + this.getName() + " Revision " +  revision + " Deplyed to Env " + DeployedEnvName +" Exported Successfully");
				}
				catch (Exception e) {
					er = new ExportResult() ;
					er.setFailed(true);
					er.setExceptionClassName(e.getClass().getName());
					er.setError(e.getMessage());
					exportResults.add(er) ; 
					logger.error("Failed to Export" + this.getClass().getName() +", Name : " + this.getName() +" revision # " +revision + " Due To : "+ e.getMessage()); 
				}
			}
				
		}
		
		return exportResults ; 
	}
	


}
