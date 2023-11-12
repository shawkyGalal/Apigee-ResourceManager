package com.smartvalue.apigee.environmentsMonitor;

import com.smartvalue.apigee.rest.schema.environment.Environment;

public abstract class EnvironmentCondition implements Icondition {
	
	private Environment env ; 

	public EnvironmentCondition ( Environment m_env)
	{
		this.env = m_env ; 
	}

	public Environment getEnv() {
		return env;
	}

}
