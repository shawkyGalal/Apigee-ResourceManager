package com.smartvalue.apigee.rest.schema.proxy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.resourceManager.ManagementServer;
import com.smartvalue.apigee.rest.schema.Service;

public class ProxyServices extends Service {

	public ProxyServices(ManagementServer ms, String m_orgName) {
		super(ms, m_orgName);
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Proxy>  getAllProxies() throws UnirestException, IOException
	{
		ArrayList<String> proxiesName = null; 
		String apiPath = "/v1/o/"+this.orgName+"/apis" ; 
		ManagementServer ms = this.getMs() ; 
		proxiesName = ms.executeGetMgmntAPI(apiPath , ArrayList.class ) ; 
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

}
