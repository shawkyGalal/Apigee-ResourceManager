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
import com.smartvalue.apigee.rest.schema.Service;
import com.smartvalue.apigee.rest.schema.organization.Organization;
import com.smartvalue.apigee.rest.schema.proxy.google.auto.GoogleProxiesList;
import com.smartvalue.apigee.rest.schema.proxy.google.auto.GoogleProxy;
import com.smartvalue.apigee.rest.schema.proxy.transformers.BundleUploadTransformer;
import com.smartvalue.apigee.rest.schema.proxyUploadResponse.ProxyUploadResponse;


public class ProxyServices extends Service {

	ArrayList<BundleUploadTransformer> bundleUploadTranformers = new ArrayList<BundleUploadTransformer>();
	private boolean deployUponUpload = false ; 
	
	public ArrayList<BundleUploadTransformer> getBundleUploadTranformers() {
		return bundleUploadTranformers;
	}

	public void setBundleUploadTranformers(ArrayList<BundleUploadTransformer> bundleUploadTranformers) {
		this.bundleUploadTranformers = bundleUploadTranformers;
	}

	public ProxyServices(ManagementServer ms, String m_orgName) {
		super(ms, m_orgName);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Proxy>  getAllProxies() throws UnirestException, IOException
	{
		return this.getAllResourcesList(Proxy.class) ; 
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<String>  getAllProxiesList() throws UnirestException, IOException
	{
		ArrayList<String> proxiesList = this.getAllResources(ArrayList.class) ;  
		return proxiesList ;  
	}
	
	public <T> T  getAllProxiesList( Class<T> classOfT ) throws UnirestException, IOException
	{
		
		T proxiesList = this.getAllResources(classOfT) ;  
		return proxiesList ;  
	}
	
	/**
	 * REturn a MashMap with proxyname and revision numbers that Does uses the given polices  
	 * @param m_polices
	 * @param m_deployedVersionOnly
	 * @return
	 * @throws UnirestException
	 * @throws IOException
	 */
	public HashMap<String, List<Object>>  getProxiesWithoutPolices(String[] m_polices , boolean m_deployedVersionOnly ) throws UnirestException, IOException
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
	public HttpResponse<String> importProxy(String pundleZipFileName , String m_proxyName) throws UnirestException, IOException
	{
		HttpResponse<String> result = null; 
		String apiPath = "/v1/organizations/"+orgName+"/apis?action=import&name="+m_proxyName+"&validate=true" ; 
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
							HttpResponse<String> deployresult = this.deployProxyRevision(proxyName, envName , newRevesion) ;
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
	
	public HttpResponse<String> deployProxyRevision(String m_proxyName , String m_envName , int revision ) throws UnirestException, IOException
	{
		HttpResponse<String> result = null; 
		String apiPath = "/v1/organizations/"+orgName+"/environments/"+m_envName+"/apis/"+m_proxyName +"/revisions/"+revision+"/deployments" ; 
		ManagementServer ms = this.getMs() ; 
		result = ms.getPostHttpResponse(apiPath, "", "" ) ;
		return result ; 
	}
	
	public  ArrayList<HttpResponse<String>> deleteAll() throws UnirestException, IOException
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
	
	public  HashMap<String , HashMap<String , Exception>> exportAll(String folderDest) throws UnirestException, IOException
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
	
	
	
}
