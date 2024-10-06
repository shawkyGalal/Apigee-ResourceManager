package com.smartvalue.apigee.rest.schema;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.migration.load.LoadResult;
import com.smartvalue.apigee.migration.transformers.ApigeeObjectTransformer;
import com.smartvalue.apigee.migration.transformers.IApigeeObjectTransformer;
import com.smartvalue.apigee.migration.transformers.TransformResult;
import com.smartvalue.apigee.migration.transformers.TransformationResults;
import com.smartvalue.apigee.migration.transformers.proxy.ProxyTransformer;
import com.smartvalue.apigee.rest.schema.proxyUploadResponse.ProxyUploadResponse;

public abstract class BundleObjectService extends ApigeeService {

	protected boolean deployUponUpload = false ; 
	
	public BundleObjectService(ManagementServer ms, String m_orgName) {
		super(ms, m_orgName);
	}

	public boolean isDeployUponUpload() {
		return deployUponUpload;
	}

	public BundleObjectService withDeployUponUpload(boolean m_deployUponUpload) {
		this.deployUponUpload = m_deployUponUpload;
		return this;
	}
	
	public void setDeployUponUpload(boolean deployUponUpload) {
		this.deployUponUpload = deployUponUpload;
	}
	
	public TransformationResults transformProxy(String pundleZipFileName, String newBundleFolderPath) throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, FileNotFoundException, IOException {
		TransformationResults transformResults  = new TransformationResults ();
		ArrayList<ApigeeObjectTransformer>  transformers = this.buildTransformers();

		File pundleZipFile = new File (pundleZipFileName) ; 
		String zipFileName = pundleZipFile.getName();

		 
		String proxyName = zipFileName.substring(0, zipFileName.indexOf(".")); 
		int transformerCount = 1 ; 
		String tempTramsformedFilePath = newBundleFolderPath + File.separatorChar +"temp"+ File.separatorChar + "tranformer_"+transformerCount ; 
		Path sourcePath ; 
		Path destPath ; 
		if (transformers.size() > 0 )
		{
			TransformResult  tr = null ; 
			for (ApigeeObjectTransformer trasnformer : transformers)
			{
				boolean transform = trasnformer.filter(pundleZipFileName) ;
				if (transform)
				{	
					tr = trasnformer.trasform( pundleZipFileName , tempTramsformedFilePath);
					if (tr.isFailed())	
					{	System.out.println("Error  : Proxy Bundle "+ pundleZipFileName +" Transformed Failed Using "+ trasnformer.getClass().getName() + " With error : " + tr.getError());
						transformResults.add(tr); break; 	}
					else 
					{ 
						System.out.println("Success : Proxy Bundle "+ pundleZipFileName +" Transformed Successully Using "+ trasnformer.getClass().getName()+" and saved to " + tempTramsformedFilePath );
					}
					transformResults.add(tr);
				
					//System.out.println("=======Proxy "+ pundleZipFile + " Is Tranformed To : "+tempTramsformedFilePath+" ==========") ;
					// in the next loop transform the transformed file
					if (transformerCount != transformers.size())
					{
						pundleZipFileName = tempTramsformedFilePath + File.separatorChar + proxyName + ".zip" ;
						transformerCount++;
						tempTramsformedFilePath = newBundleFolderPath + File.separatorChar +"temp"+ File.separatorChar + "tranformer_"+transformerCount ;
					}
				}
				 
			}
			//--Upon success transformation, Copy Last Transformed file to the outputFolderPath 
			if (tr!= null && !tr.isFailed())
			{
				sourcePath = Path.of(tempTramsformedFilePath + File.separatorChar + proxyName + ".zip");
				destPath = Path.of(newBundleFolderPath + File.separatorChar + proxyName + ".zip");
				Files.copy(sourcePath, destPath , StandardCopyOption.REPLACE_EXISTING);
			}
		}
		else
		{
			// if No TRansformers found, simply copy the file to destination 
			sourcePath =  Path.of(pundleZipFileName);
			destPath =  Path.of(newBundleFolderPath + File.separatorChar + zipFileName );
			Files.copy(sourcePath, destPath , StandardCopyOption.REPLACE_EXISTING);
		}
		
		return transformResults ; 
	}

	
	public TransformationResults  transformAll(String inputFolderPath , String outputFolderPath) throws Exception
	{
		
		TransformationResults transformResults  = new TransformationResults (); 
		String envName ;
		File folder = new File(inputFolderPath);
		File[] listFiles = folder.listFiles() ; 
		if (listFiles == null) { throw new Exception("Transformation source Folder " + folder + "  Not Found Oe Empty ") ; }
		for (File envFolder : folder.listFiles() )
		{
			int envProxiesCount = 0 ; 
			envName = envFolder.getName();
			System.out.println("================Tranforming Proxies Deplyed TO Environment  " + envName + " ==============");
			for (File proxyFolder : envFolder.listFiles() )
			{
				envProxiesCount++; 
				for (File revisionFolder : proxyFolder.listFiles() )
				{
					String revision = revisionFolder.getName();
					String zipFileName = null ;
					String proxyName ; 
					for (File pundleZipFile : revisionFolder.listFiles())
					{
						try {
						zipFileName= pundleZipFile.getName(); 
						proxyName = zipFileName.substring(0, zipFileName.indexOf("."));
						}
						catch (Exception e) {
							System.err.println("File : " + pundleZipFile + "is Not a zip File ");
							continue; 
						}
						String newBundleFolderPath = outputFolderPath+ File.separatorChar + envName + File.separatorChar + proxyName + File.separatorChar + revision +File.separatorChar ;
						transformResults.addAll(this.transformProxy(pundleZipFile.getAbsolutePath(), newBundleFolderPath)) ; 
						
						/*
						String pundleZipFileName = pundleZipFile.getAbsolutePath() ; 
						int transformerCount = 1 ; 
						String tempTramsformedFilePath = newBundleFolderPath + File.separatorChar +"temp"+ File.separatorChar + "tranformer_"+transformerCount ; 
						Path sourcePath ; 
						Path destPath ; 
						if (transformers.size() > 0 )
						{
							TransformResult  tr = null ; 
							for (ApigeeObjectTransformer trasnformer : transformers)
							{
								//System.out.println("\t\tTransformer : " + trasnformer.getClass()) ; 
								boolean transform = trasnformer.filter(pundleZipFileName) ;
								
								if (transform)
								{	
									 
									tr = trasnformer.trasform( pundleZipFileName , tempTramsformedFilePath);
									if (tr.isFailed())	
									{	
										transformResults.add(tr);
										break; 
									}
									else { System.out.println("Proxy Bundle "+ pundleZipFileName +" Transformed Successully Using "+ trasnformer.getClass().getName()+" and saved to " + tempTramsformedFilePath );}
								
									//System.out.println("=======Proxy "+ pundleZipFile + " Is Tranformed To : "+tempTramsformedFilePath+" ==========") ;
									// in the next loop transform the transformed file
									if (transformerCount != transformers.size())
									{
										pundleZipFileName = tempTramsformedFilePath + File.separatorChar + proxyName + ".zip" ;
										transformerCount++;
										tempTramsformedFilePath = newBundleFolderPath + File.separatorChar +"temp"+ File.separatorChar + "tranformer_"+transformerCount ;
									}
								}
								 
							}
							//--Upon success transformation, Copy Last Transformed file to the outputFolderPath 
							if (tr!= null && !tr.isFailed())
							{
								sourcePath = Path.of(tempTramsformedFilePath + File.separatorChar + proxyName + ".zip");
								destPath = Path.of(newBundleFolderPath + File.separatorChar + proxyName + ".zip");
								Files.copy(sourcePath, destPath , StandardCopyOption.REPLACE_EXISTING);
							}
						}
						else
						{
							 sourcePath =  Path.of(pundleZipFileName);
							 destPath =  Path.of(newBundleFolderPath + File.separatorChar + proxyName + ".zip");
							 Files.createDirectories(destPath.getParent());
							 Files.copy(sourcePath, destPath , StandardCopyOption.REPLACE_EXISTING);
						}
						*/

						
					}
				}
			}

			System.out.println("==== End of Tranforming Proxies Deplyed to Environment " + envName +"==("+envProxiesCount+") Proxies =====\n\n\n");
			
		}
		return transformResults ; 

	}
	
