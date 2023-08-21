package com.smartvalue.apigee.rest.schema;

import com.smartvalue.apigee.resourceManager.ManagementServer;

public abstract class Service {

	private ManagementServer ms ;
	protected String orgName ; 
	protected String envName ; 
	public ManagementServer getMs() {
		return ms;
	}
	
	public void setMs(ManagementServer ms) {
		this.ms = ms;
	}
	
	//----3 Types of constructors -------
	public  Service(ManagementServer ms ) {
		this.ms = ms ;
	}
	
	public  Service(ManagementServer ms , String m_orgName) {
		this.ms = ms ;
		this.orgName = m_orgName ;
	}
	
	public  Service(ManagementServer ms , String m_orgName, String m_envName) {
		this.ms = ms;
		this.orgName = m_orgName ;
		this.envName = m_envName ; 
	}
	
	
	
}
