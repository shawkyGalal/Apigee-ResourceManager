package com.smartvalue.apigee.rest.schema.developer;

import java.io.IOException;
import java.util.ArrayList;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.rest.schema.Service;
import com.smartvalue.apigee.rest.schema.application.Application;
import com.smartvalue.apigee.rest.schema.product.Product;

public class DeveloperServices extends Service {

	public DeveloperServices(ManagementServer ms, String m_orgName) {
		super(ms, m_orgName);
	}

	@Override
	public ArrayList<HttpResponse<String>> deleteAll() throws UnirestException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getResourcePath() {
		return "/v1/organizations/"+orgName+"/developers";
	}
	
	public Developer getDeveloperById(String developerId) throws UnirestException, IOException
	{
		return this.getResource(developerId, Developer.class) ; 
	}

	@SuppressWarnings("unchecked")
	public ArrayList<String>  getAllDevelopersIdList() throws UnirestException, IOException
	{
		ArrayList<String> DevelopersIdList = this.getAllResources(ArrayList.class); 
		return DevelopersIdList ;  
	}

	public ArrayList<Developer> getAllDevelopersList() throws UnirestException, IOException
	{
		
		return this.getAllResourcesList(Developer.class) ; 
		
	}

	@Override
	public String getApigeeObjectType() {
		// TODO Auto-generated method stub
		return "Developer";
	}


}
