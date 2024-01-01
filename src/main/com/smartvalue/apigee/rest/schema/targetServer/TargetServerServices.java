package com.smartvalue.apigee.rest.schema.targetServer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.rest.schema.Service;

public class TargetServerServices  extends Service {

	private String resourcePath = "/v1/organizations/"+orgName+"/environments/"+envName+"/targetservers/"; 
	@Override
	public String getResourcePath() {
		return resourcePath ; 
	}
	
	protected void setEnvName(String m_envName)
	{
		this.envName = m_envName ; 
		this.resourcePath = "/v1/organizations/"+orgName+"/environments/"+m_envName+"/targetservers/";
	}

	public TargetServerServices(ManagementServer ms, String m_orgName) {
		super(ms, m_orgName);
	}
	

	@Override
	public ArrayList<HttpResponse<String>> deleteAll() throws UnirestException, IOException {
		return null;
	}

	public HashMap<String , HashMap<String , Exception>> exportAll(String destFolder) throws UnirestException, IOException
	{
		HashMap<String , HashMap<String , Exception>> failedResult = new HashMap<String , HashMap<String , Exception>>();
		for ( String envName : this.getOrganization().getEnvironments())
		{
			this.setEnvName(envName);
			HashMap<String , Exception> xx = new HashMap<String , Exception>() ;
			for (String targetServerName : this.getAllResources())
			{
				try {
					exportResource(targetServerName , destFolder +File.separatorChar+ envName ) ;
					System.out.println("TargetServer  : " + envName+"/"+targetServerName + " Exported. ");
				}
				catch(Exception e ) {
					xx.put(targetServerName, e) ; 
				}
			}
			if ( xx.size()>0 ) failedResult.put(envName, xx) ; 
			
		}
		return failedResult;
	}
	
	public  ArrayList<HttpResponse<String>> importAll(String folderPath) throws UnirestException, IOException 
	{
		ArrayList<HttpResponse<String>> failedResult = new ArrayList<HttpResponse<String>>();  
		String envName ;
		File folder = new File(folderPath); 
		
		for (File envFolder : folder.listFiles() )
		{
			int envTargetCount = 0 ; 
			envName = envFolder.getName();
			this.setEnvName(envName);
			System.out.println("================Importing TargetServers Of Environment  " + envName +"==============");
			for (File targetServerFile : envFolder.listFiles() )
			{
				envTargetCount++; 
				int dotIndex = targetServerFile.getName().indexOf(".");
				String targetServerName= targetServerFile.getName().substring(0, dotIndex ) ; 
				System.out.println( targetServerName + ":" +targetServerFile.getAbsolutePath()  );
				HttpResponse<String> result = importResource(targetServerFile);
				int status = result.getStatus() ; 
				if (! (status == 200 || status == 201) )
				{	
					System.out.println("Error Uploading TargetServer " + targetServerName);
					System.out.println("Error Details " + result.getBody());
					failedResult.add(result) ; 
				}
			}
			System.out.println("==== End of Importing KVM's for Environment " + envName +"==("+envTargetCount+") KVM's =====\n\n\n");
			
		}
		return failedResult ; 
	}

	@Override
	public String getApigeeObjectType() {
		return "TargetServr";
	}

}
