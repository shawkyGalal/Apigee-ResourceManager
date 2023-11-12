package com.smartvalue.apigee.rest.schema;

import java.io.PrintStream;

import com.smartvalue.apigee.configuration.infra.ManagementServer;

public abstract class Service {

	private ManagementServer ms ;
	protected String orgName ; 
	protected String envName ; 
	private PrintStream printStream = System.out; 
	
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

	public PrintStream getPrintStream() {
		return printStream;
	}

	public void setPrintStream(PrintStream printStream) {
		this.printStream = printStream;
	}
	
	public Service withPrintStream(PrintStream printStream) {
		this.printStream = printStream;
		return this ; 
	}
	
	
	
}
