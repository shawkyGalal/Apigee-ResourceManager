package com.smartvalue.apigee.rest.schema;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.migration.transformers.ApigeeObjectTransformer;
import com.smartvalue.apigee.migration.transformers.TransformResult;
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
	
	public ArrayList<TransformResult>  transformAll(String inputFolderPath , String outputFolderPath) throws Exception
	{
		
		ArrayList<TransformResult> transformResults  = new ArrayList<TransformResult> (); 
		String envName ;
		File folder = new File(inputFolderPath);
		ArrayList<ApigeeObjectTransformer>  transformers = this.getMs().getInfra().buildTransformers(ProxyTransformer.class) ;  
	
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
					for (File pundleZipFile : revisionFolder.listFiles())
					{
						String zipFileName= pundleZipFile.getName(); 
						String proxyName = zipFileName.substring(0, zipFileName.indexOf(".")); 
						String newBundleFolderPath = outputFolderPath+ File.separatorChar + envName + File.separatorChar + proxyName + File.separatorChar + revision +File.separatorChar ;
						String pundleZipFileName = pundleZipFile.getAbsolutePath() ; 
						int transformerCount = 1 ; 
						String tempTramsformedFilePath = newBundleFolderPath + File.separatorChar +"temp"+ File.separatorChar + "tranformer_"+transformerCount ; 
						Path sourcePath ; 
						Path destPath ; 
						if (transformers.size() > 0 )
						{
							for (ApigeeObjectTransformer trasnformer : transformers)
							{
								System.out.println("\t\tTransformer : " + trasnformer.getClass()) ; 
								boolean transform = trasnformer.filter(pundleZipFileName) ;
								if (transform)
								{	
									 
									TransformResult  tr = trasnformer.trasform( pundleZipFileName , tempTramsformedFilePath);
									if (tr.isFailed())	
									{transformResults.add(tr);}
								
									System.out.println("=======Proxy "+ pundleZipFile + " Is Tranformed To : "+tempTramsformedFilePath+" ==========") ;
									// in the next loop transform the transformed file
									if (transformerCount != transformers.size())
									{
										pundleZipFileName = tempTramsformedFilePath + File.separatorChar + proxyName + ".zip" ;
										transformerCount++;
										tempTramsformedFilePath = newBundleFolderPath + File.separatorChar +"temp"+ File.separatorChar + "tranformer_"+transformerCount ;
									}
								}
								 
							}
							//-- Copy Last Transformed file to the outputFolderPath 
							sourcePath = Path.of(tempTramsformedFilePath + File.separatorChar + proxyName + ".zip");
							destPath = Path.of(newBundleFolderPath + File.separatorChar + proxyName + ".zip");
						}
						else
						{
							 sourcePath =  Path.of(pundleZipFileName);
							 destPath =  Path.of(newBundleFolderPath + File.separatorChar + proxyName + ".zip");
							 Files.createDirectories(destPath.getParent());
						}

						Files.copy(sourcePath, destPath , StandardCopyOption.REPLACE_EXISTING);
					}
				}
			}

			System.out.println("==== End of Tranforming Proxies Deplyed to Environment " + envName +"==("+envProxiesCount+") Proxies =====\n\n\n");
			
		}
		return transformResults ; 

	}
	
	public  ArrayList<HttpResponse<String>> importAll(String folderPath) throws UnirestException, IOException 
	{
		ArrayList<HttpResponse<String>> failedResult = new ArrayList<HttpResponse<String>>();  
		String envName ;
		File folder = new File(folderPath); 
		
		for (File envFolder : folder.listFiles() )
		{
			failedResult = new ArrayList<HttpResponse<String>>();  
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
						/*
						if ( this.getProxyFilter() != null && !this.getProxyFilter().filter(zipfile))
						{
							System.out.println("=======Proxy "+ zipfile + " Is Scaped ==========") ; 
							break;
						}
						*/
						String objectName= zipfile.getName().substring(0, dotIndex ) ; 
						System.out.println( objectName + ":" +zipfile.getAbsolutePath()  );
						HttpResponse<String> result = importObject(zipfile.getAbsolutePath() , objectName);
						int status = result.getStatus() ; 
						if (! (status == 200 || status == 201) )
						{	
							System.out.println("Error Uploading Bundle " + objectName);
							System.out.println("Error Details " + result.getBody());
							failedResult.add(result) ; 
							logger.error("Error Importing (" + this.getApigeeObjectType() +") Name : " + objectName +", Response_Body : "+ result.getBody());
						}
						if (this.isDeployUponUpload())
						{
							Gson json = new Gson(); 
							ProxyUploadResponse pur = json.fromJson(result.getBody(), ProxyUploadResponse.class); 
							//--- Started Deploying the proxy revision to environment 
							int newRevesion = pur.getConfigurationVersion().getMajorVersion();
							HttpResponse<String> deployresult = this.deployRevision(objectName, envName , newRevesion) ;
							status = deployresult.getStatus() ;
							if (status != 200)
							{	
								System.out.println("Error Deplying Proxy " + objectName);
								System.out.println("Error Details " + deployresult.getBody());
								failedResult.add(deployresult) ;
								logger.error("Error Deploying (" + this.getApigeeObjectType() +") Name : " + objectName +"Response Body : "+ deployresult.getBody());
							}
						}
					}
			
				}
				
			}
			System.out.println("==== End of Importing Proxies Deplyed to Environment " + envName +"==("+envProxiesCount+") Proxies =====\n\n\n");
			
			if (failedResult.size() > 0 )
			{
				System.out.println(" Import Errors " + failedResult.size() + ", For More Details check log file /logs/system.log" );
			}
		}
		
		//System.out.println("Errors:  \n" + failedResult.toString()); 
		return failedResult;
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
