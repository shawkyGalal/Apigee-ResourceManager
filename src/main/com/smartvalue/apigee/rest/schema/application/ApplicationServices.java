package com.smartvalue.apigee.rest.schema.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.rest.schema.Service;
import com.smartvalue.apigee.rest.schema.product.Product;
import com.smartvalue.apigee.rest.schema.proxy.Proxy;

public class ApplicationServices extends Service {

	public ApplicationServices(ManagementServer ms, String m_orgName) {
		super(ms, m_orgName);
		
	}

	

	

	@Override
	public ArrayList<HttpResponse<String>> deleteAll() throws UnirestException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getResourcePath() {
		
		return "/v1/organizations/"+orgName+"/apps/";
	}
	
	
	public Application getAppByName(String appName) throws UnirestException, IOException
	{
		return this.getResource(appName, Application.class) ; 
	}

	@SuppressWarnings("unchecked")
	public ArrayList<String>  getAllAppsNamesList() throws UnirestException, IOException
	{
		ArrayList<String> appssList = this.getAllResources(ArrayList.class); 
		return appssList ;  
	}

	public ArrayList<Application> getAllAppsList() throws UnirestException, IOException
	{
		
		return this.getAllResourcesList(Application.class) ; 
		
	}





	@Override
	public String getApigeeObjectType() {
		// TODO Auto-generated method stub
		return "Application";
	}
	

}
