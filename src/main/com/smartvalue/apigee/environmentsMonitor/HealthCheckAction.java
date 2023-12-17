package com.smartvalue.apigee.environmentsMonitor;

import java.util.ArrayList;

import com.smartvalue.apigee.rest.schema.environment.Environment;
import com.smartvalue.apigee.rest.schema.server.MPServer;

public class HealthCheckAction extends EnvironmentAction {

	@Override
	public void run() throws Exception {
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
