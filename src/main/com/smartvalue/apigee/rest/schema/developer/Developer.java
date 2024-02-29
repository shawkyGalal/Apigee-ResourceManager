package com.smartvalue.apigee.rest.schema.developer;

import com.smartvalue.apigee.rest.schema.application.Application;

public class Developer extends com.smartvalue.apigee.rest.schema.developer.auto.Developer 
{

	public Application getAppByname(String appName) throws Exception {
		Application result ; 
		String path = "/v1/organizations/"+this.getManagmentServer().getOrgName()+"/developers/"+this.getDeveloperId()+"/apps/" + appName ; 
		result = this.getManagmentServer().executeMgmntAPI(path, Application.class) ;
		result.setManagmentServer(getManagmentServer());
		return result ; 
	}

		
}
