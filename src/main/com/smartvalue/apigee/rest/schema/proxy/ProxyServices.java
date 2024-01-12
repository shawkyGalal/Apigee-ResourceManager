package com.smartvalue.apigee.rest.schema.proxy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.rest.schema.Deployable;
import com.smartvalue.apigee.rest.schema.ApigeeService;
import com.smartvalue.apigee.rest.schema.BundleObjectService;
import com.smartvalue.apigee.rest.schema.organization.Organization;
import com.smartvalue.apigee.rest.schema.proxy.google.auto.GoogleProxiesList;
import com.smartvalue.apigee.rest.schema.proxy.google.auto.GoogleProxy;
import com.smartvalue.apigee.rest.schema.proxy.transformers.ApigeeObjectTransformer;
import com.smartvalue.apigee.rest.schema.proxy.transformers.NullTransformer;
import com.smartvalue.apigee.rest.schema.proxy.transformers.TransformResult;
import com.smartvalue.apigee.rest.schema.proxyUploadResponse.ProxyUploadResponse;


public class ProxyServices extends BundleObjectService implements Deployable {

	private boolean deployUponUpload = false ; 

	public ProxyServices(ManagementServer ms, String m_orgName) {
		super(ms, m_orgName);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Proxy>  getAllProxies() throws Exception
	{
		return this.getAllResourcesList(Proxy.class) ; 
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<String>  getAllProxiesList() throws Exception
	{
		ArrayList<String> proxiesList = this.getAllResources(ArrayList.class) ;  
		return proxiesList ;  
	}
	
	public <T> T  getAllProxiesList( Class<T> classOfT ) throws Exception
	{
		
		T proxiesList = this.getAllResources(classOfT) ;  
		return proxiesList ;  
	}
	
	/**
	 * REturn a MashMap with proxyname and revision numbers that Does uses the given polices  
	 * @param m_polices
	 * @param m_deployedVersionOnly
	 * @return
	 * @throws Exception 
	 */
	public HashMap<String, List<Object>>  getProxiesWithoutPolices(String[] m_polices , boolean m_deployedVersionOnly ) throws Exception
	{
		HashMap<String, List<Object>> result = new HashMap<>() ; 
		ArrayList<String> proxiesName = getAllProxiesList() ; 
		ManagementServer ms = this.getMs() ;
		Organization org = (Organization) ms.getOrgByName(this.orgName) ;
		int count = 0 ; 
		System.out.println("===============Start Searching for Proxies ("+proxiesName.size()+") Does not Use Polices with names " +  m_polices +"=======");
		for (String proxyName : proxiesName )
		{   count++; 
			System.out.print(count + "- Processing Proxy " + proxyName );
			Proxy proxy = org.getProxy(proxyName);
			ArrayList<Object> revisionWithoutPolices = proxy.getRevisionsNotUsingPolices(m_polices , m_deployedVersionOnly) ; 
			if (revisionWithoutPolices.size() > 0 )
			{  System.out.println("....  Found in revision(s)" +  revisionWithoutPolices );
				result.put(proxyName , revisionWithoutPolices ) ;
			}
			else {System.out.println("...   Ok " );}
		}
		System.out.println("===============End Searching for Proxies ("+proxiesName.size()+") =======");
		return result;
		
	}
	
	public HttpResponse<String> importProxy(String pundleZipFileName ) throws UnirestException, IOException
	{
		return importProxy (pundleZipFileName , new File(pundleZipFileName).getName() ) ; 
	}
	
	public ArrayList<TransformResult> transformPundle(String pundleZipFileName , String newFilePath)
	{
		int count=0; 
		int transformersSize = this.getTransformers().size();
		String sourceFile = pundleZipFileName ;
		ArrayList<TransformResult> result = new ArrayList<TransformResult>() ; 
		for (ApigeeObjectTransformer aot : this.getTransformers())
		{
			count++; 
			String tranformedFile = (count == transformersSize)? newFilePath : newFilePath+"_Tranform_"+count;
			if(aot.filter(pundleZipFileName))
			{
			 result.add( aot.trasform(sourceFile , tranformedFile));
			 sourceFile = tranformedFile + File.separator + new File(pundleZipFileName).getName() ;
			}
		}
		
		return result; 
	}
	public HttpResponse<String> importProxy(String pundleZipFileName , String m_proxyName) throws UnirestException, IOException
	{
		HttpResponse<String> result = null; 
		
		String apiPath = this.getResourcePath()+"?action=import&name="+m_proxyName+"&validate=true" ; 
		ManagementServer ms = this.getMs() ;
		result = ms.getPostFileHttpResponse(apiPath , pundleZipFileName ) ;
		return result ; 
	}
	
	public  ArrayList<HttpResponse<String>> importAll(String folderPath) throws UnirestException, IOException 
	{
		ArrayList<HttpResponse<String>> failedResult = new ArrayList<HttpResponse<String>>();  
		String envName ;
		File folder = new File(folderPath); 
		
		for (File envFolder : folder.listFiles() )
		{
			int envProxiesCount = 0 ; 
			envName = envFolder.getName(); 
			System.out.println("================Importing Proxies Deplyed TO Environment  " + envName +"==============");
			for (File proxyFolder : envFolder.listFiles() )
			{
				envProxiesCount++; 
				for (File revisionFolder : proxyFolder.listFiles() )
				{
				
					for (File zipfile : revisionFolder.listFiles())
					{
						int dotIndex = zipfile.getName().indexOf(".");
						if ( this.getProxyFilter() != null && !this.getProxyFilter().filter(zipfile))
						{
							System.out.println("=======Proxy "+ zipfile + " Is Scaped ==========") ; 
							break;
						}
						String proxyName= zipfile.getName().substring(0, dotIndex ) ; 
						System.out.println( proxyName + ":" +zipfile.getAbsolutePath()  );
						HttpResponse<String> result = importProxy(zipfile.getAbsolutePath() , proxyName);
						int status = result.getStatus() ; 
						if (! (status == 200 || status == 201) )
						{	
							System.out.println("Error Uploading Proxy " + proxyName);
							System.out.println("Error Details " + result.getBody());
							failedResult.add(result) ; 
						}
						if (this.isDeployUponUpload())
						{
							Gson json = new Gson(); 
							ProxyUploadResponse pur = json.fromJson(result.getBody(), ProxyUploadResponse.class); 
							//--- Started Deploying the proxy revision to environment 
							int newRevesion = pur.getConfigurationVersion().getMajorVersion();
							HttpResponse<String> deployresult = this.deployRevision(proxyName, envName , newRevesion) ;
							status = deployresult.getStatus() ;
							if (status != 200)
							{	
								System.out.println("Error Deplying Proxy " + proxyName);
								System.out.println("Error Details " + deployresult.getBody());
								failedResult.add(deployresult) ; 
							}
						}
					}
			
				}
				
			}
			System.out.println("==== End of Importing Proxies Deplyed to Environment " + envName +"==("+envProxiesCount+") Proxies =====\n\n\n");
		}
		System.out.println("Errors:  \n" + failedResult.toString()); 
		return failedResult;
	}
	private ProxyFilter proxyFilter ; 
	

	public HttpResponse<String> deleteProxy( String m_proxyName) throws UnirestException, IOException
	{
		HttpResponse<String> result = null; 
		String apiPath = getResourcePath()+m_proxyName ; 
		ManagementServer ms = this.getMs() ; 
		result = ms.getDeleteHttpResponse(apiPath ) ;
		return result ; 
	}
	
	public HttpResponse<String> deployRevision(String m_proxyName , String m_envName , int revision ) throws UnirestException, IOException
	{
		HttpResponse<String> result = null; 
		String apiPath = "/v1/organizations/"+orgName+"/environments/"+m_envName+"/apis/"+m_proxyName +"/revisions/"+revision+"/deployments" ; 
		ManagementServer ms = this.getMs() ; 
		result = ms.getPostHttpResponse(apiPath, null, null ) ;
		return result ; 
	}
	
	public  ArrayList<HttpResponse<String>> deleteAll() throws Exception
	{
		GoogleProxiesList proxiesList = this.getAllProxiesList(GoogleProxiesList.class);
		return deleteAll(proxiesList); 
	}
	
	public  ArrayList<HttpResponse<String>> deleteAll(GoogleProxiesList proxiesList) throws UnirestException, IOException
	{
		ArrayList<HttpResponse<String>> failedResult = new ArrayList<HttpResponse<String>>();  
		for (com.smartvalue.apigee.rest.schema.proxy.google.auto.GoogleProxy proxy : proxiesList.getProxies())
		{
			String proxyName = proxy.getName() ; 
			System.out.println( "proxyName :" + proxyName + " Deleted");
			HttpResponse<String> result = deleteProxy(proxyName) ; 
			int status = result.getStatus() ; 
			if (status != 200)
			{
				failedResult.add(result) ; 
			}
		}
		return failedResult;
	}
	
	public  HashMap<String , HashMap<String , Exception>> exportAll(String folderDest) throws Exception
	{
		ArrayList<String> allProxies ; 
		Boolean isGoogleCloud = this.getMs().getInfra().getGooglecloud() ;
		if (isGoogleCloud != null && isGoogleCloud)
		{
			GoogleProxiesList proxiesList = this.getMs().getProxyServices(this.orgName).getAllProxiesList(GoogleProxiesList.class);
			allProxies = new ArrayList<String>();
			for (GoogleProxy googleproxy : proxiesList.getProxies())
			{
				allProxies.add(googleproxy.getName()) ;  
			}
		}
		else {
			allProxies =  this.getAllProxiesList();
		}
		return exportAll(allProxies , folderDest ); 
	}
	
	public  HashMap<String , HashMap<String , Exception>> exportAll( ArrayList<String> proxiesList , String folderDest) throws UnirestException, IOException
	{
		
		HashMap<String , HashMap<String , Exception>> failedResult = new HashMap<String , HashMap<String , Exception>>();  
		{
			for (String proxyName : proxiesList)
			{
				System.out.println( "Start Exporting Proxy :" + proxyName );
				Proxy proxy = this.getOrganization().getProxy(proxyName); 
				HashMap<String , Exception> xx = proxy.exportAllDeployedRevisions(folderDest) ;
				failedResult.put(proxyName, xx); 
			}
		}
		return failedResult;
	}

	public ProxyFilter getProxyFilter() {
		return proxyFilter;
	}

	public void setProxyFilter(ProxyFilter proxyFilter) {
		this.proxyFilter = proxyFilter;
	}

	@Override
	public String getResourcePath() {
		// TODO Auto-generated method stub
		return "/v1/organizations/"+orgName+"/apis";
	}

	public boolean isDeployUponUpload() {
		return deployUponUpload;
	}

	public void setDeployUponUpload(boolean deployUponUpload) {
		this.deployUponUpload = deployUponUpload;
	}

	@Override
	public String getApigeeObjectType() {
		return "apis";
	}

	public ProxyServices withDeployUponUpload(boolean m_deployUponUpload) {
		this.deployUponUpload = m_deployUponUpload;
		return this;
	}
	
	
	
}
