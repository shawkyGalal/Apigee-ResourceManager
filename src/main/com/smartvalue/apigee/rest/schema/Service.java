package com.smartvalue.apigee.rest.schema;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.rest.schema.application.Application;
import com.smartvalue.apigee.rest.schema.organization.Organization;

public abstract class Service {

	private ManagementServer ms ;
	protected String orgName ; 
	protected String envName ; 
	private PrintStream printStream = System.out; 
	private Organization organization ; 
	
	public ManagementServer getMs() {
		return ms;
	}
	
	public void setMs(ManagementServer ms) {
		this.ms = ms;
	}
	
	//----3 Types of constructors -------
	public  Service(ManagementServer ms ) {
		this.ms = ms ;
	}
	
	public  Service(ManagementServer ms , String m_orgName) {
		this.ms = ms ;
		this.orgName = m_orgName ;
	}
	
	public  Service(ManagementServer ms , String m_orgName, String m_envName) {
		this.ms = ms;
		this.orgName = m_orgName ;
		this.envName = m_envName ; 
	}

	public PrintStream getPrintStream() {
		return printStream;
	}

	public void setPrintStream(PrintStream printStream) {
		this.printStream = printStream;
	}
	
	public Service withPrintStream(PrintStream printStream) {
		this.printStream = printStream;
		return this ; 
	}

	
	public Organization getOrganization() throws UnirestException, IOException {
		if (organization == null)
		{
			organization = this.getMs().getOrgByName(this.orgName); 
		}
		return organization;
	}
	
	
	public abstract ArrayList<HttpResponse<String>> deleteAll() throws UnirestException, IOException ; 
	
	public abstract String  getResourcePath() ;
	
	
	public <T> T getAllResources(Class<T> classOfT ) throws UnirestException, IOException
	{
		T allResourcesResponse = this.getMs().executeGetMgmntAPI(getResourcePath() , classOfT) ;
		return allResourcesResponse ; 
	}
	
	public ArrayList<String> getAllResources() throws UnirestException, IOException 
	{
		ArrayList<String> allResourcesResponse = this.getMs().executeGetMgmntAPI(getResourcePath() , ArrayList.class) ;
		return allResourcesResponse ; 
	}
	
	public <T> T getResource(String resourceId , Class<T> classOfT ) throws UnirestException, IOException
	{
		T resource = this.getMs().executeGetMgmntAPI(getResourcePath()+ "/"+ resourceId, classOfT) ;
		return resource ; 
	}
	
	public void  exportResource(String resourceId , String destFolder) throws UnirestException, IOException 
	{
		Path path = Paths.get(destFolder);
        Files.createDirectories(path);
        //System.out.println("Directory "+destFolder+" created successfully!");
            
		//File f = new File(destFolder); 
		//if (!f.exists())
		//{
		//	f.mkdir(); 
		//}
		String responseBody = this.getMs().getGetHttpResponse(getResourcePath()+"/" + resourceId).getBody() ;
		try(  FileWriter myWriter = new FileWriter(destFolder +File.separatorChar + resourceId+".json" ) )
		{
			myWriter.write(responseBody);
			myWriter.close();
		}
	}
	
	public ArrayList<HttpResponse<String>> importAll(String sourceFolder)
	{
		ArrayList<HttpResponse<String>> result = new ArrayList<HttpResponse<String>> () ; 
		File source = new File(sourceFolder); 
		for (File resourceFile : source.listFiles() )
		{
			try {
				result.add(importResource(resourceFile)) ;
			}
			catch (Exception e ) {
				
			}
		}
		
		return result ; 
	}
	
	
	private HttpResponse<String>  importResource(File resourceFile) throws IOException, UnirestException  {
		Path path = Paths.get(resourceFile.getAbsolutePath()); 
		String body = new String(Files.readAllBytes(path));
		String apiPath = this.getResourcePath() ; 
		HttpResponse<String> result = this.getMs().getPostHttpResponse(apiPath, body, "application/json");
		
		return result ; 
		
	}

	public HashMap<String , HashMap<String , Exception>>  exportAll(String destFolder) throws UnirestException, IOException
	{
		HashMap<String , HashMap<String , Exception>> failedResult = new HashMap<String , HashMap<String , Exception>>();
		for (String resourceId : getAllResources() )
		{
			try {
			exportResource(resourceId , destFolder) ;
			}
			catch (Exception e) {
				HashMap<String, Exception> xx = new HashMap<String , Exception>() ;
				xx.put(resourceId, e); 
				failedResult.put(this.envName, xx); 
			}
		}
		
		return failedResult ; 
		
	}
	
	
	public <T> ArrayList<T>  getAllResourcesList( Class<T> classOfT ) throws UnirestException, IOException
	{
		ArrayList<String> allResourcesNames = getAllResources() ; 
		ArrayList<T> allResources = new ArrayList<T>() ; 
		for (String resourceNsame : allResourcesNames)
		{
			allResources.add (getResource(resourceNsame , classOfT)) ; 
		}
		return allResources ; 

	}
	
	
	
	
}
