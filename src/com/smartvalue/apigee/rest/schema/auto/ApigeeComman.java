package com.smartvalue.apigee.rest.schema.auto;

import com.smartvalue.apigee.resourceManager.ManagementServer;

public class ApigeeComman {

	private ManagementServer managmentServer;
	private String orgName ;  

	
	public void setManagmentServer(ManagementServer ms) {
		this.managmentServer = ms;
	}
	
	public ManagementServer getManagmentServer() {
		return this.managmentServer ; 
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	
}
