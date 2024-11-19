package com.smartvalue.apigee.rest.schema;

import java.io.IOException;
import java.io.Serializable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.resourceManager.helpers.Helper;

public abstract class ApigeeComman implements Serializable {

	//private transient PrintStream printStream = System.out; 
	private transient ManagementServer managmentServer;
	private transient String orgName ;
	
	public void setManagmentServer(ManagementServer ms) {
		this.managmentServer = ms;
		//this.setOrgName(ms.getOrgName());
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

	
	public String toJsonString() throws JsonProcessingException
	{
		return Helper.mapObjectToJsonStr(this) ; 
	}
	
	public void update() throws UnirestException, IOException {
		// There is issue here as toJsonString() will fail if this object contains a value for ManagementServer insbite it is marked as transient !!!! .. Under fix
		ManagementServer ms = this.getManagmentServer() ; 
		String objectType = Helper.mapClassToObjectType(this.getClass()); 
		String path = "/v1/organizations/"+this.getOrgName()+"/"+objectType+"/"+this.getUniqueId() ; 
		ms.getPutHttpResponse(path,  this.toJsonString(), "application/json") ; 
	}

	protected abstract String getUniqueId();
	
}
