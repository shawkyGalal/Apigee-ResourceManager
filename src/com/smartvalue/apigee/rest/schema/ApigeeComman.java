package com.smartvalue.apigee.rest.schema;

import java.io.PrintStream;

import com.smartvalue.apigee.configuration.infra.ManagementServer;

public class ApigeeComman {

	private transient PrintStream printStream = System.out; 
	private transient  ManagementServer managmentServer;
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

	public PrintStream getPrintStream() {
		return printStream;
	}

	public void setPrintStream(PrintStream printStream) {
		this.printStream = printStream;
	}

	
}
