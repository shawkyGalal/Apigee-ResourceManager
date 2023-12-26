package com.smartvalue.apigee.rest.schema.keyValueMap;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.context.annotation.ImportResource;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.rest.schema.proxyUploadResponse.ProxyUploadResponse;

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
		return resourcePath ; 
	}

	protected void setEnvName(String m_envName)
	{
		this.envName = m_envName ; 
		this.resourcePath = "/v1/organizations/"+orgName+"/environments/"+m_envName+"/keyvaluemaps/";
	}
	
	public HashMap<String , HashMap<String , Exception>> exportAll(String destFolder) throws UnirestException, IOException
	{
		HashMap<String , HashMap<String , Exception>> failedResult = new HashMap<String , HashMap<String , Exception>>();
		for ( String envName : this.getOrganization().getEnvironments())
		{
			this.setEnvName(envName);
			HashMap<String , Exception> xx = new HashMap<String , Exception>() ;
			for (String kvmName : this.getAllResources())
			{
				try {
					exportResource(kvmName , destFolder +File.separatorChar+ envName ) ;
					System.out.println("KVM  : " + kvmName + " Exported. ");
				}
				catch(Exception e ) {
					xx.put(kvmName, e) ; 
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
			int envKvmsCount = 0 ; 
			envName = envFolder.getName(); 
			System.out.println("================Importing KVM's Of Environment  " + envName +"==============");
			for (File kvmFolder : envFolder.listFiles() )
			{
				envKvmsCount++; 
					for (File kvmfile : kvmFolder.listFiles())
					{
						int dotIndex = kvmfile.getName().indexOf(".");
						
						String kvmName= kvmfile.getName().substring(0, dotIndex ) ; 
						System.out.println( kvmName + ":" +kvmfile.getAbsolutePath()  );
						HttpResponse<String> result = importResource(kvmfile);
						int status = result.getStatus() ; 
						if (! (status == 200 || status == 201) )
						{	
							System.out.println("Error Uploading KVM " + kvmName);
							System.out.println("Error Details " + result.getBody());
							failedResult.add(result) ; 
						}
						
					}
				
			}
			System.out.println("==== End of Importing KVM's for Environment " + envName +"==("+envKvmsCount+") KVM's =====\n\n\n");
			
		}
		return failedResult ; 
	}
}
