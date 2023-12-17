package com.smartvalue.apigee.rest.schema.proxy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.rest.schema.Service;
import com.smartvalue.apigee.rest.schema.organization.Organization;
import com.smartvalue.apigee.rest.schema.proxy.google.auto.GoogleProxiesList;
import com.sun.net.httpserver.Authenticator.Result;

public class ProxyServices extends Service {

	public ProxyServices(ManagementServer ms, String m_orgName) {
		super(ms, m_orgName);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Proxy>  getAllProxies() throws UnirestException, IOException
	{
		ArrayList<String> proxiesName = getAllProxiesNames() ; 
		ManagementServer ms = this.getMs() ;
		String apiPath = "/v1/organizations/"+orgName+"/apis" ; 
		ArrayList<Proxy> proxies = new ArrayList<Proxy>() ; 
		for (String proxyName : proxiesName)
		{
			String apiPath01 = apiPath + "/" + proxyName ; 
			@SuppressWarnings("deprecation")
			Proxy proxy = ms.executeGetMgmntAPI(apiPath01 , Proxy.class ) ;
			proxy.setOrgName(this.orgName) ; 
			proxy.setManagmentServer(ms) ; 
			proxies.add (proxy) ; 
		}
		return proxies ; 
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<String>  getAllProxiesNames() throws UnirestException, IOException
	{
		ArrayList<String> proxiesName = null; 
		String apiPath = "/v1/organizations/"+orgName+"/apis" ; 
		ManagementServer ms = this.getMs() ; 
		proxiesName = ms.executeGetMgmntAPI(apiPath , ArrayList.class ) ;
		return proxiesName ;  
	}
	
	public <T> T  getAllProxiesList( Class<T> classOfT ) throws UnirestException, IOException
	{
		T proxiesList = null; 
		String apiPath = "/v1/organizations/"+orgName+"/apis" ; 
		ManagementServer ms = this.getMs() ; 
		proxiesList = ms.executeGetMgmntAPI(apiPath , classOfT ) ;
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
		ArrayList<String> proxiesName = getAllProxiesNames() ; 
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
	
	public HttpResponse<String> uploadPundle(String pundleZipFileName , String m_proxyName) throws UnirestException, IOException
	{
		HttpResponse<String> result = null; 
		String apiPath = "/v1/organizations/"+orgName+"/apis?action=import&name="+m_proxyName+"&validate=true" ; 
		ManagementServer ms = this.getMs() ; 
		result = ms.getPostFileHttpResponse(apiPath , pundleZipFileName ) ;
		return result ; 
	}
	
	
	
	public  ArrayList<HttpResponse<String>> uploadFolder(String folderPath) throws UnirestException, IOException
	{
		ArrayList<HttpResponse<String>> failedResult = new ArrayList<HttpResponse<String>>();  
		File folder = new File(folderPath); 
		for (File zipfile : folder.listFiles())
		{
			int dotIndex = zipfile.getName().indexOf("."); 
			String proxyName= zipfile.getName().substring(0, dotIndex ) ; 
			System.out.println( proxyName + ":" +zipfile.getAbsolutePath()  );
			HttpResponse<String> result = uploadPundle(zipfile.getAbsolutePath() , proxyName);
			int status = result.getStatus() ; 
			if (status != 200)
			{	
				System.out.println("Error Uploading Proxy " + proxyName);
				System.out.println("Error Details " + result.getBody());
				failedResult.add(result) ; 
			}
		}
		System.out.println(failedResult.toString()); 
		return failedResult;
	}

	public HttpResponse<String> deleteProxy( String m_proxyName) throws UnirestException, IOException
	{
		HttpResponse<String> result = null; 
		String apiPath = "/v1/organizations/"+orgName+"/apis/"+m_proxyName ; 
		ManagementServer ms = this.getMs() ; 
		result = ms.getDeleteHttpResponse(apiPath ) ;
		return result ; 
	}
	
	public  ArrayList<HttpResponse<String>> deleteAllProxies(GoogleProxiesList proxiesList) throws UnirestException, IOException
	{
		ArrayList<HttpResponse<String>> failedResult = new ArrayList<HttpResponse<String>>();  
		 
		for (com.smartvalue.apigee.rest.schema.proxy.google.auto.Proxy proxy : proxiesList.getProxies())
		{
			String proxyName = proxy.getName() ; 
			System.out.println( "proxyName :" + proxyName );
			HttpResponse<String> result = deleteProxy(proxyName) ; 
			int status = result.getStatus() ; 
			if (status != 200)
			{
				failedResult.add(result) ; 
			}
		}
		return failedResult;
	}
	
	
}
