package com.smartvalue.apigee.rest.schema;

import java.io.PrintStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.smartvalue.apigee.configuration.infra.ManagementServer;

public abstract class ApigeeComman {

	private transient PrintStream printStream = System.out; 
	private transient  ManagementServer managmentServer;
	protected static final Logger logger = LogManager.getLogger(ApigeeComman.class);
	private String orgName ;
	
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

	public PrintStream getPrintStream() {
		return printStream;
	}

	public void setPrintStream(PrintStream printStream) {
		this.printStream = printStream;
	}
	
}
