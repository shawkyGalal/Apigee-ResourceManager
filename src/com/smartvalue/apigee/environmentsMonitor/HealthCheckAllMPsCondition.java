package com.smartvalue.apigee.environmentsMonitor;


import java.util.ArrayList;
import java.util.List;

import com.smartvalue.apigee.resourceManager.ManagementServer;
import com.smartvalue.apigee.rest.schema.environment.Environment;
import com.smartvalue.apigee.rest.schema.server.MPServer;

public class HealthCheckAllMPsCondition extends EnvironmentCondition {

	public HealthCheckAllMPsCondition(Environment m_env) {
		super(m_env);
	}

	@Override
	public boolean evaluate() throws Exception  {
		boolean result  = true ; 
		ManagementServer ms = this.getEnv().getMs(); 
		ArrayList<String> regions = ms.getRegions(); 
		for (String region : regions)
		{
			List<MPServer> mpservers = this.getEnv().getMessageProcesors(region);
			for (MPServer mpserver : mpservers )
			{
				result = result  && mpserver.healthCheck(); 
				if (! result) 
				{
					break ; 
				}
			}
		}
		return result ; 
	}

	
	
}
