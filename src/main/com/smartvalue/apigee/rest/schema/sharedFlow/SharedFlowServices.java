package com.smartvalue.apigee.rest.schema.sharedFlow;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.migration.export.ExportResults;
import com.smartvalue.apigee.migration.transformers.ApigeeObjectTransformer;
import com.smartvalue.apigee.migration.transformers.IApigeeObjectTransformer;
import com.smartvalue.apigee.rest.schema.Deployable;
import com.smartvalue.apigee.rest.schema.proxy.Proxy;
import com.smartvalue.apigee.rest.schema.proxyDeployment.ProxyDeployment;
import com.smartvalue.apigee.rest.schema.proxyDeployment.auto.Environment;
import com.smartvalue.apigee.rest.schema.proxyDeployment.auto.Revision;
import com.smartvalue.apigee.rest.schema.BundleObjectService;
import com.smartvalue.apigee.rest.schema.sharedFlow.google.auto.GoogleSharedflowList;



public class SharedFlowServices extends BundleObjectService implements Deployable{

	private boolean deployUponUpload = false ; 

	public SharedFlowServices(ManagementServer ms, String m_orgName  ) {
		super(ms, m_orgName );
	}
	
	public SharedFlow  getSharedFlows(String sharedFlowName) throws Exception
	{
		return this.getResource(sharedFlowName, SharedFlow.class) ; 
	}
	
	public ArrayList<SharedFlow>  getAllSharedFlows() throws Exception
	{
		ArrayList<String> sharedflowsNames = getAllBundledObjectNameList() ; 
		ManagementServer ms = this.getMs() ;
		ArrayList<SharedFlow> AllSharedflows = new ArrayList<SharedFlow>() ; 
		for (String sharedflowName : sharedflowsNames)
		{
			SharedFlow sharedflow = getSharedFlows(sharedflowName) ; //ms.executeGetMgmntAPI(apiPath01 , SharedFlow.class ) ;
			sharedflow.setOrgName(this.orgName) ; 
			sharedflow.setManagmentServer(ms) ; 
			AllSharedflows.add (sharedflow) ; 
		}
		return AllSharedflows ; 
	}
	
	
	
	public <T> T  getAllSharedFlowsList( Class<T> classOfT ) throws Exception
	{
		T proxiesList  = this.getAllResources(classOfT) ; 
		return proxiesList ;  
	}
	

	
	public void transformPundle(String pundleZipFileName , String newFilePath) throws Exception
	{
		int count=0; 
		ArrayList<ApigeeObjectTransformer> transformers  = this.buildTransformers() ;
		int transformersSize = transformers.size();
		String sourceFile = pundleZipFileName ;
		for (ApigeeObjectTransformer aot : transformers )
		{
			count++; 
			String tranformedFile = (count == transformersSize)? newFilePath : newFilePath+"_"+count;
			if(aot.filter(pundleZipFileName))
			{
			 aot.trasform(sourceFile , tranformedFile);
			 sourceFile = tranformedFile ;
			}
		}
	}
	/*
	public  ArrayList<HttpResponse<String>> importAll(String folderPath ) throws UnirestException, IOException
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
						HttpResponse<String> result = importObject(zipfile.getAbsolutePath() , sharedflowName);
						int status = result.getStatus() ; 
						if (! (status == 200 || status == 201) )
						{	
							System.out.println("Error Uploading SharedFlow " + sharedflowName);
							System.out.println("Error Details " + result.getBody());
							failedResult.add(result) ; 
						}
						if (isDeployUponUpload())
						{
							Gson json = new Gson(); 
							ProxyUploadResponse pur = json.fromJson(result.getBody(), ProxyUploadResponse.class); 
							//--- Started Deploying the proxy revision to environment 
							int newRevesion = pur.getConfigurationVersion().getMajorVersion();
							HttpResponse<String> deployresult = this.deployRevision(sharedflowName, envName , newRevesion) ;
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
	
*/
	public HttpResponse<String> deleteSharedFlow( String m_sharedFlow) throws UnirestException, IOException
	{
		HttpResponse<String> result = null; 
		String apiPath = this.getResourcePath()+"/"+ m_sharedFlow ; 
		ManagementServer ms = this.getMs() ; 
		result = ms.getDeleteHttpResponse(apiPath ) ;
		return result ; 
	}
	
	
	public  ArrayList<HttpResponse<String>> deleteAll() throws Exception
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
	

	
	public  ExportResults exportAll(String folderDest) throws Exception
	{
		
		ArrayList<String> allSharedflows ; 
		Boolean isGoogleCloud = this.getMs().getInfra().isGooglecloud() ;
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
	
	public  ExportResults exportAll( ArrayList<String> sharedFlowList , String folderDest) throws UnirestException, IOException
	{
		ExportResults exportResults = new ExportResults(); 
		{
			for (String sharedFlowStr : sharedFlowList)
			{
				System.out.println( "Start Exporting SharedFlow :" + sharedFlowStr );
				SharedFlow sharedFlow = this.getOrganization().getShardFlow(sharedFlowStr); 
				exportResults.addAll( sharedFlow.exportAllDeployedRevisions(folderDest)) ;
			}
		}
		return exportResults;
	}

	@Override
	public String getResourcePath() {
		
		return "/v1/organizations/"+orgName+"/"+getApigeeObjectType();
	}

	public boolean isDeployUponUpload() {
		return deployUponUpload;
	}

	public void setDeployUponUpload(boolean deployUponUpload) {
		this.deployUponUpload = deployUponUpload;
	}

	@Override
	public String getApigeeObjectType() {
		return "sharedflows";
	}

	@Override
	public ArrayList<ApigeeObjectTransformer> buildTransformers()
			throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, FileNotFoundException, IOException {
		
		return this.getMs().getInfra().buildSharedFlowTransformers();
	}

		
	
}
