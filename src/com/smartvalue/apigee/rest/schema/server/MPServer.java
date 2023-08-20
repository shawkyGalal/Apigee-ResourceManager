package com.smartvalue.apigee.rest.schema.server;


import java.io.IOException;
import java.util.HashMap;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.rest.schema.environment.Environment;

public class MPServer extends Server {

			
	
	
	
	public HashMap<String , Object > associateWithEnvironment(Environment env ) throws UnirestException, IOException
	{
		String path = "/v1/o/"+this.getOrgName()+"/e/"+env.getName()+"/servers" ; 
		String uuid = this.getuUID() ;
		@SuppressWarnings("unchecked")
		HashMap<String , Object > result =  this.getManagmentServer().executePostMgmntAPI(path, HashMap.class, "action=add&uuid=" + uuid) ; 
		return result ; 
	}
	
	public void desAssociateWithEnvironment(Environment env )
	{
		//====ToDo Implement logic
	}
	
}
