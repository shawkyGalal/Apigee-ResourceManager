package com.smartvalue.apigee.migration.export;

import com.smartvalue.apigee.migration.load.LoadResult;

public class ExportResult extends LoadResult{

	private int revision ; 
	public int getRevision() {
		return revision;
	}
	private String deployedEnvName ; 
	
	public String getDeployedEnvName() {
		return deployedEnvName;
	}
	public void setRevision(int m_revision) {
		this.revision = m_revision ; 
		
	}
	public void setDeployedEnvName(String m_deployedEnvName) {
		deployedEnvName = m_deployedEnvName ; 
		
	}

}
