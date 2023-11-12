package com.smartvalue.apigee.environmentsMonitor;

import com.smartvalue.apigee.rest.schema.environment.Environment;

public class CondActionPair {

	private EnvironmentAction action ; 
	private EnvironmentCondition condition ; 
	private Environment env ;



	public EnvironmentAction getAction() {
		return action;
	}
	public void setAction(EnvironmentAction action) {
		this.action = action;
	}
	public EnvironmentCondition getCondition() {
		return condition;
	}
	public void setCondition(EnvironmentCondition condition) {
		this.condition = condition;
	}
	public Environment getEnv() {
		return env;
	}
	public void setEnv(Environment env) {
		this.env = env;
	} 
}
