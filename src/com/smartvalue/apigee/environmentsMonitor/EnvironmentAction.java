package com.smartvalue.apigee.environmentsMonitor;

import com.smartvalue.apigee.rest.schema.environment.Environment;

public abstract class EnvironmentAction  implements Iaction {
	private Environment env ; 
	
	public EnvironmentAction ( Environment m_env)
	{
		this.env = m_env ; 
	}
	
	public Environment getEnv() {
		return env;
	}
}
