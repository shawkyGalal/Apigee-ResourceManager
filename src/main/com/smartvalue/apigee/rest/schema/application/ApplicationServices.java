package com.smartvalue.apigee.rest.schema.application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
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

public class ApplicationServices extends ApigeeService {

	public ApplicationServices(ManagementServer ms) {
		super(ms);
	}


	@Override
	public DeleteResults deleteAll() throws UnirestException, IOException {
		// TODO Auto-generated method stub - 
		return null;
	}

	@Override
	public String getResourcePath() {
		
		return AppConfig.BASE_BATH+this.getMs().getOrgName()+"/"+getApigeeObjectType()+"/";
	}
	
	
	public Application getAppByName(String appName) throws Exception
	{
		return this.getResource(appName, Application.class) ; 
	}

	@SuppressWarnings("unchecked")
	public ArrayList<String>  getAllAppsNamesList() throws Exception
	{
		ArrayList<String> appssList = this.getAllResources(ArrayList.class); 
		return appssList ;  
	}

	public ArrayList<Application> getAllAppsList() throws Exception
	{
		
		return this.getAllResourcesList(Application.class) ; 

	}


	@Override
	public String getApigeeObjectType() {
		return "apps";
	}
	
	public List<String> getAllGrantedScopes()
	{
		List<String> result = new ArrayList<String>(); 
		// TODO Need Implementation get All App Products scopes - 
		return result ; 
	}


	@Override
	public ArrayList<ApigeeObjectTransformer> buildTransformers() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, FileNotFoundException, IOException   
	{
		return this.getMs().getInfra().buildAppsTransformers();
	}


	@Override
	public ProcessResults performETL(String objectId, UUID uuid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


}
