package com.smartvalue.apigee.rest.schema.keyValueMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.infra.ManagementServer;

public class KvmServices extends com.smartvalue.apigee.rest.schema.Service {
	
	

	public KvmServices(ManagementServer ms, String m_orgName ) {
		super(ms, m_orgName );
	}

	public ArrayList <String>  getAllKvms() throws UnirestException, IOException
	{

		ArrayList<String> result = this.getMs().executeGetMgmntAPI(getResourcePath(), ArrayList.class) ; 
		return result;
		
	}
	
	public KeyValueMap  getKvmDetails( String m_kvmName ) throws UnirestException, IOException
	{
		String apiPath = getResourcePath() +  m_kvmName ; 
		KeyValueMap result = this.getMs().executeGetMgmntAPI(apiPath, KeyValueMap.class) ; 
		result.setEnvName(envName);
		return result;
		
	}

	
	@Override
	public ArrayList<HttpResponse<String>> deleteAll() throws UnirestException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	private String resourcePath = "/v1/organizations/"+orgName+"/environments/"+envName+"/keyvaluemaps/"; 
	
	@Override
	public String getResourcePath() {
		// TODO Auto-generated method stub
		return resourcePath ; 
	}

	public void setResourcePath(String m_envName ) {
		// TODO Auto-generated method stub
		resourcePath = "/v1/organizations/"+orgName+"/environments/"+m_envName+"/keyvaluemaps/";  ; 
	}
	
	public HashMap<String , HashMap<String , Exception>> exportAll(String destFolder) throws UnirestException, IOException
	{
		HashMap<String , HashMap<String , Exception>> failedResult = new HashMap<String , HashMap<String , Exception>>();
		for ( String envName : this.getOrganization().getEnvironments())
		{
			this.setResourcePath(envName);
			HashMap<String , Exception> xx = new HashMap<String , Exception>() ;
			for (String kvmName : this.getAllResources())
			{
				try {
					exportResource(kvmName , destFolder) ;
				}
				catch(Exception e ) {
					xx.put(kvmName, e) ; 
				}
			}
			if ( xx.size()>0 ) failedResult.put(envName, xx) ; 
			
		}
		return failedResult;
	}
}
