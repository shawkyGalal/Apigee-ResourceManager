package com.smartvalue.apigee.rest.schema.sharedFlow;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.rest.schema.Service;
import com.smartvalue.apigee.rest.schema.organization.Organization;
import com.smartvalue.apigee.rest.schema.sharedFlow.google.auto.GoogleSharedflowList;
import com.smartvalue.apigee.rest.schema.proxy.google.auto.GoogleProxy;
import com.smartvalue.apigee.rest.schema.proxy.transformers.BundleUploadTransformer;
import com.smartvalue.apigee.rest.schema.proxyUploadResponse.ProxyUploadResponse;


public class SharedFlowServices extends Service {

	ArrayList<BundleUploadTransformer> bundleUploadTranformers = new ArrayList<BundleUploadTransformer>();
	
	public ArrayList<BundleUploadTransformer> getBundleUploadTranformers() {
		return bundleUploadTranformers;
	}

	public void setBundleUploadTranformers(ArrayList<BundleUploadTransformer> bundleUploadTranformers) {
		this.bundleUploadTranformers = bundleUploadTranformers;
	}

	public SharedFlowServices(ManagementServer ms, String m_orgName) {
		super(ms, m_orgName);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<SharedFlow>  getAllSharedFlows() throws UnirestException, IOException
	{
		ArrayList<String> proxiesName = getAllSharedFlowsNames() ; 
		ManagementServer ms = this.getMs() ;
		String apiPath = "/v1/organizations/"+orgName+"/apis" ; 
		ArrayList<SharedFlow> proxies = new ArrayList<SharedFlow>() ; 
		for (String proxyName : proxiesName)
		{
			String apiPath01 = apiPath + "/" + proxyName ; 
			@SuppressWarnings("deprecation")
			SharedFlow proxy = ms.executeGetMgmntAPI(apiPath01 , SharedFlow.class ) ;
			proxy.setOrgName(this.orgName) ; 
			proxy.setManagmentServer(ms) ; 
			proxies.add (proxy) ; 
		}
		return proxies ; 
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<String>  getAllSharedFlowsNames() throws UnirestException, IOException
	{
		ArrayList<String> proxiesName = null; 
		String apiPath = "/v1/organizations/"+orgName+"/sharedflows" ; 
		ManagementServer ms = this.getMs() ; 
		proxiesName = ms.executeGetMgmntAPI(apiPath , ArrayList.class ) ;
		return proxiesName ;  
	}
	
	public <T> T  getAllSharedFlowsList( Class<T> classOfT ) throws UnirestException, IOException
	{
		T proxiesList = null; 
		String apiPath = "/v1/organizations/"+orgName+"/apis" ; 
		ManagementServer ms = this.getMs() ; 
		proxiesList = ms.executeGetMgmntAPI(apiPath , classOfT ) ;
		return proxiesList ;  
	}
	
	
	
	public HttpResponse<String> importProxy(String pundleZipFileName ) throws UnirestException, IOException
	{
		return importShareFlow (pundleZipFileName , new File(pundleZipFileName).getName() ) ; 
	}
	
	public String transformPundle(String pundleZipFileName , String newFilePath)
	{
		String proxyAfterTransformation = pundleZipFileName ; 
		for (BundleUploadTransformer but : this.getBundleUploadTranformers())
		{
		 if(but.filter(pundleZipFileName))
			{
			 proxyAfterTransformation = but.trasform(pundleZipFileName , newFilePath);
			 pundleZipFileName = proxyAfterTransformation ;
			}
		}
		
		return proxyAfterTransformation ;
	}
	public HttpResponse<String> importShareFlow(String pundleZipFileName , String m_sharedflowName) throws UnirestException, IOException
	{
		HttpResponse<String> result = null; 
		String apiPath = "/v1/organizations/"+orgName+"/sharedflows?action=import&name="+m_sharedflowName+"&validate=true" ; 
		ManagementServer ms = this.getMs() ;
		result = ms.getPostFileHttpResponse(apiPath , pundleZipFileName ) ;
		return result ; 
	}
	
	public void  transformAllProxies(String inputFolderPath , String outputFolderPath)
	{
		String envName ;
		File folder = new File(inputFolderPath);
		ArrayList<BundleUploadTransformer>  put = this.getBundleUploadTranformers(); 
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
					for (File proxyBundlefile : revisionFolder.listFiles())
					{
						for (BundleUploadTransformer trasnformer : put)
						{
							String pundleZipFileName = proxyBundlefile.getAbsolutePath() ; 
							
							boolean transform = trasnformer.filter(pundleZipFileName) ;
							if (transform)
							{	String zipFileName= proxyBundlefile.getName();  
								String proxyName = zipFileName.substring(0, zipFileName.indexOf(".")); 
								String newBundleFolderPath = outputFolderPath+ File.separatorChar + envName + File.separatorChar + proxyName + File.separatorChar + revision +File.separatorChar ; 
								trasnformer.trasform(pundleZipFileName , newBundleFolderPath);
								System.out.println("=======Proxy "+ proxyBundlefile + " Is Tranformed To : "+newBundleFolderPath+" ==========") ;
							}
						}
					}
				}
			}
			System.out.println("==== End of Tranforming Proxies Deplyed to Environment " + envName +"==("+envProxiesCount+") Proxies =====\n\n\n");
		}


	}

	public  ArrayList<HttpResponse<String>> importAllSharedflows(String folderPath, boolean m_deploy) throws UnirestException, IOException
	{
		ArrayList<HttpResponse<String>> failedResult = new ArrayList<HttpResponse<String>>();  
		String envName ;
		File folder = new File(folderPath); 
		
		for (File envFolder : folder.listFiles() )
		{
			int envProxiesCount = 0 ; 
			envName = envFolder.getName(); 
			System.out.println("================Importing Sharedflows Deplyed TO Environment  " + envName +"==============");
			for (File sharedflowFolder : envFolder.listFiles() )
			{
				envProxiesCount++; 
				for (File revisionFolder : sharedflowFolder.listFiles() )
				{
				
					for (File zipfile : revisionFolder.listFiles())
					{
						int dotIndex = zipfile.getName().indexOf(".");
						String sharedflowName= zipfile.getName().substring(0, dotIndex ) ; 
						System.out.println( sharedflowName + ":" +zipfile.getAbsolutePath()  );
						HttpResponse<String> result = importShareFlow(zipfile.getAbsolutePath() , sharedflowName);
						int status = result.getStatus() ; 
						if (! (status == 200 || status == 201) )
						{	
							System.out.println("Error Uploading SharedFlow " + sharedflowName);
							System.out.println("Error Details " + result.getBody());
							failedResult.add(result) ; 
						}
						if (m_deploy)
						{
							Gson json = new Gson(); 
							ProxyUploadResponse pur = json.fromJson(result.getBody(), ProxyUploadResponse.class); 
							//--- Started Deploying the proxy revision to environment 
							int newRevesion = pur.getConfigurationVersion().getMajorVersion();
							HttpResponse<String> deployresult = this.deploySharedFlowRevision(sharedflowName, envName , newRevesion) ;
							status = deployresult.getStatus() ;
							if (status != 200)
							{	
								System.out.println("Error Deplying Proxy " + sharedflowName);
								System.out.println("Error Details " + deployresult.getBody());
								failedResult.add(deployresult) ; 
							}
						}
					}
			
				}
				
			}
			System.out.println("==== End of Importing SharedFlows Deplyed to Environment " + envName +"==("+envProxiesCount+") Proxies =====\n\n\n");
		}
		System.out.println("Errors:  \n" + failedResult.toString()); 
		return failedResult;
	}
	

	public HttpResponse<String> deleteSharedFlow( String m_sharedFlow) throws UnirestException, IOException
	{
		HttpResponse<String> result = null; 
		String apiPath = "/v1/organizations/"+orgName+"/sharedflows/"+m_sharedFlow ; 
		ManagementServer ms = this.getMs() ; 
		result = ms.getDeleteHttpResponse(apiPath ) ;
		return result ; 
	}
	
	public HttpResponse<String> deploySharedFlowRevision(String m_proxyName , String m_envName , int revision ) throws UnirestException, IOException
	{
		HttpResponse<String> result = null; 
		String apiPath = "/v1/organizations/"+orgName+"/environments/"+m_envName+"/sharedflows/"+m_proxyName +"/revisions/"+revision+"/deployments" ; 
		ManagementServer ms = this.getMs() ; 
		result = ms.getPostHttpResponse(apiPath, "", "" ) ;
		return result ; 
	}
	
	public  ArrayList<HttpResponse<String>> deleteAll() throws UnirestException, IOException
	{
		GoogleSharedflowList proxiesList = this.getAllSharedFlowsList(GoogleSharedflowList.class);
		return deleteAll(proxiesList); 
	}
	
	public  ArrayList<HttpResponse<String>> deleteAll(GoogleSharedflowList sharedFlowsList) throws UnirestException, IOException
	{
		ArrayList<HttpResponse<String>> failedResult = new ArrayList<HttpResponse<String>>();  
		for (com.smartvalue.apigee.rest.schema.sharedFlow.google.auto.SharedFlow sharedFlow : sharedFlowsList.getSharedFlows())
		{
			String sharedFlowName = sharedFlow.getName() ; 
			System.out.println( "SharedFlow : " + sharedFlowName + " Deleted");
			HttpResponse<String> result = deleteSharedFlow(sharedFlowName) ; 
			int status = result.getStatus() ; 
			if (status != 200)
			{
				failedResult.add(result) ; 
			}
		}
		return failedResult;
	}
	

	
	public  HashMap<String , HashMap<Integer , Exception>> exportAll(String folderDest) throws UnirestException, IOException
	{
		ArrayList<String> allSharedflows ; 
		Boolean isGoogleCloud = this.getMs().getInfra().getGooglecloud() ;
		if (isGoogleCloud != null && isGoogleCloud)
		{
			GoogleSharedflowList sharedflowList = this.getAllSharedFlowsList(GoogleSharedflowList.class);
			allSharedflows = new ArrayList<String>();
			for (com.smartvalue.apigee.rest.schema.sharedFlow.google.auto.SharedFlow googleSharedflow
					: sharedflowList.getSharedFlows())
			{
				allSharedflows.add(googleSharedflow.getName()) ;  
			}
		}
		else {
			allSharedflows =  this.getOrganization().getAllShardFlow();
		}
		
		return exportAll(allSharedflows , folderDest ); 
	}
	
	public  HashMap<String , HashMap<Integer , Exception>> exportAll( ArrayList<String> sharedFlowList , String folderDest) throws UnirestException, IOException
	{
		
		HashMap<String , HashMap<Integer , Exception>> failedResult = new HashMap<String , HashMap<Integer , Exception>>();  
		{
			for (String sharedFlowStr : sharedFlowList)
			{
				System.out.println( "Start Exporting SharedFlow :" + sharedFlowStr );
				SharedFlow sharedFlow = this.getOrganization().getShardFlow(sharedFlowStr); 
				HashMap<Integer , Exception> xx = sharedFlow.exportAllDeployedRevisions(folderDest) ;
				failedResult.put(sharedFlowStr, xx); 
			}
		}
		return failedResult;
	}

	
	
	
	
	
}
