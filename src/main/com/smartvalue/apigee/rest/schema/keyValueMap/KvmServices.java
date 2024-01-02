package com.smartvalue.apigee.rest.schema.keyValueMap;

import java.io.IOException;
import java.util.ArrayList;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.rest.schema.EnvironmentScopeService;

public class KvmServices extends EnvironmentScopeService {
	
	

	public KvmServices(ManagementServer ms, String m_orgName   ) {
		super(ms, m_orgName );
	}

	public ArrayList <String>  getAllKvms() throws UnirestException, IOException
	{
		ArrayList<String> result = null;
		try {
			result = this.getMs().executeGetMgmntAPI(getResourcePath(), ArrayList.class);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return result;
	}
	
	public KeyValueMap  getKvmDetails( String m_kvmName ) throws Exception
	{
		String apiPath = getResourcePath() +  m_kvmName ; 
		KeyValueMap result = this.getMs().executeGetMgmntAPI(apiPath, KeyValueMap.class) ; 
		result.setEnvName(envName);
		return result;
		
	}

	@Override
	public String getApigeeObjectType() {
		return "keyvaluemaps";
	}

}
