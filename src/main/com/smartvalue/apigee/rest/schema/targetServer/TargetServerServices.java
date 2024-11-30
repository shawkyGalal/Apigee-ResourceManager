package com.smartvalue.apigee.rest.schema.targetServer;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.UUID;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.AppConfig;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.migration.ProcessResults;
import com.smartvalue.apigee.migration.transformers.ApigeeObjectTransformer;
import com.smartvalue.apigee.rest.schema.EnvironmentScopeService;

public class TargetServerServices  extends EnvironmentScopeService {
	
	
	public TargetServerServices(ManagementServer ms) {
		super(ms);
	}


	@Override
	public String getApigeeObjectType() {
		return "targetservers";
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<String>  getTargetServersList(String m_org ) throws UnirestException, IOException
	{
		ArrayList<String> targetServersNames = null; 
		String apiPath = AppConfig.BASE_BATH+m_org+"/environments/"+this.envName+"/targetservers?expand=true" ; 
		targetServersNames = this.getMs().executeGetMgmntAPI(apiPath , ArrayList.class ) ; 
		return targetServersNames ; 
	}


	@Override
	public ArrayList<ApigeeObjectTransformer> buildTransformers()
			throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, FileNotFoundException, IOException {
		return this.getMs().getInfra().buildTargetServerTransformers();
	}


	@Override
	public ProcessResults performETL(String objectId, UUID  uuid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
}
