package com.smartvalue.apigee.rest.schema;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.migration.load.LoadResult;
import com.smartvalue.apigee.migration.load.LoadResults;
import com.smartvalue.apigee.migration.transformers.ApigeeObjectTransformer;
import com.smartvalue.apigee.migration.transformers.TransformResult;

public abstract class EnvironmentScopeService extends ApigeeService {


	@Override
	public String getResourcePath() {
		//if (envName == null)
		//{
		//	throw new Exception("Environment Name is null, this is Environment Scoped Service ") ; 
		//}
		return resourcePath ; 
	}
	
	public EnvironmentScopeService(ManagementServer ms, String m_orgName) {
		super(ms, m_orgName);
	}

	public void setEnvName(String m_envName)
	{
		this.envName = m_envName ; 
		this.resourcePath = "/v1/organizations/"+orgName+"/environments/"+m_envName+"/"+ this.getApigeeObjectType();
	}
	
	/**
	 * Environmental Based Objects export ( targetServers , KVMs ) export 
	 */
	public HashMap<String , HashMap<String , Exception>> exportAll(String destFolder) throws Exception
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
					System.out.println(this.getApigeeObjectType() + " : " + kvmName + " Exported. ");
				}
				catch(Exception e ) {
					xx.put(kvmName, e) ; 
				}
			}
			if ( xx.size()>0 ) failedResult.put(envName, xx) ; 
			
		}
		return failedResult;
	}
	
	/**
	 * Environmental Based Objects transform ( targetServers , KVMs ) export 
	 * @throws NoSuchFieldException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	public ArrayList<TransformResult>  transformAll(String inputFolderPath , String outputFolderPath) throws Exception
	{
		String envName ;
		File folder = new File(inputFolderPath);
		ArrayList<ApigeeObjectTransformer>  transformers = this.buildTransformers();
		ArrayList<TransformResult> transformResults  = new ArrayList<TransformResult> ();
		
		for (File envFolder : folder.listFiles() )
		{
			int envObjectCount = 0 ; 
			envName = envFolder.getName();
			System.out.println("================Tranforming KVMs Deplyed TO Environment  " + envName + " ==============");
			envObjectCount++; 
				for (File apigeeObjectFile : envFolder.listFiles())
					{
						String newBundleFolderPath = outputFolderPath+ File.separatorChar + envName + File.separatorChar   ;
						String pundleZipFileName = apigeeObjectFile.getAbsolutePath() ; 
						
						for (ApigeeObjectTransformer trasnformer : transformers)
						{
							boolean transform = trasnformer.filter(pundleZipFileName) ;
							if (transform)
							{	 
								transformResults.add(trasnformer.trasform(pundleZipFileName , newBundleFolderPath));
								System.out.println("======="+this.getApigeeObjectType() + ": " +apigeeObjectFile + " Is Tranformed To : "+newBundleFolderPath+" ==========") ;
							}
						}

					}
			
			System.out.println("==== End of Tranforming Proxies Deplyed to Environment " + envName +"==("+envObjectCount+") Proxies =====\n\n\n");
			
		}
		return transformResults ; 

	}
	
	/**
	 * Environmental Based Objects Import ( targetServers , KVMs ) export 
	 */
	public  LoadResults importAll(String folderPath) throws Exception 
	{
		LoadResults allResult = new LoadResults();  
		String envName ;
		File folder = new File(folderPath); 
		
		for (File envFolder : folder.listFiles() )
		{
			int envObjectCount = 0 ; 
			envName = envFolder.getName(); 
			this.setEnvName(envName);
			System.out.println("================Importing "+this.getApigeeObjectType()+" Of Environment  " + envName +"==============");
			for (File objectFile : envFolder.listFiles())
			{
				envObjectCount++; 
				int dotIndex = objectFile.getName().indexOf(".");
					
				String ObjectName= objectFile.getName().substring(0, dotIndex ) ; 
				LoadResult lr = new LoadResult();
				lr.setSource(objectFile.getAbsolutePath()) ; 
				System.out.println( ObjectName + ":" +objectFile.getAbsolutePath()  );
				try {
				HttpResponse<String> result = importResource(objectFile);
				}
				catch (Exception e) {
					lr.setFailed(true); 
					lr.setExceptionClassName(e.getClass().getName());
					lr.setError(e.getMessage());
				}				
				
				allResult.add(lr) ;
			}
				
			System.out.println("==== End of Importing KVM's for Environment " + envName +"==("+envObjectCount+") KVM's =====\n\n\n");
			
		}
		return allResult ; 
	}
	
	@Override
	public ArrayList<HttpResponse<String>> deleteAll() throws UnirestException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
