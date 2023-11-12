package com.smartvalue.apigee.environmentsMonitor;

import java.io.IOException;
import java.util.ArrayList;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.rest.schema.environment.Environment;
import com.smartvalue.apigee.rest.schema.server.MPServer;

public class HealthCheckAction extends EnvironmentAction {

	@Override
	public void run() throws UnirestException, IOException {
		// TODO Auto-generated method stub
		// Add one of the free mp's to this env. 
		Environment env = this.getEnv() ;
		ArrayList<MPServer> freeMps = env.getMs().getFreeMps(this.getRegion()) ;
		env.addMessageProcessor(freeMps.get(0)) ; 
	}
	
	

	public HealthCheckAction (Environment m_env)
	{
		super(m_env); 
	}
	

}
