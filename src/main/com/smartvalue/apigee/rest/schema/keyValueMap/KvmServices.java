package com.smartvalue.apigee.rest.schema.keyValueMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.infra.ManagementServer;

public class KvmServices extends com.smartvalue.apigee.rest.schema.Service {
	
	

	public KvmServices(ManagementServer ms, String m_orgName, String m_envName) {
		super(ms, m_orgName, m_envName);
		// TODO Auto-generated constructor stub
	}

	public ArrayList <String>  getAllKvms() throws UnirestException, IOException
	{
		String apiPath = "/v1/organizations/"+orgName+"/environments/"+envName+"/keyvaluemaps" ; 
		ArrayList<String> result = this.getMs().executeGetMgmntAPI(apiPath, ArrayList.class) ; 
		return result;
		
	}
	
	public KeyValueMap  getKvmDetails( String m_kvmName ) throws UnirestException, IOException
	{
		String apiPath = "/v1/organizations/"+orgName+"/environments/"+envName+"/keyvaluemaps/"+  m_kvmName ; 
		KeyValueMap result = this.getMs().executeGetMgmntAPI(apiPath, KeyValueMap.class) ; 
		return result;
		
	}

	@Override
	public ArrayList<HttpResponse<String>> importAll(String folderPath, boolean m_deploy)
			throws UnirestException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<String, HashMap<Integer, Exception>> exportAll(String folderDest)
			throws UnirestException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<HttpResponse<String>> deleteAll() throws UnirestException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
