package com.smartvalue.apigee.rest.schema.targetServer;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.migration.transformers.ApigeeObjectTransformer;
import com.smartvalue.apigee.rest.schema.EnvironmentScopeService;

public class TargetServerServices  extends EnvironmentScopeService {
	
	public TargetServerServices(ManagementServer ms, String m_orgName) {
		super(ms, m_orgName);
	}


	@Override
	public String getApigeeObjectType() {
		return "targetservers";
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<String>  getTargetServersList(String m_org ) throws UnirestException, IOException
	{
		ArrayList<String> targetServersNames = null; 
		String apiPath = "/v1/organizations/"+m_org+"/environments/"+this.envName+"/targetservers?expand=true" ; 
		targetServersNames = this.getMs().executeGetMgmntAPI(apiPath , ArrayList.class ) ; 
		return targetServersNames ; 
	}


	@Override
	public ArrayList<ApigeeObjectTransformer> buildTransformers()
			throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		return this.getMs().getInfra().buildTargetServerTransformers();
	}

}
