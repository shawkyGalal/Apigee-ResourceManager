package com.smartvalue.apigee.rest.schema.proxy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.rest.schema.Service;
import com.smartvalue.apigee.rest.schema.organization.Organization;

public class ProxyServices extends Service {

	public ProxyServices(ManagementServer ms, String m_orgName) {
		super(ms, m_orgName);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Proxy>  getAllProxies() throws UnirestException, IOException
	{
		ArrayList<String> proxiesName = getAllProxiesNames() ; 
		ManagementServer ms = this.getMs() ;
		String apiPath = "/v1/o/"+orgName+"/apis" ; 
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
		String apiPath = "/v1/o/"+orgName+"/apis" ; 
		ManagementServer ms = this.getMs() ; 
		proxiesName = ms.executeGetMgmntAPI(apiPath , ArrayList.class ) ;
		return proxiesName ;  
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
	

}
