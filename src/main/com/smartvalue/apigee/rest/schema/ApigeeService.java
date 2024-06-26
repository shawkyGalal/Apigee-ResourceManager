package com.smartvalue.apigee.rest.schema;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.rest.schema.organization.Organization;
import com.smartvalue.apigee.rest.schema.proxy.transformers.TransformResult;

public abstract class ApigeeService {

	protected static final Logger logger = LogManager.getLogger(ApigeeService.class);
	private ManagementServer ms ;
	protected String orgName ; 
	protected String envName ; 
	private PrintStream printStream = System.out; 
	private Organization organization ; 
	protected String resourcePath ; 
	
	/*
	ArrayList<ApigeeObjectTransformer> transformers = new ArrayList<ApigeeObjectTransformer>();
	
	public ArrayList<ApigeeObjectTransformer> getTransformers() {

		if (transformers == null || transformers.size() == 0 )
		{ 	
			transformers = new ArrayList<ApigeeObjectTransformer> () ; 
			//Add a dummy NullTransformer to just copy file from source to Destination 
			transformers.add( new NullTransformer()) ;
		}

		return transformers;
	}

	public void setTranformers(ArrayList<ApigeeObjectTransformer> bundleUploadTranformers) {
		this.transformers = bundleUploadTranformers;
	}
	
	*/
	public ManagementServer getMs() {
		return ms;
	}
	
	public void setMs(ManagementServer ms) {
		this.ms = ms;
	}
	
	//----3 Types of constructors -------
	public  ApigeeService(ManagementServer ms ) {
		this.ms = ms ;
	}
	
	public  ApigeeService(ManagementServer ms , String m_orgName) {
		this.ms = ms ;
		this.orgName = m_orgName ;
	}
	
	public  ApigeeService(ManagementServer ms , String m_orgName, String m_envName) {
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
	
	public ApigeeService withPrintStream(PrintStream printStream) {
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
	
	
	public abstract ArrayList<HttpResponse<String>> deleteAll() throws UnirestException, IOException, Exception ; 
	
	public abstract String  getResourcePath()  ;
	public  ArrayList<TransformResult>  transformAll(String inputFolderPath , String outputFolderPath) throws Exception 
	{
		// Default Simple Implementation 
		File folder = new File(inputFolderPath);
		ArrayList<ApigeeObjectTransformer>  transformers = this.getMs().getInfra().buildTransformers();
		ArrayList<TransformResult> transformResults  = new ArrayList<TransformResult> ();
		
		for (File apigeeObjectFile : folder.listFiles() )
		{
			envName = apigeeObjectFile.getName();
			System.out.println("================Tranforming "+this.getApigeeObjectType()+" ==============");
			for (ApigeeObjectTransformer trasnformer : transformers)
			{
				boolean transform = trasnformer.filter(apigeeObjectFile.getAbsolutePath()) ;
				if (transform)
				{	String filePath = apigeeObjectFile.getAbsolutePath() ; 
					TransformResult  tr = trasnformer.trasform( filePath , outputFolderPath);
					if (tr.isFailed())	
					{transformResults.add(tr);}
					System.out.println("=======Object  "+ filePath + " Is Tranformed To : "+outputFolderPath+" ==========") ;
				}
			}
		}
		
		return transformResults ; 
	}
	
	
	protected <T> T getAllResources(Class<T> classOfT ) throws Exception
	{
		T allResourcesResponse = this.getMs().executeGetMgmntAPI(getResourcePath() , classOfT) ;
		return allResourcesResponse ; 
	}
	
	protected ArrayList<String> getAllResources() throws Exception 
	{
		ArrayList<String> allResourcesResponse = this.getMs().executeGetMgmntAPI(getResourcePath() , ArrayList.class) ;
		return allResourcesResponse ; 
	}
	
	protected <T> T getResource(String resourceId , Class<T> classOfT ) throws Exception
	{
		T resource = this.getMs().executeGetMgmntAPI(getResourcePath()+ "/"+ resourceId, classOfT) ;
		return resource ; 
	}
	
	public void  exportResource(String resourceId , String destFolder) throws Exception 
	{
		Path path = Paths.get(destFolder);
        Files.createDirectories(path);
 		String responseBody = this.getMs().getGetHttpResponse(getResourcePath()+"/" + resourceId).getBody() ;
		try(  FileWriter myWriter = new FileWriter(destFolder +File.separatorChar + resourceId+".json" ) )
		{
			myWriter.write(responseBody);
			myWriter.close();
		}
	}
	
	public ArrayList<HttpResponse<String>> importAll(String sourceFolder) throws UnirestException, IOException, Exception
	{
		ArrayList<HttpResponse<String>> result = new ArrayList<HttpResponse<String>> () ; 
		ArrayList<HttpResponse<String>> failedResult ; 
		File source = new File(sourceFolder); 
		for (File resourceFile : source.listFiles() )
		{
			failedResult = new ArrayList<HttpResponse<String>>(); 
			System.out.println("Importing "+this.getApigeeObjectType()+" : "  + resourceFile );
			try {
				HttpResponse<String> productImportresult =  importResource(resourceFile) ;
				int status = productImportresult.getStatus() ; 
				if (! (status == 200 || status == 201) )
				{	int dotIndex = resourceFile.getName().indexOf(".");
					String objectName= resourceFile.getName().substring(0, dotIndex ) ; 
					System.out.println("Error Uploading " + this.getResourcePath() +": " + objectName);
					System.out.println("Error Details " + productImportresult.getBody());
					failedResult.add(productImportresult) ; 
					logger.error("Error Importing (" + this.getApigeeObjectType() +") Name : " + objectName +", Response_Body : "+ productImportresult.getBody());
				}
				
				result.add(productImportresult) ;
			}
			catch (Exception e ) {
				
			}
		}
		
		return result ; 
	}
	
	
	protected HttpResponse<String>  importResource(File resourceFile) throws Exception  {
		Path path = Paths.get(resourceFile.getAbsolutePath()); 
		String body = new String(Files.readAllBytes(path));
		String apiPath = this.getResourcePath() ; 
		HttpResponse<String> result = this.getMs().getPostHttpResponse(apiPath, body, "application/json");
		
		return result ; 
		
	}

	public HashMap<String , HashMap<String , Exception>>  exportAll(String destFolder) throws Exception
	{
		// Organizational Based Objects ( Products , developers , apps ) export 
		HashMap<String , HashMap<String , Exception>> failedResult = new HashMap<String , HashMap<String , Exception>>();
		for (String resourceId : getAllResources() )
		{
			System.out.println("Exporting "+this.getApigeeObjectType()+" : "  + resourceId );
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
	
	public abstract String getApigeeObjectType() ; 
	protected  <T> ArrayList<T>  getAllResourcesList( Class<T> classOfT ) throws Exception
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