	public HashMap< String , HttpResponse<String>> importAll(String folderPath) throws UnirestException, IOException 
	{
		HashMap< String , HttpResponse<String> > allResults = new HashMap< String , HttpResponse<String>>(); 
		String envName ;
		File folder = new File(folderPath); 
		
		for (File envFolder : folder.listFiles() )
		{
			int envProxiesCount = 0 ; 
			envName = envFolder.getName(); 
			System.out.println("================Importing "+this.getApigeeObjectType()+" Deplyed TO Environment  " + envName +"==============");
			for (File objectFolder : envFolder.listFiles() )
			{
				envProxiesCount++; 
				for (File revisionFolder : objectFolder.listFiles() )
				{
				
					for (File zipfile : revisionFolder.listFiles())
					{
						int dotIndex = zipfile.getName().indexOf(".zip");
						if (dotIndex<0) break ; // ignore not zip files & folders 
						
						String objectName= zipfile.getName().substring(0, dotIndex ) ; 
						System.out.println( objectName + ":" +zipfile.getAbsolutePath()  );
						HttpResponse<String> uploadHttpReponse = null ; 
						try { uploadHttpReponse = importObject(zipfile.getAbsolutePath() , objectName); }
						catch (Exception e) {
						}
						
						allResults.put(zipfile.getAbsolutePath(), uploadHttpReponse ) ;
						
						if (this.isDeployUponUpload())
						{
							Gson json = new Gson(); 
							ProxyUploadResponse pur = json.fromJson(uploadHttpReponse.getBody(), ProxyUploadResponse.class); 
							//--- Started Deploying the proxy revision to environment 
							int newRevesion = pur.getConfigurationVersion().getMajorVersion();
							HttpResponse<String> deployresult = this.deployRevision(objectName, envName , newRevesion) ;
							allResults.put(zipfile.getAbsolutePath(), deployresult ) ;
						}
					}
			
				}
				
			}
			System.out.println("==== End of Importing Proxies Deplyed to Environment " + envName +"==("+envProxiesCount+") Proxies =====\n\n\n");
	
		}
		
		//System.out.println("Errors:  \n" + failedResult.toString()); 
		return allResults;
	}

	public HttpResponse<String> importObject(String pundleZipFileName , String objectName ) throws UnirestException, IOException
	{
		HttpResponse<String> result = null; 
		String apiPath = this.getResourcePath()+"?action=import&name="+objectName+"&validate=true" ; 
		ManagementServer ms = this.getMs() ;
		result = ms.getPostFileHttpResponse(apiPath , pundleZipFileName ) ;
		return result ; 
	}	
	
	
	public HttpResponse<String> deployRevision(String m_objectName , String m_envName , int revision ) throws UnirestException, IOException
	{
		HttpResponse<String> result = null; 
		String apiPath = "/v1/organizations/"+orgName+"/environments/"+m_envName+"/"+getApigeeObjectType()+"/"+m_objectName +"/revisions/"+revision+"/deployments" ; 
		ManagementServer ms = this.getMs() ; 
		result = ms.getPostHttpResponse(apiPath, null, null ) ;
		return result ; 
	}
	
	

}
