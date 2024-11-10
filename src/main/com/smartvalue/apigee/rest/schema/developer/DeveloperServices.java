package com.smartvalue.apigee.rest.schema.developer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.UUID;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.AppConfig;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.migration.ProcessResults;
import com.smartvalue.apigee.migration.deploy.DeployResults;
import com.smartvalue.apigee.migration.transformers.ApigeeObjectTransformer;
import com.smartvalue.apigee.rest.schema.ApigeeService;
import com.smartvalue.apigee.rest.schema.proxy.DeleteResults;

public class DeveloperServices extends ApigeeService {

	public DeveloperServices(ManagementServer ms, String m_orgName) {
		super(ms, m_orgName);
	}

	@Override
	public DeleteResults deleteAll() throws UnirestException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getResourcePath() {
		return AppConfig.BASE_BATH+this.getMs().getOrgName()+"/developers";
	}
	
	public Developer getDeveloperById(String developerId) throws Exception
	{
		Developer result ; 
		result = this.getResource(developerId, Developer.class) ;
		result.setManagmentServer(getMs());
		return result ; 
	}

	@SuppressWarnings("unchecked")
	public ArrayList<String>  getAllDevelopersIdList() throws Exception
	{
		ArrayList<String> DevelopersIdList = this.getAllResources(ArrayList.class); 
		return DevelopersIdList ;  
	}

	public ArrayList<Developer> getAllDevelopersList() throws Exception
	{
		
		return this.getAllResourcesList(Developer.class) ; 
		
	}

	@Override
	public String getApigeeObjectType() {
		return "Developer";
	}

	@Override
	public ArrayList<ApigeeObjectTransformer> buildTransformers()
			throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, FileNotFoundException, IOException {
		return this.getMs().getInfra().buildDeveloperTransformers();
	}

	@Override
	public ProcessResults performETL( String objectId,  UUID uuid ) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
}
