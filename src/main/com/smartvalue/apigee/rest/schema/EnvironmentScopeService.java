package com.smartvalue.apigee.rest.schema;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.AppConfig;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.migration.export.ExportResult;
import com.smartvalue.apigee.migration.export.ExportResults;
import com.smartvalue.apigee.migration.load.LoadResult;
import com.smartvalue.apigee.migration.load.LoadResults;
import com.smartvalue.apigee.migration.transformers.ApigeeObjectTransformer;
import com.smartvalue.apigee.migration.transformers.TransformResult;
import com.smartvalue.apigee.migration.transformers.TransformationResults;
import com.smartvalue.apigee.rest.schema.proxy.DeleteResults;

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
		this.resourcePath = AppConfig.BASE_BATH+this.getMs().getOrgName()+"/environments/"+m_envName+"/"+ this.getApigeeObjectType();
	}
	
	/**
	 * Environmental Based Objects export ( targetServers , KVMs ) export 
	 */
	public ExportResults exportAll(String destFolder) throws Exception
	{
		UUID uuid = UUID.randomUUID(); 
		ExportResults exportResults = new ExportResults("ExportAll " , uuid );
		for ( String envName : this.getOrganization().getEnvironments())
		{	ExportResult exportResult = new ExportResult(); 
			this.setEnvName(envName);
			for (String kvmName : this.getAllResources())
			{
				try {
					exportResource(kvmName , destFolder +File.separatorChar+ envName ) ;
					System.out.println(this.getApigeeObjectType() + " : " + kvmName + " Exported. ");
					exportResult.setFailed(false); 
				}
				catch(Exception e ) {
					exportResult.setError(e.getMessage()); 
					exportResult.setExceptionClassName(e.getClass().getName()) ; 
					exportResult.setFailed(true); 
				}
				exportResult.setSource(envName + "." + this.getApigeeObjectType() + "s . " + kvmName); 
			}
			exportResults.add(exportResult) ; 
		}
		return exportResults;
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
	public TransformationResults  transformAll(String inputFolderPath , String outputFolderPath) throws Exception
	{
		String envName ;
		File folder = new File(inputFolderPath);
		ArrayList<ApigeeObjectTransformer>  transformers = this.buildTransformers();
		UUID uuid = UUID.randomUUID(); 
		TransformationResults transformResults  = new TransformationResults ("transformAll" , uuid );
		
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
		UUID uuid = UUID.randomUUID();
		LoadResults allResult = new LoadResults("ImportAll " , uuid);  
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
	public DeleteResults deleteAll() throws UnirestException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
