package com.smartvalue.apigee.rest.schema;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.AppConfig;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.migration.ProcessResults;
import com.smartvalue.apigee.migration.deploy.DeployResults;
import com.smartvalue.apigee.migration.export.ExportResult;
import com.smartvalue.apigee.migration.export.ExportResults;
import com.smartvalue.apigee.migration.load.LoadResult;
import com.smartvalue.apigee.migration.load.LoadResults;
import com.smartvalue.apigee.migration.load.LoadResults;
import com.smartvalue.apigee.migration.transformers.ApigeeObjectTransformer;
import com.smartvalue.apigee.migration.transformers.IApigeeObjectTransformer;
import com.smartvalue.apigee.migration.transformers.TransformResult;
import com.smartvalue.apigee.migration.transformers.TransformationResults;
import com.smartvalue.apigee.migration.transformers.proxy.ProxyTransformer;
import com.smartvalue.apigee.resourceManager.helpers.Helper;
import com.smartvalue.apigee.rest.schema.organization.Organization;
import com.smartvalue.apigee.rest.schema.proxy.DeleteResults;
import com.smartvalue.apigee.rest.schema.proxy.ProxyServices;

public abstract class ApigeeService {

	public static final String TransformedFoldername = "Transformed";
	protected static final Logger logger = LogManager.getLogger(ApigeeService.class);
	private ManagementServer ms ;
	//protected String orgName ; 
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
	}
	
	public  ApigeeService(ManagementServer ms , String m_orgName, String m_envName) {
		this.ms = ms;
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
			organization = this.getMs().getCurrentOrg(); 
		}
		return organization;
	}
	
	
	public abstract DeleteResults deleteAll() throws UnirestException, IOException, Exception ; 
	public abstract ArrayList<ApigeeObjectTransformer> buildTransformers() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, FileNotFoundException, IOException  ; 

	public abstract String  getResourcePath()  ;
	public  TransformationResults  transformAll(String inputFolderPath , String outputFolderPath) throws Exception 
	{
		// Default Simple Implementation 
		File folder = new File(inputFolderPath);
		ArrayList<ApigeeObjectTransformer>  transformers = this.buildTransformers();
		TransformationResults transformResults  = new TransformationResults();
		
		for (File apigeeObjectFile : folder.listFiles() )
		{
			String objectFileName = apigeeObjectFile.getName();
			
			System.out.println("================Tranforming "+this.getApigeeObjectType()+" ==============");
			int transformerCount = 1 ; 
			String tempTramsformedFilePath = outputFolderPath + File.separatorChar +"temp"+ File.separatorChar + "tranformer_"+transformerCount ; 
			Path sourcePath ; 
			Path destPath ; 
			if (transformers.size() > 0 )
			{
				for (IApigeeObjectTransformer trasnformer : transformers)
				{
					if ((trasnformer instanceof ProxyTransformer ) ) continue; 
					
					boolean transform = trasnformer.filter(apigeeObjectFile.getAbsolutePath()) ;
					if (transform)
					{	String filePath = apigeeObjectFile.getAbsolutePath() ; 
						TransformResult  tr = trasnformer.trasform( filePath , tempTramsformedFilePath);
						if (tr.isFailed())	
						{transformResults.add(tr);}
						System.out.println("=======Object  "+ filePath + " Is Tranformed To : "+outputFolderPath+" ==========") ;
						// in the next loop transform the transformed file
						if (transformerCount < transformers.size())
						{
							filePath = tempTramsformedFilePath + File.separatorChar + objectFileName ;
							transformerCount++;
							tempTramsformedFilePath = outputFolderPath + File.separatorChar +"temp"+ File.separatorChar + "tranformer_"+transformerCount ;
						}
						else // Last Transformer 
						{
							//-- Copy Last Transformed file to the outputFolderPath 
							sourcePath = Path.of(tempTramsformedFilePath + File.separatorChar + objectFileName );
							destPath = Path.of(outputFolderPath + File.separatorChar + objectFileName );
							Files.copy(sourcePath, destPath , StandardCopyOption.REPLACE_EXISTING);
						}
					}
				}
			}
			else
			{
				// if No TRansformers found, simply copy the file to destination 
				sourcePath =  Path.of(inputFolderPath + File.separatorChar + objectFileName);
				destPath =  Path.of(outputFolderPath + File.separatorChar + objectFileName );
				Files.copy(sourcePath, destPath , StandardCopyOption.REPLACE_EXISTING);
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
	

	
	public LoadResults  importAll(String sourceFolder) throws UnirestException, IOException, Exception
	{
		 
		LoadResults allResults = new LoadResults() ; 
		File source = new File(sourceFolder); 
		LoadResult lr  ; 
		for (File resourceFile : source.listFiles() )
		{
			lr = new LoadResult();
			lr.setSource(resourceFile.getAbsolutePath());
			HttpResponse<String> productImportresult = null ; 
			try { productImportresult =  importResource(resourceFile) ; 
				lr.setFailed(false);
			}
			catch (Exception e) {
				lr.setFailed(true); 
				lr.setExceptionClassName(e.getClass().getName());
				lr.setError(e.getMessage());
			}
			lr.setHttpResponse(productImportresult);
			allResults.add( lr);
		}
		
		return allResults ; 
	}
	
	
	protected HttpResponse<String>  importResource(File resourceFile) throws Exception  {
		Path path = Paths.get(resourceFile.getAbsolutePath()); 
		String body = new String(Files.readAllBytes(path));
		String apiPath = this.getResourcePath() ; 
		HttpResponse<String> result = this.getMs().getPostHttpResponse(apiPath, body, "application/json");
		
		return result ; 
		
	}
	
	public ExportResult  exportResource(String resourceId , String destFolder) throws Exception 
	{
		ExportResult er  = new ExportResult();
		HttpResponse<String> response = null ; 
		try {
			Path path = Paths.get(destFolder);
	        Files.createDirectories(path);
	        response = this.getMs().getGetHttpResponse(getResourcePath()+"/" + resourceId) ; 
	 		String responseBody = response.getBody() ;
			try(  FileWriter myWriter = new FileWriter(destFolder +File.separatorChar + resourceId+".json" ) )
			{
				myWriter.write(responseBody);
				myWriter.close();
			}
			boolean considerSuccess = Helper.isConsideredSuccess(response.getStatus());  
			er.setFailed(! considerSuccess);
			er.setSource(this.getApigeeObjectType()+" : "  + resourceId);
			er.setHttpResponse(response); 
			
		} catch (Exception e) {
			er.setFailed(true);
			er.setExceptionClassName(e.getClass().getName());
			er.setError(e.getMessage());
			er.setHttpResponse(response);

		}
		return er ; 
		
	}

	public ExportResults  exportAll(String destFolder) throws Exception
	{
		// Organizational Based Objects ( Products , developers , apps ) export 
		ExportResults exportResults = new ExportResults();
		
		for (String resourceId : getAllResources() )
		{
			System.out.println("Exporting "+this.getApigeeObjectType()+" : "  + resourceId );
			exportResults.add(exportResource(resourceId , destFolder) ); 
		}
		
		return exportResults ; 
		
	}
	
	public abstract String getApigeeObjectType() ; 
	public String getMigationSubFoler() 
	{
		String result ; 
		if ( this instanceof ProxyServices) result = "proxies" ; 
		else result = this.getApigeeObjectType() ; 
		return result ; 
		
	}
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
	
	public abstract ProcessResults  performETL( String objectId , String processId ) throws Exception ; 
	
	
	
	
	
}
