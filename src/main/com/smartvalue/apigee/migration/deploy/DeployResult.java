package com.smartvalue.apigee.migration.deploy;


import com.smartvalue.apigee.migration.ProcessResult;

public class DeployResult extends ProcessResult {
	
	private String envName ;  
	
	public String getEnvName() {
		return envName;
	}

	public void setEnvName(String envName) {
		this.envName = envName;
	}
}
